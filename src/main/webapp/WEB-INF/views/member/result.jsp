<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<p>아이디 : ${memberVO.userId}</p>
<p>이름 : ${memberVO.userName}</p>
<p>비밀번호 : ${memberVO.password}</p>
<pre>소개글 : ${memberVO.introduction}</pre>
<p>취미 : 
	<!-- String[] hobbys -->
	<c:forEach var="hobby" items="${memberVO.hobbys}">
		<p>${hobby}</p>
	</c:forEach>
</p>
<p>
	성별 : ${memberVO.gender}
</p>
<p>
	국적 : ${memberVO.nationality}
</p>
<p>
	보유자동차 : 
	<!-- String[] cars -->
	<c:forEach var="car" items="${memberVO.cars}">
		<p>${car}<p>		
	</c:forEach>
</p>




