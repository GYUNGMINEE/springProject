package kr.or.ddit.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.dao.BookInfoDao;
import kr.or.ddit.service.BookInfoService;
import kr.or.ddit.vo.AttachVO;
import kr.or.ddit.vo.BookInfoVO;
import lombok.extern.slf4j.Slf4j;

//서비스 클래스 : 비즈니스 로직
//스프링 MVC 구조에서 Controller와 DAO를 연결하는 역할
@Slf4j
@Service
public class BookInfoServiceImpl implements BookInfoService {
	//데이터베이스 접근을 위해 BookInfoDao 인스턴스를 주입받자
	//DI / IoC
	@Autowired
	BookInfoDao bookInfoDao;

	//1. 도서 정보 등록
	/*
	BookInfoVO(author=개똥이, bookId=ISBN1234, category=소설, condition=신규도서
	, description=<p>내용부분</p>
	, name=개똥이의 여행, publisher=삼성출판사, releaseDate=2023-07-26
	, totalPages=100, unitPrice=10000, unitsInStock=0
	, bookImage=org.springframework.web.multipart.su
	 */
	@Override
	public int addBookPost(BookInfoVO bookInfoVO) {
		//1-1) 도서 정보 등록
		int result = this.bookInfoDao.addBookPost(bookInfoVO);//부모
		
		//1-2) 첨부파일 등록. bookId, filename이 필요함
		AttachVO attachVO = new AttachVO();
		
		attachVO.setBookId(bookInfoVO.getBookId());//참조무결성
		
		//////////// 첨부파일 처리 시작 ////////////////////
		String uploadFolder = 
			"C:\\eGovFrame3.10.0\\workspace\\springProj\\src\\main\\webapp\\resources\\images";
		
		//연/월/일 폴더 생성
		//2023-07-28 형식(format) 지정
		//간단한 날짜 형식
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//날짜 객체 생성(java.util 패키지)
		Date date = new Date();
		//2023-07-28 => ..s\\images\\2023\\07\\28
		String str = sdf.format(date);
		File uploadPath = new File(uploadFolder,str.replace("-", File.separator));
		log.info("uploadPath : " + uploadPath);
		
		//만약 연/월/일 해당 폴더가 없으면 생성
		if(uploadPath.exists()==false) {
			uploadPath.mkdirs();
		}
		//파일객체(업로드 대상)
		MultipartFile multipartFile = bookInfoVO.getBookImage();
		
		log.info("파일명 : " + multipartFile.getOriginalFilename());
		log.info("파일크기 : " + multipartFile.getSize());
		log.info("MIME타입 : " + multipartFile.getContentType());
		//원래 파일명. 개똥이.jpg
		String uploadFileName = multipartFile.getOriginalFilename();
		
		//같은 날 같은 이미지를 업로드 시 파일 중복되는 것을 방지해보자
		//java.util.UUID => 랜덤값 생성
		UUID uuid = UUID.randomUUID();
		//원래의 파일 이름과 구분하기 위해 _를 붙임(ASDFAFDSFD_개똥이.jpg)
		uploadFileName = uuid.toString() + "_" + uploadFileName;
		
		//File객체 복사 설계(복사할 대상 경로, 파일명)
		File saveFile = new File(uploadPath,uploadFileName);
		
		//설계대로 파일 복사 실행
		try {
			multipartFile.transferTo(saveFile);
			//str : 2023-07-28
			// /2023/07/28/ASDFAFDSFD_개똥이.jpg 이 세팅됨
			attachVO.setFilename("/" 
			+ str.replace("-", File.separator).replace("\\", "/") 
			+ "/" + uploadFileName);
			
			result += this.bookInfoDao.addAttach(attachVO);//자식
			
			log.info("최종result : " + result);
			
			return result;
		} catch (IllegalStateException | IOException e) {
			log.error(e.getMessage());
			return 0;
		}
		//////////// 첨부파일 처리 끝 ////////////////////
	}
	
	//도서코드 자동생성
	@Override
	public String getBookId() {
		return this.bookInfoDao.getBookId();
	}
	
