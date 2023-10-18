package kr.or.ddit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.vo.AddressVO;
import kr.or.ddit.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

//스프링이 자바빈으로 등록하여 관리함
@Slf4j
@RequestMapping("/member")
@Controller
public class MemberController {
	//모델에 폼 객체를 추가하지 않으면 오류 발생
	//뷰에 전달할 데이터를 위해 모델을 매개변수로 지정
	@GetMapping("/registerForm01")	
	public String registerForm01(Model model) {
		//모델의 속성명에 memberVO를 지정하고 폼 객체를 모델에 추가함
		model.addAttribute("memberVO", new MemberVO());
		//forwarding
		return "member/registerForm01";
	}
	
	//모델의 속성명과 스프링 폼 태그의 modelAttribute 속성값이 일치해야 함
	@GetMapping("/registerForm02")	
	public String registerForm02(Model model) {
		//모델의 속성명에 memberVO를 지정하고 폼 객체를 모델에 추가함
//		model.addAttribute("gaeddongi", new MemberVO()); //(x)
		model.addAttribute("memberVO", new MemberVO()); //(o)
		//forwarding
		return "member/registerForm02";
	}
	//컨트롤러 메서드의 매개변수로 자바빈즈 객체가 전달이 되면
	// forwarding 시 뷰(registerForm.jsp)로 memberVO를 전달함
	//컨트롤러는 자바빈즈 규칙에 맞는 객체는 뷰로 객체를 전달함
	@GetMapping("/registerForm05")	
	public String registerForm05(MemberVO memberVO) {
		//폼 객체의 속성명은 직접 지정하지 않으면 : 골뱅이ModelAttribute("속성명")를 생략
		//폼 객체의 클래스명의 맨 처음 문자를
		//소문자로 변환하여 처리함
		//forwarding
		return "member/registerForm05";
	}
	
	//골뱅이ModelAttribute 애너테이션으로 폼 객체의 속성명을 직접 지정할 수 있음
	@GetMapping("/registerForm06")	
	public String registerForm06(@ModelAttribute("user") MemberVO memberVO,
			Model model) {
		memberVO.setUserId("gaeddongi");
		memberVO.setUserName("개똥이");
		memberVO.setCoin(100);
		
		model.addAttribute("memberVO", memberVO);
		
		//forwarding
		return "member/registerForm06";
	}
	
	@GetMapping("/registerForm07")
	public String registerForm07(@ModelAttribute("addressVO") AddressVO addressVO) {
		addressVO.setZonecode("12345");
		addressVO.setAddress("대전 중구");
		addressVO.setBuildingName("용전동 223");
		//forwarding
		return "member/registerForm07";
	}
	
	@GetMapping("/registerForm08")
	public String registerForm08(@ModelAttribute("memberVO") MemberVO memberVO,
			Model model) {
		memberVO.setUserId("gaeddongi");
		memberVO.setUserName("개똥이");
		//값을 setting을 하여 뷰에 전달해도 패스워드 필드에 반영되지 않음
		memberVO.setPassword("java");
		memberVO.setIntroduction("안녕하세요 \n 반가워요");
		
		//취미를 미리 체크
		String[] hobbys = {"sports","movie"};
		memberVO.setHobbys(hobbys);
		
		Map<String,String> hobbyMap = new HashMap<String,String>();
		hobbyMap.put("sports","sports");
		hobbyMap.put("music","music");
		hobbyMap.put("movie","movie");
		log.info("hobbyMap : " + hobbyMap);
		
		model.addAttribute("hobbyMap", hobbyMap);
		
		//성별 미리 체크
		memberVO.setGender("Female");
		
		//여러개의 라디오 버튼 요소의 value와 label을 구성
		Map<String,String> genderMap = new HashMap<String, String>();
		genderMap.put("Male", "Male");
		genderMap.put("Female", "Female");
		genderMap.put("Other", "Other");
		log.info("genderMap : " + genderMap);
		
		model.addAttribute("genderMap", genderMap);
		
		//국적
		Map<String,String> nationalityMap = new HashMap<String, String>();
		nationalityMap.put("Korea", "한국");
		nationalityMap.put("Germany", "독일");
		nationalityMap.put("Australia", "오스트레일리아");
		model.addAttribute("nationalityMap", nationalityMap);
		
		//자동차 다중 선택
		Map<String,String> carsMap = new HashMap<String, String>();
		carsMap.put("qm5", "qm5");
		carsMap.put("genesis", "제네시스");
		carsMap.put("sonata", "소나타");
		model.addAttribute("carsMap", carsMap);
		
		//forwarding
		return "member/registerForm08";
	}
	
