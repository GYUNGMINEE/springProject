<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 
요청URI : /req/registerFile04Post
요청파라미터 : {userId=gaeddongi&password=java&pictures=파일객체들}
요청방식 : post
 -->
<form action="/req/registerFile04Post" method="post"
	enctype="multipart/form-data">
	<!-- 텍스트 필드 요소 -->
	<p><input type="text" name="userId" value="gaeddongi" /></p>
	<p><input type="text" name="password" value="java" /></p>
	<!-- 파일업로드 폼 파일 요소 -->
	<p><input type="file" name="pictures" multiple /></p>
	<p><input type="submit" value="파일업로드" /></p>
</form>