	//도서목록
	@Override
	public List<BookInfoVO> listBook(Map<String,Object> map){
		return this.bookInfoDao.listBook(map);
	}
	
	//BOOK_INFO 테이블의 전체 행 수
	@Override
	public int getBookInfoTotal(Map<String,Object> map) {
		return this.bookInfoDao.getBookInfoTotal(map);
	}
	
	//도서 상세 
	@Override
	public BookInfoVO detailBook(String bookId) {
		return this.bookInfoDao.detailBook(bookId);
	}
	
	//도서정보 및 첨부파일이 함께 수정
	@Override
	public int updateBookPost(BookInfoVO bookInfoVO) {
		//1) 도서정보 수정
		int result = this.bookInfoDao.updateBookPost(bookInfoVO);
		
		log.info("bookInfoVO.getBookImage() : " + bookInfoVO.getBookImage().getSize());
		//2) 첨부파일정보 수정
		//2-1) 파일객체가 있음(파일도 수정하겠음) bookInfoVO{...bookImage=org.springfra...
		if(bookInfoVO.getBookImage().getSize()>0) {
			log.info("파일객체가 있음");
			//업로드 폴더 설정
			String uploadFolder 
				= "C:\\eGovFrame3.10.0\\workspace\\springProj\\src\\main\\webapp\\resources\\images";
			//연월일 폴더 설정
			File uploadPath = new File(uploadFolder, getFolder());
			//만약 연월일 폴더가 없으면 생성
			if(uploadPath.exists()==false) {
				uploadPath.mkdirs();
			}
			
			//원래 파일명을 할당할 변수
			String uploadFileName = "";
			
			//파일정보를 확인해보자
			MultipartFile multipartFile = bookInfoVO.getBookImage();
			log.info("파일명 : " + multipartFile.getOriginalFilename());
			log.info("파일크기 : " + multipartFile.getSize());
			log.info("파일MIME타입 : " + multipartFile.getContentType());
			
			//같은 날 같은 이미지를 업로드 시 파일명 중복 방지
			//java.util.UUID => 랜덤값 생성
			UUID uuid = UUID.randomUUID();		
			
			//File객체 설계(복사할 대상 경로, uuid데이터_파일명)
			//uploadPath : ...\\images\\2023\\08\\02
			//uploadFileName : asdfdsfa_개똥이.jpg
			uploadFileName = uuid.toString() + "_" + multipartFile.getOriginalFilename();
			File saveFile = new File(uploadPath,uploadFileName);
			
			try {
				//파일 복사 실행(원본파일객체.transferTo(설계))
				multipartFile.transferTo(saveFile);
				
				AttachVO attachVO = new AttachVO();
				//SEQ(자동검색), BOOK_ID, FILENAME
				attachVO.setBookId(bookInfoVO.getBookId());
				//web경로를 setting
				// /2023/08/02/asdfasfdk_개똥이.jpg
				attachVO.setFilename("/" + getFolder().replace("\\", "/") + "/" + uploadFileName);
				
				//ATTACH 테이블에 update 처리
				result += this.bookInfoDao.updateAttach(attachVO);
				
				return result;
			}catch (IllegalStateException e) {
				log.error(e.getMessage());
				return 0;
			}catch(IOException e) {
				log.error(e.getMessage());
				return 0;
			}
		}else {
			log.info("파일객체가 없음");
			
			//2-2) 파일객체가 없음(파일은 수정안함)  bookInfoVO{...bookImage=null
			return result;
		}//end if
	}
	
	//도서 삭제
	//<delete id="deleteBookPost" parameterType="bookInfoVO">
	@Override
	public int deleteBookPost(BookInfoVO bookInfoVO) {
		return this.bookInfoDao.deleteBookPost(bookInfoVO);
	}
	
	//연월일 폴더 생성
	public String getFolder() {
		//2023-08-02 형식(format)지정
		//간단한 날짜 형식
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//날짜 객체 생성(java.util.Date)
		Date date = new Date();
		//2023-08-02
		String str = sdf.format(date);
		
		//File.separator : 윈도우 폴더 구분자(2023\\08\\02)
		return str.replace("-", File.separator);
	}
}









