package kr.or.ddit.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.util.FileUploadUtils;
import kr.or.ddit.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

//스프링이 자바빈즈(객체)로 관리
@Slf4j
@RequestMapping("/req")
@Controller
public class RequestController {
	//2-1. URL 경로 상의 쿼리 파라미터 정보로부터 요청 데이터를 취득할 수 있음
	//요청URI : /req/request?userId=gaeddongi&password=java
	//요청파라미터(Query String) : userId=gaeddongi&password=java
	@ResponseBody
	@GetMapping("/request")
	public Map<String,String> registerByParameter(String userId, String password) {
		log.info("registerByParameter->userId : " + userId);
		log.info("registerByParameter->password : " + password);
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("userId", userId);
		map.put("password", password);
		
		return map;
	}
	
	//2-2. URL 경로 상의 경로(Path) 변수(Variable)로부터 요청 데이터를 취득할 수 있음
	//요청URI : /req/register/gaeddongi
	//골뱅이@PathVariable(value="userId") 생략 가능
	@ResponseBody
	@GetMapping("/register/{userId}")
	public String registerByPath(@PathVariable(value="userId") String userId) {
		log.info("registerByPath->userId : " + userId);
		
		return userId;
	}
	
	//2-3. HTML 폴 필드명과 컨트롤러 매개변수명이 일치하면 요청 데이터 취득 가능
	//뷰의 경로로써 String을 리턴 시 골뱅이ResponseBody를 사용안함
	@GetMapping("/register01")
	public String register01() {
		log.info("register01");
		
		//forwarding
		return "req/register01";
	}
	
	/**
	 요청URI : /req/register01
	요청파라미터 : {userId=gaeddongi,password=java,coin=100}
	요청방식 : post
	골뱅이RequestParam(value="userId")생략 가능
	 */
	@ResponseBody
	@PostMapping("/register01")
	public String register01Post(String userId) {
		log.info("register01Post->userId : " + userId);
		
		return userId;
	}
	
	//2-4. HTML 폼 필드가 여러 개일 경우에도 컨트롤러 매개변수명이 일치하면
	//		요청 데이터를 취득할 수 있음. 매개변수의 위치는 무관함
	//요청파라미터 : {userId=gaeddongi,password=java,coin=100}
	// HTML 폼 필드값이 숫자일 경우, 컨트롤러 매개변수 타입이 숫자형이면 숫자로 타입변환하여 요청데이터를 취득함
	@ResponseBody
	@PostMapping("/register02")
	public Map<String,Object> register02Post(String userId, int coin, String password) {
		log.info("register02Post->userId : " + userId);
		log.info("register02Post->coin : " + coin);
		log.info("register02Post->password : " + password);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("coin", coin);
		
		return map;
	}
	
	//3. 요청 데이터 처리 애너테이션
	/*
	 PathVariable : URL에서 경로 변수 값을 가져옴 
	 RequestParam : 요청 파라미터 값을 가져옴
	 RequestBody  : 요청 본문 내용(JSON)을 가져옴
	 RequestHeader : 요청 헤더 값을 가져옴
	 CookieValue  : 쿠키 값을 가져옴
	 */
	//3-1) URL 경로 상의 경로 변수가 여러개일 때 PathVariable 애너테이션 사용
	//요청URI : /req/register/gaeddongi/100
	//예상 상황 : gaeddongi회원에게 coin을 100 지급
	@ResponseBody
	@GetMapping("/register/{userId}/{coin}")
	public String registerByPath(@PathVariable("userId") String userId
			, @PathVariable("coin") int coin) {
		log.info("registerByPath->userId : " + userId);
		log.info("registerByPath->coin : " + coin);
		
		return "SUCCESS";
	}
	
	//요청URI : /req/register02
	//요청방식 : get
	@GetMapping("/register02")
	public String register02() {
		//forwarding
		return "req/register02";
	}
	
