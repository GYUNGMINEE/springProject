<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 
요청URI : /req/registerFile01Post
요청파라미터 : {picture=파일객체}
요청방식 : post
 -->
<form action="/req/registerFile01Post" method="post"
	enctype="multipart/form-data">
	<p><input type="file" name="picture" /></p>
	<p><input type="submit" value="파일업로드" /></p>
</form>