	//요청파라미터 : {userId=gaeddongi,userName=개똥이,password=java
	//		,email=test@test.com
	//		,introduction=안녕하세요 \n 반가워요,hobbys=[sport,movie]
	//		,gender=Female,nationality=Korea,cars=[sonata,qm5]}
	//입력값 검증을 할 도메인 클래스에 골뱅이Validated를 지정함
	/*
	 입력값 검증 결과
	 입력값 검증 대상의 도메인 클래스 직후에 BindingResult를 정의함
	 BindingResult에는 요청 데이터의 바인딩 에러와 입력값 검증 에러 정보가 저장됨
	 - hasErrors() : 오류 발생 시 true
	 - hasGlobalErrors() : 객체 레벨의 오류 발생 시 true
	 - hasFieldErrors() : 멤버변수 레벨의 오류 발생 시 true
	 - hasFieldErrors(String) : 인수에 지정한 멤버변수에 오류발생 시 true
	 */
	@PostMapping("/registerForm08Post")
	public String registerForm08Post(@Validated MemberVO memberVO,
			BindingResult result, Model model) {
		log.info("memberVO : " + memberVO);
		log.info("result.hasErrors() : " + result.hasErrors());
		//유효성검증 실패
		if(result.hasErrors()) {//true(오류 발생함)
			List<ObjectError> allErrors = result.getAllErrors();//모든
			List<ObjectError> globalErrors = result.getGlobalErrors();//객체
			List<FieldError>  fieldErrors = result.getFieldErrors();	//멤버변수
			
			log.info("allErrors.size() : " + allErrors.size());
			log.info("globalErrors.size() : " + globalErrors.size());
			log.info("fieldErrors.size() : " + fieldErrors.size());
			
			for(int i=0;i<allErrors.size();i++) {
				ObjectError objectError = allErrors.get(i);
				log.info("allError : " + objectError);
			}
			for(int i=0;i<globalErrors.size();i++) {
				ObjectError objectError = globalErrors.get(i);
				log.info("globalError : " + objectError);
			}
			for(int i=0;i<fieldErrors.size();i++) {
				FieldError fieldError = fieldErrors.get(i);
				log.info("fieldErrors : " + fieldError);
				log.info("fieldError.getDefaultMessage() : " + fieldError.getDefaultMessage());
			}
			
			memberVO.setUserId("gaeddongi");
			memberVO.setUserName("개똥이");
			//값을 setting을 하여 뷰에 전달해도 패스워드 필드에 반영되지 않음
			memberVO.setPassword("java");
			memberVO.setIntroduction("안녕하세요 \n 반가워요");
			
			//취미를 미리 체크
			String[] hobbys = {"sports","movie"};
			memberVO.setHobbys(hobbys);
			
			Map<String,String> hobbyMap = new HashMap<String,String>();
			hobbyMap.put("sports","sports");
			hobbyMap.put("music","music");
			hobbyMap.put("movie","movie");
			log.info("hobbyMap : " + hobbyMap);
			
			model.addAttribute("hobbyMap", hobbyMap);
			
			//성별 미리 체크
			memberVO.setGender("Female");
			
			//여러개의 라디오 버튼 요소의 value와 label을 구성
			Map<String,String> genderMap = new HashMap<String, String>();
			genderMap.put("Male", "Male");
			genderMap.put("Female", "Female");
			genderMap.put("Other", "Other");
			log.info("genderMap : " + genderMap);
			
			model.addAttribute("genderMap", genderMap);
			
			//국적
			Map<String,String> nationalityMap = new HashMap<String, String>();
			nationalityMap.put("Korea", "한국");
			nationalityMap.put("Germany", "독일");
			nationalityMap.put("Australia", "오스트레일리아");
			model.addAttribute("nationalityMap", nationalityMap);
			
			//자동차 다중 선택
			Map<String,String> carsMap = new HashMap<String, String>();
			carsMap.put("qm5", "qm5");
			carsMap.put("genesis", "제네시스");
			carsMap.put("sonata", "소나타");
			model.addAttribute("carsMap", carsMap);
			
			return "member/registerForm08";
		}
		//유효성검증 통과
		//forwarding 시 memberVO객체가 뷰로 전달됨
		return "member/result";
	}
}










