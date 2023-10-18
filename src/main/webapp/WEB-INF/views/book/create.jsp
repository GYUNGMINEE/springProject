<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<title>책 등록하기</title>
<script type="text/javascript">
$(function(){
	$("#btnHeaders").on("click",function(){
		//비동기 통신
		//요청URI : /board/book
		//JSON data : {"title":"제목","category":"소설","price":"12000","content":"내용"}
		//bookService.insert(bookVO)를 활용
		//book 테이블에 insert 후
		//bookId데이터를 리턴받아 success의 콜백함수로 result로 받아 console.log로 출력해보자
		let data = {
			 "title":$("input[name='title']").val()
			,"category":$("input[name='category']").val()
			,"price":$("input[name='price']").val()
			,"content":$("textarea[name='content']").val()
		};
		
		console.log("data : " + JSON.stringify(data));
		
		//아작나써유..피씨다타써
		//요청URL : /board/book
		//JSON데이터 : {"title":"제목","category":"소설","price":"12000","content":"내용"}
		//요청방식 : post
		$.ajax({
			url:"/board/book",
			contentType:"application/json;charset:utf-8",
			data:JSON.stringify(data),
			type:"post",
			success:function(result){
				console.log("result : " + result);
			}
		});
	});
});
</script>
</head>
<body>
<h1>책 등록</h1>
<!-- 뷰(View) : 화면을 담당
Client -> 요청(/create) -> Server(create()메소드와 매핑. View에 create.jsp를 포워딩)
		<- 응답(create_jsp.java, create_jsp.class, HTML**) <-
웹 브라우저(크롬)에서 HTML을 렌더링 : 브라우저가 읽어서 해석
 -->
 <!-- 
 요청URL : /create
 요청파라미터 : {title=개똥이의 모험,category=소설,price=10000}
 요청방식 : post
  -->

<form action="/create" method="post">
	<!-- 폼데이터 -->
	<p>제목 : <input type="text" name="title" required /></p>
	<p>카테고리 : <input type="text" name="category" required /></p>
	<p>가격 : <input type="number" name="price" maxLength="10"
				required /></p>
	<p>설명 : <textarea rows="5" cols="30" name="content"></textarea>
	<p>
		<input type="submit" value="저장" />
		<input type="button" value="목록" />
		<input type="button" id="btnHeaders" value="headers매핑" />
	</p>
</form>
</body>
</html>