	/*
	 요청URI : /req/register0201
	요청파라미터 : {userId=gaeddongi,password=java,coin=100}
	요청방식 : post
	 */
	//HTML 폼 필드명과 컨트롤러 매개변수명이 일치(대소문자 구분)하지 않으면 
	//	요청처리할 수 없음
	//그래서, 골뱅이RequestParam 애너테이션을 사용하여 특정한 HTML 폼의 필드명을
	//	지정하여 요청을 처리함
	@ResponseBody
	@PostMapping("/register0201")
	public String register0201(@RequestParam("userId") String username,
			String password, int coin) {
		//register0201->null
		log.info("register0201->username : " + username);
		//password : java
		log.info("register0201->password : " + password);
		//register0201->coin : 100
		log.info("register0201->coin : " + coin);
		
		return "SUCCESS";
	}
	
	//4. 요청 처리 자바빈즈
	// 폼 텍스트 필드 요소값을 자바빈즈 매개변수로 처리함
	//요청URI : /req/register04
	//요청방식 : get
	@GetMapping("/register04")
	public String register04() {
		//forwarding
		return "req/register04";
	}
	
	/*
	 요청URI : /req/register0401
	요청파라미터 : {userId=gaeddongi,password=java,coin=100}
	요청방식 : post
	 */
	@ResponseBody
	@PostMapping("/register0401")
	public MemberVO register0401(String userId, MemberVO memberVO, int coin) {
		log.info("userId : " + memberVO.getUserId());
		log.info("password : " + memberVO.getPassword());
		log.info("coin : " + memberVO.getCoin());
		//폼 텍스트 필드 요소값을 자바빈즈 매개변수와 기본 데이터 타입인 정수 타입 매개변수로 처리함
		log.info("coin : " + coin);
		//여러 개의 폼 텍스트 필드 요소값을 매개변수 순서와 상관없이 매개변수명을 기준으로 처리함
		log.info("userId : " + userId);
		
		return memberVO;
	}
	
	//5. Date 타입 처리
	//5-1)요청URI : /req/registerByGet01?userId=gaeddongi&dateOfBirth=1234
	//  dateOfBirth=1234 : 날짜 문자열 형식에 맞지 않음. Date 타입으로 변환 실패
	//5-2)요청URI : /req/registerByGet01?userId=gaeddongi&dateOfBirth=2023-08-07
	//  dateOfBirth=2023-08-07 : 날짜 문자열 형식에 맞지 않음. Date 타입으로 변환 실패
	//5-3)요청URI : /req/registerByGet01?userId=gaeddongi&dateOfBirth=20230807
	//  dateOfBirth=20230807 : 날짜 문자열 형식에 맞지 않음. Date 타입으로 변환 실패
	//5-3)요청URI : /req/registerByGet01?userId=gaeddongi&dateOfBirth=2023/08/07
	//  dateOfBirth=2023/08/07 : 날짜 문자열 형식에 맞아서 Date 타입으로 변환 성공!
	@ResponseBody
	@GetMapping("/registerByGet01")
	public String registerByGet01(String userId, Date dateOfBirth) {
		
		log.info("userId : " + userId);
		//dateOfBirth : Mon Aug 07 00:00:00 KST 2023
		log.info("dateOfBirth : " + dateOfBirth);
		
		//forwarding이 아닌 String타입의 데이터
		return "SUCCESS";
	}
	//5-1)요청URI : /req/registerByGet02?userId=gaeddongi&dateOfBirth=1234
	//  dateOfBirth=1234 : 날짜 문자열 형식에 맞지 않음. Date 타입으로 변환 실패
	//5-2)요청URI : /req/registerByGet02?userId=gaeddongi&dateOfBirth=2023-08-07
	//  dateOfBirth=2023-08-07 : 날짜 문자열 형식에 맞지 않음. Date 타입으로 변환 실패
	//5-3)요청URI : /req/registerByGet02?userId=gaeddongi&dateOfBirth=20230807
	//  dateOfBirth=20230807 : 날짜 문자열 형식에 맞지 않음. Date 타입으로 변환 실패
	//5-3)요청URI : /req/registerByGet02?userId=gaeddongi&dateOfBirth=2023/08/07
	//  dateOfBirth=2023/08/07 : 날짜 문자열 형식에 맞아서 Date 타입으로 변환 성공!
	@ResponseBody
	@GetMapping("/registerByGet02")
	public String registerByGet02(MemberVO memberVO) {
		
		log.info("userId : " + memberVO.getUserId());
		//dateOfBirth : Mon Aug 07 00:00:00 KST 2023
		log.info("dateOfBirth : " + memberVO.getDateOfBirth());
		
		//forwarding이 아닌 String타입의 데이터
		return "SUCCESS";
	}
	
