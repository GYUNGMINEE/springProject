package kr.or.ddit.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.BookVO;
import lombok.extern.slf4j.Slf4j;

//DAO(Data Access Object) 클래스
// 매퍼xml(book_SQL.xml)을 실행함
//Repository 어노테이션 : 데이터에 접근하는 클래스라고
//		스프링에게 알려줌. 스프링이 자바빈으로 등록해서 관리함
@Slf4j
@Repository
public class BookDao {
	
	//DI(Dependency Injection) : 의존성 주입
	//					(sqlSessionTemplate 타입 객체를 BookDao 객체에 주입함)
	//IoC(Inversion of Control) 
	//		: 제어의 역전(new 키워드를 통해 개발자가 직접 객체를 생성하지 않고 스프링이 미리 만들어놓음)
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	//<insert id="insert" parameterType="bookVO">
	public int insert(BookVO bookVO) {
		//book_SQL.xml 파일의 namespace가 book이고, id가 insert인
		//	태그를 찾아서 그 안에 들어있는 sql을 실행함
		// .insert("namespace.id", 파라미터)
		//book.insert 실행 전 bookVO의 bookId는 0
		log.info("bookVO(전) : " + bookVO);
		int result = sqlSessionTemplate.insert("book.insert", bookVO);
		//book.insert 실행 후 bookVO의 bookId는 1(다음값)
		log.info("bookVO(후) : " + bookVO);
		log.info("result : " + result);
		
		return result;
	}
	
	//책 상세보기(p.71)
	//<select id="detail" parameterType="bookVO" resultType="bookVO">
	public BookVO detail(BookVO bookVO) {
		//sqlSessionTemplate : 쿼리를 실행해주는 객체.(root_context.xml에서 bean으로 생성되어있음)
		// .selectOne() 메소드 : 1행을 가져올 때 사용. / .selectList() 메소드 : 결과 집합 목록 반환(다중행)
		//만약, 결과 행 수가 0일때? null을 반환
		//     결과 행 수가 2 이상일 때? TooManyResultsException 예외 발생
		// .selectOne("namespace.id", 파라미터)
		return this.sqlSessionTemplate.selectOne("book.detail",bookVO);
	}
	
	//책 목록
	//<select id="list" parameterType="String" resultType="bookVO">
	public List<BookVO> list(String keyword) {
		//select 결과를 목록으로 받음. .selectList("namespace.id",[파라미터])
		return this.sqlSessionTemplate.selectList("book.list", keyword);
	}
	
	//책 수정
	//<update id="updatePost" parameterType="bookVO">
	public int updatePost(BookVO bookVO) {
		//.update("namespace.id",파라미터)
		return this.sqlSessionTemplate.update("book.updatePost", bookVO);
	}
	
	//책 삭제
//	<delete id="deletePost" parameterType="bookVO">
	public int deletePost(BookVO bookVO) {
		return this.sqlSessionTemplate.delete("book.deletePost", bookVO);
	}
}







