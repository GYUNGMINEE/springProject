package kr.or.ddit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.dao.BookDao;
import kr.or.ddit.service.BookService;
import kr.or.ddit.vo.BookVO;

//서비스 클래스 : 비즈니스 로직(기능 수행)
//스프링 MVC 구조에서 Controller와 DAO를 연결하는 역할
/*
 스프링 프레임워크는 직접 클래스를 호출하는 것을 지양하고,
 인터페이스를 호출하는 것을 좋아함. 
 인터페이스를 통해 접근하는 것을 권장하고 있기 때문에.(확장성)
 
 Impl : implment의 약어
 */
//"스프링아 이 클래스는 서비스 클래스야"라고 알려주면. 프링이가 자바빈으로 등록해줌
@Service
public class BookServiceImpl implements BookService {
	//DI(의존성 주입)
	//IoC(제어의 역전)
	@Autowired
	BookDao bookDao;
	
	//Override 어노테이션 : 메소드를 재정의
	@Override
	public int insert(BookVO bookVO) {
		return this.bookDao.insert(bookVO);
	}
	
	//책 상세
	@Override
	public BookVO detail(BookVO bookVO) {
		return this.bookDao.detail(bookVO);
	}
	
	//책 목록
	@Override
	public List<BookVO> list(String keyword) {
		return this.bookDao.list(keyword);
	}
	
	//책 수정
	@Override
	public int updatePost(BookVO bookVO) {
		return this.bookDao.updatePost(bookVO);
	}
	
	//책 삭제
	@Override
	public int deletePost(BookVO bookVO) {
		return this.bookDao.deletePost(bookVO);
	}
}