	/**
	 요청URI : /req/register0402
	요청파라미터 : {userId=gaeddongi,password=java,coin=100,dateOfBirth=2023-08-07,
				gender=female, nationality=Korea, cars={genesisGV70,perari296GTB},
				homeList={home03,home0F}}
	요청방식 : post
	 */
	//골뱅이DateTimeFormat(pattern="yyyy-MM-dd')를 통해 Date타입으로 변환할 수 있음
	//ArrayList<String> homeList : 안됨
	@ResponseBody
	@PostMapping("/register0402")
	public String register0402(String userId, int coin, String gender
			, @DateTimeFormat(pattern="yyyy-MM-dd") Date dateOfBirth
			, String nationality, String[] cars, ArrayList<String> homeList
			, String[] hobbys, String developer
			, boolean foreigner, String introduction
			, MemberVO memberVO) {
		log.info("userId : " + userId);
		log.info("coin : " + coin);
		//dateOfBirth : Mon Aug 07 00:00:00 KST 2023
		log.info("dateOfBirth : " + dateOfBirth);
		log.info("gender : " + gender);
		log.info("nationality : " + nationality);
		//보유자동차 String[] cars		----------------------(O)
		for(String car : cars) {
			log.info("car : " + car);			
		}
		//집 ArrayList<String> homeList : List매개변수는 안됨--(X)
		homeList = memberVO.getHomeList();
		if(homeList!=null) {
			for(int i=0;i<homeList.size();i++) {
				log.info("home : "+ homeList.get(i));
			}
		}
		for(String hobby : hobbys) {
			log.info("hobby : " + hobby);
		}
		//개발자 여부
		log.info("developer : " + developer);
		//외국인 여부
		log.info("foreigner : " + foreigner);
		//자기소개
		log.info("introduction : " + introduction);
		
		//cardVOList=
		//[
		//CardVO(no=1, validMonth=202101), 
		//CardVO(no=2, validMonth=202201), 
		//CardVO(no=3, validMonth=202301)
		//]
		
		log.info("memberVO : " + memberVO);
		
		return "SUCCESS";
	}
	
	//8. 파일업로드 폼 방식 요청 처리
	// 파일업로드 폼 파일 요소값을 스프링MVC가 지원하는 MultipartFile 매개변수로 처리함
	@GetMapping("/registerFile01")
	public String registerFile01() {
		//forwarding
		return "req/registerFile01";
	}
	
	/*
	요청URI : /req/registerFile01Post
	요청파라미터 : {picture=파일객체}
	요청방식 : post
	 */
	@ResponseBody
	@PostMapping("/registerFile01Post")
	public String registerFile01Post(MultipartFile picture) {
		log.info("원본파일명 : " + picture.getOriginalFilename());
		log.info("파일크기 : " + picture.getSize());
		log.info("MINE타입 : " + picture.getContentType());
		//뷰경로가 아닌 데이터->ResponseBody
		return "SUCCESS";
	}
	
	@GetMapping("/registerFile02")
	public String registerFile02() {
		//forwarding
		return "req/registerFile02";
	}
	
