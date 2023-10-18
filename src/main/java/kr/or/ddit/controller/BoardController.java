package kr.or.ddit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.service.BookService;
import kr.or.ddit.vo.BookInfoVO;
import kr.or.ddit.vo.BookVO;
import lombok.extern.slf4j.Slf4j;

//스프링이 이 클래스를 자바빈으로 등록하여 관리함
//클래스레벨 요청경로매핑
@RequestMapping("/board")
@Slf4j
@Controller
public class BoardController {
	//DI, IoC
	@Autowired
	BookService bookService;
	/*
	 1. 요청 경로 매핑
	  - 요청 경로는 필수
	  - 속성이 하나일 때 속성명 생략 가능
	  - 클래스레벨 요청경로는 기본 경로로 취급
	  - 클래스레벨 요청경로(/board) + 메소드레벨 요청경로(/register) => 최종요청경로(/board/register)
	 */
	/*
	 method생략 : get방식
	 리턴타입이 void : return "board/register";
	 				board폴더에 register.jsp를 forwarding
	 */
	//메서드레벨 요청경로매핑
	//value속성에 요청 경로를 설정
	@RequestMapping(value="/register")
	public void registerForm() {
		log.info("registerForm");
	}
	
	//속성이 하나일 때는 속성명(value) 생략 가능
	@RequestMapping("/modify")
	public void modifyForm() {
		log.info("modifyForm");
		
		// board폴더의 modify.jsp가 forwarding
	}
	
	/* 2. 경로 패턴 매핑
	  	- 요청 경로를 동적으로 표현 가능
	 	- 경로 변수(path variable)에 해당하는 값을 파라미터 변수에 설정
	 */
	// 요청URI : /board/read/12 요청을 처리해보자
	//  board테이블의 12번글
	// boardNo : 경로(path) 변수(variable)
	//1) /board/read?boardNo=12
	//		=> 골뱅이RequestParam("boardNo") int boardNo
	//2) /board/read/12
	//		=> 골뱅이PathVariable("boardNo") int boardNo
	@RequestMapping("/read/{boardNo}")
	public String readForm(@PathVariable("boardNo") int boardNo) {
		//readForm->boardNo : 12
		log.info("readForm->boardNo : " + boardNo);
		
		//board 폴더의 read.jsp를 forwarding
		return "board/read";
	}
	
	// 요청URI : /board/update/27
	// 경로변수명 : boardNo
	// board폴더의 update.jsp를 forwarding해보자
	@RequestMapping("/update/{boardNo}")
	public String update(@PathVariable("boardNo") int boardNo) {
		log.info("update->boardNo : " + boardNo);
		//forwarding
		return "board/update";
	}
	
	/*
	 4. Params 매핑
	  - 요청 파라미터를 매핑 조건으로 지정하는 경우 params 속성을 사용
	  - <button>, <a href..에 따라 호출할 메서드를 바꿔야 할 때 사용함
	 */
	//요청URI : /board/get?register
	//요청방식 : get
	//요청파라미터 : register
	@RequestMapping(value="/get",method=RequestMethod.GET,params="register")
	public String register() {
		log.info("register");
		//forwarding
		return "board/register";
	}
	
	/*
	 요청URI : /board/post?register
	요청방식 : post
	요청파라미터 : register
	 */
	@RequestMapping(value="/post",method=RequestMethod.POST,params="register")
	public String registerPost(String bookId) {
				log.info("registerPost");
				log.info("bookId : " + bookId);
		//forwarding
		return "board/register";
	}
	/*
	 요청URI : /board/post?update
	요청방식 : post
	요청파라미터 : {"update","bookId":"ISBN1234"}
	 */
	@RequestMapping(value="/post",method=RequestMethod.POST,params="update")
	public String updatePost(String bookId) {
		log.info("updatePost");
		log.info("bookId : " + bookId);
		//forwarding
		return "board/register";
	}
	/*
	 요청URI : /board/post?delete
	요청방식 : post
	요청파라미터 : {"delete","bookId":"ISBN1234"}
	 */
	@RequestMapping(value="/post",method=RequestMethod.POST,params="delete")
	public String deletePost(String bookId) {
		log.info("deletePost");
		log.info("bookId : " + bookId);
		//forwarding
		return "board/register";
	}
	/*
	 요청URI : /board/post?list
	요청방식 : post
	요청파라미터 : {"list","bookId":"ISBN1234"}
	 */
	@RequestMapping(value="/post",method=RequestMethod.POST,params="list")
	public String listPost(String bookId) {
		log.info("listPost");
		log.info("bookId : " + bookId);
		//forwarding
		return "board/register";
	}
	
