package kr.or.ddit.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

@Slf4j
public class FileUploadUtils {
	//1) 업로드 폴더 정보
	public static String uploadFolder = "C:\\eGovFrame3.10.0\\workspace\\springProj\\src\\main\\webapp\\resources\\upload";
	
	//2) 연월일 폴더 생성
	public static String getFolder() {
		//간단날짜형식
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		String str = sdf.format(date);
		//File.separator : 윈도우 경로 \\
		return str.replace("-", File.separator);
	}
	
	//3) 이미지인지 판단(섬네일은 이미지만 가능함)
	public static boolean checkImageType(File file) {
		//.jpeg / .jpg의 MIME 타입 : image/jpeg
		//file.toPath() : 파일객체를 path객체로 변환
		String contentType;
		try {
			contentType = Files.probeContentType(file.toPath());
			log.info("contentType : " + contentType);
			//MIME 타입 정보가 image로 시작하는지 여부를 return
			return contentType.startsWith("image");//true/false
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//4) 다중업로드 처리
	public static String multiUpload(MultipartFile[] pictures) {
		//upload까지의 경로, 년월일
		File uploadPath = new File(FileUploadUtils.uploadFolder,FileUploadUtils.getFolder());
		log.info("uploadPath : " + uploadPath);
		//만약 연/월/일 해당 폴더가 없으면 생성
		if(uploadPath.exists()==false) {
			uploadPath.mkdirs();
		}
		
		//파일객체 배열로부터 파일을 하나씩 꺼내옴
		for(MultipartFile picture : pictures) {
			log.info("--------------------------");
			log.info("파일명 : " + picture.getOriginalFilename());
			log.info("파일크기 : " + picture.getSize());
			log.info("MINE타입 : " + picture.getContentType());
			// c:\\img\\개똥이.jpg => 개똥이.jpg
			String uploadFileName = picture.getOriginalFilename();
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
			
			//------------- 같은날 같은 이미지를 업로드 시 파일 중복 방지 시작 -------------
			UUID uuid = UUID.randomUUID();	//임의의 값을 생성
			
			//원래의 파일 이름과 구분하기 위해 _를 붙임. asdfasdf_개똥이.jpg
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			//------------- 같은날 같은 이미지를 업로드 시 파일 중복 방지 끝 -------------
			
			//File 객체 설계(복사할 대상 경로, 파일명)
			//... upload\\2023\\08\\08\\asdfasdf_개똥이.jpg
			File saveFile = new File(uploadPath, uploadFileName);
			
			try {
				//원본파일객체.transferTo(설계)
				//파일 복사가 일어남
				picture.transferTo(saveFile);
				
				//--------------섬네일 처리 시작-----------------
				//이미지인지 체킹
				if(checkImageType(saveFile)) {//이미지라면..
					//... upload\\2023\\08\\08\\s_asdfasdf_개똥이.jpg
					//설계
					FileOutputStream thumbnail = new FileOutputStream(
							new File(uploadPath, "s_" + uploadFileName)
							);
					//섬네일 생성(원본,설계,가로크기,세로크기)
					Thumbnailator.createThumbnail(picture.getInputStream(),thumbnail,100,100);
					thumbnail.close();
				}
				//--------------섬네일 처리 끝-----------------
				
			}catch(IllegalStateException e) {
				log.error(e.getMessage());
				return "0";
			}catch(IOException e) {
				log.error(e.getMessage());
				return "0";
			}
			
		}//end for
		
		return "1";
	}
	
}



