<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/resources/css/bootstrap.min.css" />
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<title>주문 정보</title>
<script type="text/javascript">
//document 내의 모든 요소들이 로딩된 후에 실행
$(function(){
	$("#idThank").on("click",function(){
		//EL Data => J/S Data
		let shippingDate = "${map.shippingDate}";
		let cartId = "${map.cartId}";
		
		console.log("shippingDate : " + shippingDate);
		console.log("cartId : " + cartId);
		
		//가상의 form 태그 생성 <form></form>
		let formData = new FormData();
		//<input type="text" name="shippingDate" ...>
		formData.append("shippingDate",shippingDate);
		//<input type="text" name="cartId"..>
		formData.append("cartId",cartId);
		//아작났어유ㅠㅠ 피씨다타써
		//contentType : 보내는타입
		//dataType : 응답타입(json, text)
		$.ajax({
			url:"/shopping/thankCustomer",
			processData:false,
			contentType:false,
			data:formData,
			type:"post",
			dataType:"text",
			success:function(result){
				//result : success
				console.log("result : " + result);
		
				if(result=="success"){
					location.href="/shopping/thankCustomer";
				}else{
					console.log("오류발생");
				}
			}
		});
	});
});
</script>
</head>
<body>
	<!-- include 액션 태그 -->
	<jsp:include page="menu.jsp" />

	<!-- 주문 정보 시작 -->
	<div class="jumbotron">
		<!-- container : 이 안에 내용있다 -->
		<div class="container">
			<h1 class="display-3">주문 정보</h1>
		</div>
	</div>
	
	<div class="container col-8 alert alert-info">
		<div class="text-center">
			<h1>영수증</h1>
		</div>
		<!-- 고객이 작성한 배송 관련 정보(cookie를 활용) -->
		<div class="row justify-content-between">
			<strong>배송 주소</strong><br />
			성명 : ${map.name}<br />
			우편번호 : ${map.zipCode}<br />
			주소 : ${map.addressName}&nbsp;${map.country}
		</div>
		<div class="col-4" align="right">
			<p>
				<em>배송일 : ${map.shippingDate}</em>
			</p>
		</div>
		<!-- 상품 정보 시작(session 활용) -->
		<div>
			<table class="table table-hover">
				<tr>
					<th class="text-center">상품명</th>
					<th class="text-center">#</th>
					<th class="text-center">가격</th>
					<th class="text-center">소계</th>
				</tr>
				<!-- cartlist : 장바구니(세션) 
				session.setAttribute("cartlist",ArrayList<ProductVO>타입의 객체)
				-->
				<c:forEach var="productVO" items="${sessionScope.cartlist}" varStatus="stat">
					<c:set var="total" value="${total + productVO.unitPrice*productVO.quantity}" />
				<tr>
					<td class="text-center"><em>${productVO.pname}</em></td>
					<td class="text-center">${productVO.quantity}</td>
					<td class="text-center">
						<!-- 샵 쉽표 샵샵샵 -->
						<fmt:formatNumber value="${productVO.unitPrice}" pattern="#,###" /> 
					</td>
					<td class="text-center">
						<fmt:formatNumber value="${productVO.unitPrice*productVO.quantity}" 
							pattern="#,###" /> 
					</td>
				</tr>
				</c:forEach>
				<tr>
					<td></td>
					<td></td>
					<td class="text-right"><strong>총액:</strong></td>
					<td class="text-center text-danger">
						<strong><fmt:formatNumber value="${total}" pattern="#,###" /> </strong>원
					</td>
				</tr>
			</table>
			
			<a href="/shopping/shippingInfo?cartId=${map.cartId}" 
				class="btn btn-secondary" role="button">이전</a>
			<!-- shippingDate, cartId -->
			<a href="#"
				class="btn btn-success" role="button" id="idThank">주문 완료</a>
			<a href="/shopping/checkOutCancelled" 
				class="btn btn-secondary" role="button">취소</a>
		</div>
	</div>



	<jsp:include page="footer.jsp" />
</body>
</html>






