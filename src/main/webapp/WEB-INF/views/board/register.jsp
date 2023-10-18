<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript">
//document 내의 모든 요소들이 로딩된 후 실행
$(function(){
	$("#btnHeaders").on("click",function(){
		let boardNo = "10";
		let title = "개똥이의 여행";
		let content = "개똥이 여행을 떠나다";
		let writer = "개똥이";
		
		let data = {"boardNo":boardNo,"title":title
				   ,"content":content,"writer":writer};
		
		//JSON은 object. -> string으로 변환
		//JSON : JavaScript Object Notation(표기법)
		//	 	 클라이언트(브라우저)와 서버 간에 데이터를 교환할 때 활용. 텍스트.
		//		 모든 자바스크립트 객체를 JSON으로 변환하고 JSON을 서버로 보낼 수 있음
		//		 서버에서 받은 JSON을 자바스크립트 객체로 변환할 수 있음
		console.log("data : " + JSON.stringify(data));
		
		//아작나써유..피씨다타써
		//contentType : 보내는타입
		/*
			요청URI : /board/10
			PathVariable : boardNo
			요청방식 : post
		*/
		$.ajax({
			url:"/board/"+boardNo,
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(data),
			type:"post",
			success:function(result){
				console.log("result : " + result);
			}
		});
	});
	
	//도서상세정보(/detail?bookId=1)
	//요청URI : /board/1
	//pathVariable : boardNo -> bookId로 처리예정
	//요청방식 : get
	//dataType(응답 타입) : json
	$("#btnBookDetail").on("click",function(){
		let boardNo = "1";
		
		$.get("/board/"+boardNo,function(result){
			console.log("result : " + JSON.stringify(result));
		});
	});
});
</script>
<h3>register</h3>
<p>
	<!-- 
	요청URI : /board/post?register
	요청방식 : post
	요청파라미터 : register
	 -->
	<form action="/board/post" method="post">
		<input type="text" name="bookId" value="ISBN1234" />
		<!-- 요청URI : /board/post?register -->
		<button type="submit" name="register">Register</button>
		<!-- 요청URI : /board/post?update -->
		<button type="submit" name="update">Update</button>
		<!-- 요청URI : /board/post?delete -->
		<button type="submit" name="delete">Delete</button>
		<!-- 요청URI : /board/post?list -->
		<button type="submit" name="list">List</button>
	</form>
	<!-- /board/post?remove -->
	<form action="/board/post" method="post">
		<a href="/board/get?read" class="btn btn-primary">read</a>
		<button type="button" id="btnHeaders">Headers매핑</button>
		<button type="submit" name="remove">remove</button>
		<button type="button" id="btnBookDetail">bookDetail</button>
	</form>
</p>