	/*
	 요청URI : /req/registerFile02Post
	요청파라미터 : {userId=gaeddongi&password=java&picture=파일객체}
	요청방식 : post
	 */
	@ResponseBody
	@PostMapping("/registerFile02Post")
	public String registerFile02Post(String userId, String password,
			MultipartFile picture) {
		
		log.info("userId : " + userId);
		log.info("password : " + password);
		
		log.info("원본파일명 : " + picture.getOriginalFilename());
		log.info("파일크기 : " + picture.getSize());
		log.info("MINE타입 : " + picture.getContentType());
		
		return "SUCCESS";
	}
	
	@GetMapping("/registerFile03")
	public String registerFile03() {
		//forwarding
		return "req/registerFile03";
	}
	
	/*
	 요청URI : /req/registerFile03Post
	요청파라미터 : {userId=gaeddongi&password=java&picture=파일객체}
	요청방식 : post
	 */
	//자바빈즈 매개변수를 통해 파일업로드 폼 파일 요소값과 텍스트필드 요소값을 처리함
	@ResponseBody
	@PostMapping("/registerFile03Post")
	public String registerFile03Post(MemberVO memberVO) {
		//텍스트필드 요소값
		log.info("userId : " + memberVO.getUserId());
		log.info("password : " + memberVO.getPassword());
		//파일업로드 폼 파일 요소값
		MultipartFile picture = memberVO.getPicture();
		
		log.info("원본파일명 : " + picture.getOriginalFilename());
		log.info("파일크기 : " + picture.getSize());
		log.info("MINE타입 : " + picture.getContentType());
		
		return "SUCCESS";
	}
	
	@GetMapping("/registerFile04")
	public String registerFile04() {
		//forwarding
		return "req/registerFile04";
	}
	
	/*
	 요청URI : /req/registerFile04Post
	요청파라미터 : {userId=gaeddongi&password=java&picture=파일객체}
	요청방식 : post
	 */
	//자바빈즈 매개변수를 통해 파일업로드 폼 파일 요소값과 텍스트필드 요소값을 처리함
	@ResponseBody
	@PostMapping("/registerFile04Post")
	public String registerFile04Post(MemberVO memberVO) {
		//텍스트필드 요소값
		log.info("userId : " + memberVO.getUserId());
		log.info("password : " + memberVO.getPassword());
		//파일업로드 폼 파일 요소값
		MultipartFile[] pictures = memberVO.getPictures();
		
		//파일업로드 처리
		String result = FileUploadUtils.multiUpload(pictures);
		log.info("result : " + result);
		
		return "SUCCESS";
	}
	
	@GetMapping("/registerFile05")
	public String registerFile05() {
		//forwarding
		return "req/registerFile05";
	}
	
	/*
	<form>
	<input type="file" name="file" />
	<input type="text" name="userId" value="gaeddongI"/>
	<input type="text" name="password" value="java"/>
	</form>
	 */
	@ResponseBody
	@PostMapping("/registerFile05Post")
	public String registerFile05Post(String userId, String password,
			MultipartFile file) {
		log.info("userId : " + userId);
		log.info("password : " + password);
		log.info("파일명 : " + file.getOriginalFilename());
		//뷰경로가 아닌 데이터
		return "SUCCESS";
	}
	
	@GetMapping("/registerFile06")
	public String registerFile06() {
		//forwarding
		return "req/registerFile06";
	}
	
	// /req/registerFile06Post
	@ResponseBody
	@PostMapping("/registerFile06Post")
	public String registerFile06Post(MemberVO memberVO) {
		
		log.info("userId : " + memberVO.getUserId());
		log.info("password : " + memberVO.getPassword());
		
		MultipartFile[] pictures = memberVO.getPictures();
		//파일업로드 수행
		String result = FileUploadUtils.multiUpload(pictures);
		if(result.equals("1")) {		
			return "SUCCESS";
		}else {
			return "FAIL";
		}
	}
}











