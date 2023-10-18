<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 
registerForm.jsp -> ModelController.java -> result.jsp
{userId=gaeddongi,password=java}         (전달안됨x)->골뱅이ModelAttribute(전달O)
-->
<h3>result</h3>
<p>userId : ${userId}</p>
<p>password : ${password}</p>
<p>userId : ${memberVO.userId}</p>
<p>password : ${memberVO.password}</p>