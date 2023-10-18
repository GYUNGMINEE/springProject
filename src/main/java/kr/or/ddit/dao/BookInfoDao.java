package kr.or.ddit.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.AttachVO;
import kr.or.ddit.vo.BookInfoVO;

//매퍼xml(bookInfo_SQL.xml)을 실행시키는
//DAO(Data Access Object) 클래스
//골뱅이Repository : 데이터에 접근하는 클래스임을 명시
//스프링이 데이터를 관리하는 클래스라고 인지하여 자바빈으로 등록하여 관리
@Repository
public class BookInfoDao {
	//쿼리실행객체를 주입
	//DI / IoC
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	//1-1) <insert id="addBookPost" parameterType="bookInfoVO">
	public int addBookPost(BookInfoVO bookInfoVO) {
		//.insert("namespace.id",파라미터)
		return this.sqlSessionTemplate.insert("bookInfo.addBookPost",bookInfoVO);
	}
	
	//1-2) <insert id="addAttach" parameterType="attachVO">
	public int addAttach(AttachVO attachVO) {
		return this.sqlSessionTemplate.insert("bookInfo.addAttach",attachVO);
	}
	
	//도서코드 자동생성
	//<select id="getBookId" resultType="String">
	public String getBookId() {
		return this.sqlSessionTemplate.selectOne("bookInfo.getBookId");
	}
	
	//도서목록
	//<resultMap type="bookInfoVO" id="bookInfoMap">
	//<select id="listBook" resultMap="bookInfoMap">
	//map : {size=10, currentPage=1, keyword=개똥이}
	public List<BookInfoVO> listBook(Map<String,Object> map){
		return this.sqlSessionTemplate.selectList("bookInfo.listBook", map);
	}
	
	//BOOK_INFO 테이블의 전체 행 수
	//<select id="getBookInfoTotal" resultType="int">
	public int getBookInfoTotal(Map<String,Object> map) {
		return this.sqlSessionTemplate.selectOne("bookInfo.getBookInfoTotal",map);
	}
	
	//도서 상세 
	//<select id="detailBook" parameterType="String" resultType="bookInfoVO">
	public BookInfoVO detailBook(String bookId) {
		return this.sqlSessionTemplate.selectOne("bookInfo.detailBook", bookId);
	}
	
	//1) 도서정보 수정 
	//<update id="updateBookPost" parameterType="bookInfoVO">
	public int updateBookPost(BookInfoVO bookInfoVO) {
		return this.sqlSessionTemplate.update("bookInfo.updateBookPost", bookInfoVO);
	}
	
	//2) BOOK_INFO테이블을 update한 후 ATTACH테이블도 update할 수 있음 
	//<update id="updateAttach" parameterType="attachVO">
	public int updateAttach(AttachVO attachVO) {
		return this.sqlSessionTemplate.update("bookInfo.updateAttach",attachVO);
	}
	
	//도서 삭제
	//<delete id="deleteBookPost" parameterType="bookInfoVO">
	public int deleteBookPost(BookInfoVO bookInfoVO) {
		return this.sqlSessionTemplate.delete("bookInfo.deleteBookPost", bookInfoVO);
	}
}









