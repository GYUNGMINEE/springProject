package kr.or.ddit.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.vo.AddressVO;
import kr.or.ddit.vo.CardVO;
import kr.or.ddit.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/model")
@Controller
public class ModelController {
	//1. 모델 객체
	// Model 객체는 뷰(view)에 컨트롤러에서 생성된 데이터를
	//	담아서 전달하는 역할을 함
	// ModelAndView의 Model이라고 생각하면 됨
	
	@GetMapping("/home")
	public String home(Model model) {
		//간단날짜포맷
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		//2023-08-08
		String today = sdf.format(date);
		
		log.info("today : " + today);
		
		model.addAttribute("today", today);
		//forwarding
		//model객체는 redirect에서는 안됨
		return "model/home";
	}
	
	//2. 모델을 통한 데이터 전달
	// Model 객체를 통해서 다양한 데이터를 뷰(View, jsp)에 전달할 수 있음
	
	//2-1) 매개변수에 선언된 Model 객체의 addAttribute() 메서드를 호출하여
	// 데이터를 뷰(View, jsp)에 전달
	//요청URI : /model/read01
	@GetMapping("/read01")
	public String read01(Model model) {
		model.addAttribute("userId", "gaeddongi");
		model.addAttribute("password", "java");
		model.addAttribute("email", "test@test.com");
		//forwarding
		return "model/read01";
	}
	
	//2-2) Model 객체에 자바빈즈 클래스(MemberVO) 객체(memberVO)를 값으로만 전달 시
	//		뷰(jsp)에서 참조할 이름을 클래스명(MemberVO)의 앞글자를 소문자로 변환(memberVO)하여 처리함
	@GetMapping("/read02")
	public String read02(Model model) {
		MemberVO memberVO = new MemberVO();
		
		memberVO.setUserId("gaeddongi");
		memberVO.setPassword("java");
		memberVO.setCoin(100);
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2023);
		cal.set(Calendar.MONTH, 8);
		cal.set(Calendar.DAY_OF_MONTH, 10);
		//private Date   dateOfBirth;
		memberVO.setDateOfBirth(cal.getTime());
		//model.addAttribute("memberVO",memberVO);
		model.addAttribute(memberVO);
		
		return "model/read02";
	}
	
	//2-3) Model 객체에 자바빈즈 클래스 객체를 특정한 이름을
	//		지정하여 전달할 수 있음
	//요청URI : /model/read03
	@GetMapping("/read03")
	public String read03(Model model) {
		MemberVO memberVO = new MemberVO();
		
		memberVO.setUserId("gaeddongi");
		memberVO.setPassword("java");
		memberVO.setCoin(100);
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2023);
		cal.set(Calendar.MONTH, 8);
		cal.set(Calendar.DAY_OF_MONTH, 10);
		//private Date   dateOfBirth;
		memberVO.setDateOfBirth(cal.getTime());
		
		model.addAttribute("user", memberVO);
		//forwarding
		return "model/read03";
	}
	
	//2-4) Model 객체를 통해 배열과 컬렉션 객체를 전달할 수 있음
	//요청URI : /model/read04
	@GetMapping("/read04")
	public String read04(Model model) {
		//배열
		String[] carArray = {"벤츠", "포니"};
		
		//리스트
		List<String> carList = new ArrayList<String>();
		carList.add("페라리");
		carList.add("각그렌저");
		carList.add("장난감자동차");
		
		model.addAttribute("carArray", carArray);
		model.addAttribute("carList", carList);
		
		//forwarding
		return "model/read04";
	}
	
	//2-5) Model 객체를 통해 중첩된 자바빈즈 클래스 객체를 전달할 수 있음
	//요청URI : /model/read05
	@GetMapping("/read05")
	public String read05(Model model) {
		MemberVO memberVO = new MemberVO();
		
		//private AddressVO addressVO;
		AddressVO addressVO = new AddressVO();
		addressVO.setZonecode("12345");
		addressVO.setAddress("대전 중구");
		addressVO.setBuildingName("문화동 123");
		//1 : 1
		memberVO.setAddressVO(addressVO);
		
		//private List<CardVO> cardVOList;
		List<CardVO> cardVOList = new ArrayList<CardVO>();
		
		CardVO cardVO1 = new CardVO();
		cardVO1.setNo("11111");
		cardVO1.setValidMonth("202103");
		cardVOList.add(cardVO1);
		
		CardVO cardVO2 = new CardVO();
		cardVO2.setNo("22222");
		cardVO2.setValidMonth("202205");
		cardVOList.add(cardVO2);
		//1 : N
		memberVO.setCardVOList(cardVOList);
		//뷰에 전달
		model.addAttribute("memberVO", memberVO);
		//forwarding
		return "model/read05";
	}
	
	//3. 골뱅이ModelAttribute 애너테이션
	// : 전달받은 매개변수를 강제로 Model에 담아서 전달할 때 사용
	//요청URI : /model/registerForm
	@GetMapping("/registerForm")
	public String registerForm() {
		//forwarding
		return "model/registerForm";
	}
	
	/*
	 요청URI : /model/register01
	요청파라미터 : {userId=gaeddongi,password=java}
	요청방식 : post
	 */
	//매개변수로 선언 후 forwarding 시 기본적으로 데이터 전달이 안 됨
	@PostMapping("/register01")
	public String register01(String userId, String password) {
		log.info("userId : " + userId);	
		log.info("password : " + password);
		
		//forwarding
		return "model/result";
	}
	/*
	 요청URI : /model/register02
	요청파라미터 : {userId=gaeddongi,password=java}
	요청방식 : post
	 */
	//매개변수에 골뱅이ModelAttribute 애너테이션을 사용하면 
	//forwarding 시 데이터를 전달
	@PostMapping("/register02")
	public String register02(@ModelAttribute("userId") String userId
			, @ModelAttribute("password") String password) {
		log.info("userId : " + userId);	
		log.info("password : " + password);
		
		//forwarding
		return "model/result";
	}
	
	/*
	요청URI : /model/register04
	요청파라미터 : {userId=gaeddongi,password=java}
	요청방식 : post
	 */
	//자바빈즈 규칙(멤버변수,기본생성자,getter/setter메소드)에 맞는 객체는
	//	매개변수로 선언하면 기본적으로 forwarding 시 데이터를 전달함
	@PostMapping("/register04")
	public String register04(@ModelAttribute MemberVO memberVO) {
		log.info("userId : " + memberVO.getUserId());
		log.info("password : " + memberVO.getPassword());
		
		//forwarding
		return "model/result";
	}
}










