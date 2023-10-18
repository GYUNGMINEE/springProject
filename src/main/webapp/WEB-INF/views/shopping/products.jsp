<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<!-- 링크 렐르 흐 -->
<link rel="stylesheet" href="/resources/css/bootstrap.min.css" />
<title>상품 목록</title>
</head>
<body>
<!-- scope : 영역
토르의 활동 영역 : 대전                   / 대한민국                 / 아시아                             / 지구
scope영역       : page(동일jsp) / request(동일요청)/ session(동일웹브라우저) / application(모든웹브라우저)
scope객체        : pageContext  / request        / session             / application  
 -->
	<!-- ////////////// body 시작 /////////////// -->
	<div class="jumbotron">
		<!-- container : 이 안에 내용있다. -->
		<div class="container">
			<h1 class="display-3">상품 목록</h1>
		</div>
	</div>
	<!-- container : 이 안에 내용있다 -->
	<div class="container">	
		<!-- 행 별 처리 -->
		<div class="row" style="justify-content:right;margin:0 0 30px 0;">
			<a href="/shopping/addProduct" class="btn btn-primary">상품 추가</a>
		</div>
		<div class="row" align="center">
			<!-- List<ProductVO> listOfProducts
			mav.addObject("listOfProducts", select결과데이터)
			 -->
			<c:forEach var="product" items="${listOfProducts}" varStatus="stat"> 
			<div class="col-md-4"><!-- 열 별 처리 -->
				<img src="/resources/images/${product.filename}"
					style="width:100%;" alt="${product.pname}" 
					title="${product.pname}"
				 />
				<h3>${product.pname}</h3>
				<p>${product.description}</p>
				<p>${product.unitPrice}원</p>
				<p><a href="/shopping/product?productId=${product.productId}"
						class="btn btn-secondary" role="button">
				 상세정보</a></p>
			</div>
			</c:forEach>
		</div>
	</div>
	<!-- ////////////// body 끝 /////////////// -->
</body>
</html>









