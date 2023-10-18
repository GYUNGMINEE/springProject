package kr.or.ddit.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BookInfoVO;

public interface BookInfoService {
	//메소드 시그니처
	//도서 정보 등록(첨부파일도 함께 등록)
	public int addBookPost(BookInfoVO bookInfoVO);

	//도서코드 자동생성
	public String getBookId();

	//도서목록
	public List<BookInfoVO> listBook(Map<String,Object> map);

	//BOOK_INFO 테이블의 전체 행 수
	public int getBookInfoTotal(Map<String,Object> map);

	//도서 상세 
	public BookInfoVO detailBook(String bookId);

	//도서정보 및 첨부파일이 함께 수정
	public int updateBookPost(BookInfoVO bookInfoVO);

	//도서 삭제
	public int deleteBookPost(BookInfoVO bookInfoVO);
	
}
