<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/resources/css/bootstrap.min.css" />
<title>장바구니</title>
</head>
<body>
	<!-- include 액션 태그 -->
	<jsp:include page="menu.jsp" />
	
	<!-- 장바구니 시작 -->
	<div class="jumbotron">
		<!-- container : 이 안에 내용있다 -->
		<div class="container">
			<h1 class="display-3">장바구니</h1>
		</div>
	</div>

	<!-- 장바구니 상세 내역 시작 -->
	<div class="container">
		<div class="row">
			<table width="100%;">
				<tr>
					<td align="left">
						<!-- 장바구니 자체를 삭제 -->
						<a href="/shopping/deleteCart?cartId=${cartId}"
							class="btn btn-danger">삭제하기</a>
					</td>
					<td align="right">
						<a href="/shopping/shippingInfo?cartId=${cartId}"
							class="btn btn-success">주문하기</a> 
					</td>
				</tr>
			</table>
		</div>
		<!-- padding-top : 영역의 위쪽 여백을 50픽셀로 줌 -->
		<div style="padding-top:50px;">
			<table class="table table-hover">
				<tr>
					<th>상품</th><th>가격</th><th>수량</th>
					<th>금액</th><th>비고</th>
				</tr>
<!-- 장바구니가 비어있다면 -->
<c:if test="${cartList==null}">
				<tr style="text-align:center;">
					<td colspan="5" style="text-align:center;">
						장바구니에 상품이 없습니다.
					</td>
				</tr>
</c:if>
<!-- 장바구니에 상품목록이 있다면.. -->
<c:if test="${cartList!=null}">				
				<!-- List<ProductVO> cartList 
				딸러{cartList}
				1. 각 scope(영역) scan
					1) pageScope.cartList : pageContext.setAttribute("cartList")의 결과데이터를 찾음
					2) requestScope.cartList : request.setAttribute("cartList")의 결과데이터를 찾음
					3) sessionScope.cartList : session.setAttribute("cartList")의 결과데이터를 찾음
					4) applicationScope.cartList : application.setAttribute("cartList")의 결과데이터를 찾음
				2. 1.에 없을 때 model.addObject("cartList",결과데이터) scan
				3. 1.그리고 2.이 없으면 null
				-->
				<c:forEach var="productVO" items="${cartList}" varStatus="stat">
					<!-- 금액 : 가격 x 수량 -->
					<c:set var="amount" value="${productVO.unitPrice*productVO.quantity}" />
					<!-- total = total + amount -->
					<c:set var="total" value="${total+amount}" />
				<tr>
					<td>${productVO.productId}-${productVO.pname}</td>
					<td>${productVO.unitPrice}</td>
					<td>${productVO.quantity}</td>
					<td>${amount}</td>
					<td>
						<a href="/shopping/removeCart?productId=${productVO.productId}"
							class="badge badge-danger">삭제</a>
					</td>
				</tr>	
				</c:forEach>
				<tr>
					<th></th><th></th><th>총액</th>
					<th>${total}</th><th></th>
				</tr>
</c:if>
			</table>
			<a href="/shopping/products" class="btn btn-secondary">&laquo;쇼핑 계속하기</a>
		</div>
	</div>
	<!-- 장바구니 상세 내역 끝 -->

	<jsp:include page="footer.jsp" />
</body>
</html>