	/*
	 요청URI : /board/get?remove
	요청방식 : get
	요청파라미터 : {"remove"}
	forwarding : board/register.jsp
	 */
	@RequestMapping(value="/get",method=RequestMethod.GET,params="remove")
	public String remove() {
		log.info("remove");
		
		//forwarding
		return "board/register";
	}
	
	/*
	 요청URI : /board/post?remove
	요청방식 : post
	요청파라미터 : {"remove"}
	forwarding : board/register.jsp
	 */
	@RequestMapping(value="/post",method=RequestMethod.POST,params="remove")
	public String removePost() {
		log.info("removePost");
		
		//forwarding
		return "board/register";
	}
	
	/*
	 요청URI : /board/get?read
	요청방식 : get
	요청파라미터 : {"read"}
	forwarding : board/register.jsp
	 */
	@RequestMapping(value="/get",method=RequestMethod.GET,params="read")
	public String read() {
		log.info("read");
		//forwarding
		return "board/register";
	}
	
	
	/*
	 5. Headers 매핑
	 HttpEntity클래스를 상속받아 구현한 클래스 : RequestEntity, ResponseEntity.
	 1) 
	 요청URI : /board/10
	PathVariable : bookId
	요청방식 : post
	
	2)
	도서상세정보(/detail?bookId=1)
	요청URI : /board/1
	pathVariable : boardNo -> bookId로 처리예정
	요청방식 : get
	dataType(응답 타입) : json
	
	consumes : JSON string으로 들어올 때(client -> server)(생략가능)
	produces : JSON string으로 나갈때(server -> client)(생략가능)
	 */
	@RequestMapping(value="/{bookId}",method=RequestMethod.GET,produces="application/json")
	public ResponseEntity<BookVO> modifyPost(@PathVariable("bookId") int bookId,
			BookVO bookVO){
		log.info("modifyPost");
		
		//bookVO{bookId=1,title=null...}
		bookVO.setBookId(bookId);
		
		bookVO = this.bookService.detail(bookVO);
		//bookVO{bookId=1,title=검은태양...}
		
		ResponseEntity<BookVO> entity 
			= new ResponseEntity<BookVO>(bookVO,HttpStatus.OK);
		
		return entity;
	}
	
	//요청URI : /board/detail/ISBN1234
	//pathVariable : bookId
	//data : {"bookId":"ISBN1234", "name":"개똥이", unitPrice.., author.., totalPages..}
	//JSON데이터를 받으려면 골뱅이RequestBody
	//요청방식 : post
	//응답데이터 : SUCCESS
	@RequestMapping(value="/detail/{bookId}",method=RequestMethod.POST
			,consumes="application/json")
	public ResponseEntity<String> detailJSONPost(@PathVariable("bookId") String bookId,
			@RequestBody BookInfoVO bookInfoVO){
		log.info("detailJSONPost");
		log.info("bookId : " + bookId);
		log.info("bookInfoVO : " + bookInfoVO);
		
		ResponseEntity<String> entity 
			= new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		
		return entity;
	}
	
	//아작나써유..피씨다타써
	//요청URL : /board/book
	//JSON데이터 : {"title":"제목","category":"소설","price":"12000","content":"내용"}
	//요청방식 : post
	//,consumes="application/json" : 생략 가능
	@RequestMapping(value="/book",method=RequestMethod.POST,consumes="application/json")
	public ResponseEntity<String> bookInsert(@RequestBody BookVO bookVO){
		log.info("bookVO : " + bookVO);
		
		//bookVO{bookId:0,...}
		int result = this.bookService.insert(bookVO);
		//bookVO{bookId:7,...}
		
		log.info("bookInsert->result : " + result);
		
		ResponseEntity<String> entity = 
				new ResponseEntity<String>(bookVO.getBookId()+"",HttpStatus.OK);
		
		return entity;
	}
}







