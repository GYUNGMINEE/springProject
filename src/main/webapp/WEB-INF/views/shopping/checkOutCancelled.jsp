<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/resources/css/bootstrap.min.css">
<title>주문 취소</title>
</head>
<body>
	<!-- ////////////////// header 시작 (menu.jsp) /////////////////// -->
	<%@ include file="menu.jsp" %>
	<!-- ////////////////// header 끝 /////////////////// -->
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">주문 취소</h1>
		</div>
	</div>
	<div class="container">
		<h2 class="alert alert-danger">주문이 취소되었습니다.</h2>
	</div>
	<div class="container"> 
		<p><a href="/shopping/products" class="btn btn-secondary">&laquo;상품 목록</a></p>
	</div>
	<!-- /////////////// footer 시작 (footer.jsp) /////////////// -->
	<%@ include file="footer.jsp" %>
	<!-- /////////////// footer 끝 (footer.jsp) /////////////// -->
</body>
</html>







