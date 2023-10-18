<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/resources/css/bootstrap.min.css">
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<title>배송 정보</title>
<script type="text/javascript">
$(function(){
	$("#autoFill").on("click",function(){
		$("input[name='name']").val("개똥이");
		$("input[name='shippingDate']").val("2023-07-18");
		$("input[name='country']").val("대한민국");
		$("input[name='zipCode']").val("12345");
		$("input[name='addressName']").val("중구 문화동 123");
	});
});
</script>
</head>
<body>
	<!-- ////////////////// header 시작 (menu.jsp) /////////////////// -->
	<%@ include file="menu.jsp" %>
	<!-- ////////////////// header 끝 /////////////////// -->
	
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">
				배송 정보
			</h1>
		</div>
	</div>
	<!-- //////////// 배송 정보 시작 //////////////// -->
	<!-- /shippingInfo.jsp?cartId=EC82E2522CC853501CCB5AEC214438BE -->
	<div class="container">
		<form action="/shopping/processShippingInfo" class="form-horizontal"
			method="post">
			<!-- controller에서 mav.addObject("cartId",세션아이디값) -->
			<input type="hidden" name="cartId" value="${cartId}" />
			<div class="form-group row">
				<label class="col-sm-2">성명</label>
				<div class="col-sm-3">
					<input type="text" name="name" class="form-control" required />
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2">배송일</label>
				<div class="col-sm-3">
					<input type="date" name="shippingDate" 
						class="form-control" required />(yyyy-MM-dd)
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2">국가명</label>
				<div class="col-sm-3">
					<input type="text" name="country"
						class="form-control" required />
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2">우편번호</label>
				<div class="col-sm-3">
					<input type="text" name="zipCode"
						class="form-control" required />
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2">주소</label>
				<div class="col-sm-3">
					<input type="text" name="addressName"
						class="form-control" required />
				</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-10">
					<a href='/shopping/cart?cartId=${cartId}'
						class="btn btn-secondary" role="button">이전</a>
					<input type="submit" class="btn btn-primary" value="등록" />
					<a href="/shopping/checkOutCancelled" class="btn btn-secondary" role="button">취소</a>
					<button type="button" class="badge badge-counter"
						id="autoFill">자동채우기</button>
				</div>
			</div>
		</form>
	</div>
	<!-- //////////// 배송 정보 끝 //////////////// -->
	<!-- /////////////// footer 시작 (footer.jsp) /////////////// -->
	<%@ include file="footer.jsp" %>
	<!-- /////////////// footer 끝 (footer.jsp) /////////////// -->
</body>
</html>







