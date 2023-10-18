<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 
요청URI : /req/register0201
요청파라미터 : {userId=gaeddongi,password=java,coin=100}
요청방식 : post
-->
<form action="/req/register0201" method="post">
	<p>userId : <input type="text"   name="userId" value="gaeddongi" /></p>
	<p>password : <input type="text" name="password" value="java" /></p>
	<p>coin : <input type="text"     name="coin" value="100" /></p>
	<p>
		<input type="submit" value="register0201" />
	</p>
</form>