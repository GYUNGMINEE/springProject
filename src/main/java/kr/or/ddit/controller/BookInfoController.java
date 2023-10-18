package kr.or.ddit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.service.BookInfoService;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.vo.BookInfoVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BookInfoController {
	//DI / IoC
	@Autowired
	BookInfoService bookInfoService;
	
	//요청URI : /bookInfo/addBook
	@RequestMapping(value="/bookInfo/addBook",method=RequestMethod.GET)
	public ModelAndView addBook(ModelAndView mav) {
		//forwarding
		//tiles-config.xml에서 <definition name="*       /*"
		//									   bookInfo/addBook
		//						/WEB-INF/views/{1}     /{2}    .jsp
		mav.setViewName("bookInfo/addBook");
		
		return mav;
	}
	
	/** 도서 등록
	 요청URL : /bookInfo/addBookPost
	요청파라미터 : {bookId=ISBN1234,name=...}
	요청방식 : post
	 */
	@ResponseBody
	@RequestMapping(value="/bookInfo/addBookPost",method=RequestMethod.POST)
	public int addBookPost(ModelAndView mav,
			@ModelAttribute BookInfoVO bookInfoVO) {
		//BookInfoVO(author=개똥이, bookId=ISBN1234, category=소설, condition=신규도서
		//, description=<p>내용부분</p>
		//, name=개똥이의 여행, publisher=삼성출판사, releaseDate=2023-07-26
		//, totalPages=100, unitPrice=10000, unitsInStock=0
		//, bookImage=org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@358901e0)
		log.info("bookInfoVO : " + bookInfoVO);
		
		int result = this.bookInfoService.addBookPost(bookInfoVO);
		log.info("addBookPost->result : " + result);
		
		//forwarding
//		mav.setViewName("bookInfo/detailBook");
		
//		return mav;
		return result;
	}
	
	/** 도서코드 자동생성
	 * 요청URI : /bookInfo/getBookId
	 * 요청방식 : post
	 * 골뱅이ResponseBody : JSON데이터로 응답
	 */
	@ResponseBody
	@RequestMapping(value="/bookInfo/getBookId",method=RequestMethod.POST)
	public String getBookId() {
		//도서코드 자동생성
		String bookId = this.bookInfoService.getBookId();
		
		log.info("bookId : " + bookId);
		
		return bookId;
	}
	
	//도서 목록
	//요청URI : /bookInfo/listBook?currentPage=3&size=10
	//요청URI : /bookInfo/listBook
	//요청URI : /bookInfo/listBook?currentPage=3
	//요청URI : /bookInfo/listBook?size=10
	//vo : 골뱅이ModelAttribute
	//map, String.. : 골뱅이 RequestParam
	/* 검색
	 요청URI : /bookInfo/listBook?size=10&keyword=개똥이
	요청파라미터(HTTP파라미터=QueryString) : size=10&keyword=개똥이
	요청방식 : get
	 */
	@RequestMapping(value="/bookInfo/listBook",method=RequestMethod.GET)
	public ModelAndView listBook(ModelAndView mav,
			@RequestParam(value="currentPage",required=false,defaultValue="1") int currentPage,
			@RequestParam(value="size",required=false,defaultValue="10") int size,
			@RequestParam(value="keyword",required=false,defaultValue="") String keyword
			) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("currentPage", currentPage);//기본 : 1
		map.put("size",size);//기본 10
		map.put("keyword", keyword);	//기본 ""
		//map : {size=10, currentPage=1, keyword=개똥이}
		log.info("listBook->map : " + map);
		
		List<BookInfoVO> data = this.bookInfoService.listBook(map);
		
		log.info("data : " + data);
		
		//BOOK_INFO 테이블의 전체 행 수
		int total = this.bookInfoService.getBookInfoTotal(map);
		
		log.info("total : " + total);
		
		//Model
		//페이징 처리한 data : ArticlePage활용
		mav.addObject("data", new ArticlePage<BookInfoVO>(total, currentPage, size, data));
		
		//forwarding
		mav.setViewName("bookInfo/listBook");
		
		return mav;
	}
	
	/*
	 /bookInfo/detailBook?bookId=ISBN1234
	 controller에서 요청URI를 받아서 요청파라미터 정보를 log.info로 출력해보자.
	forwarding은 bookInfo/detailBook.jsp
	 */
	@RequestMapping(value="/bookInfo/detailBook",method=RequestMethod.GET)
	public ModelAndView detailBook(ModelAndView mav,
		String bookId) {
		//bookId : ISBN1456
		log.info("bookId : " + bookId);
		
		//SELECT문 실행 결과를 처리해보자
		BookInfoVO bookInfoVO = this.bookInfoService.detailBook(bookId);
		log.info("bookInfoVO : " + bookInfoVO);
		
		//Model
		mav.addObject("data", bookInfoVO);
		
		//forwarding
		mav.setViewName("bookInfo/detailBook");
		
		return mav;
	}
	
	/**
	 수정 처리(BOOK_INFO테이블 및 ATTACH 테이블을 수정)
	요청URI : /bookInfo/updateBookPost
	요청파라미터 : {bookId=ISBN1234,name=...}
	요청방식 : post
	*/
	@RequestMapping(value="/bookInfo/updateBookPost",method=RequestMethod.POST)
	public ModelAndView updateBookPost(ModelAndView mav,
			BookInfoVO bookInfoVO) {
		log.info("bookInfoVO : " + bookInfoVO);
		
		int result = this.bookInfoService.updateBookPost(bookInfoVO);
		log.info("updateBookPost->result : " + result);
		
		//redirect
		mav.setViewName("redirect:/bookInfo/detailBook?bookId="+bookInfoVO.getBookId());
		
		return mav;
	}
	
	//요청URI : /bookInfo/deleteBookPost
	//요청파라미터 : {bookId=ISBN1234,name=...}
	//요청방식 : post
	@RequestMapping(value="/bookInfo/deleteBookPost",method=RequestMethod.POST)
	public ModelAndView deleteBookPost(ModelAndView mav,
			BookInfoVO bookInfoVO) {
		//bookId : ISBN1453
		log.info("bookId : " + bookInfoVO.getBookId());
		
		int result = this.bookInfoService.deleteBookPost(bookInfoVO);
		log.info("deleteBookPost->result : " + result);
		
		//redirect
		mav.setViewName("redirect:/bookInfo/listBook");
		
		return mav;
	}
}











