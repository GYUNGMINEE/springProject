<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<!-- 링크 렐르 흐 -->
<link rel="stylesheet" href="/resources/css/bootstrap.min.css" />
<title>상품 등록</title>
<!-- WYSIWYG(What You See Is What You Get) -->
<script type="text/javascript" src="/resources/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="/resources/js/validation.js"></script>
<script type="text/javascript">
//document 내 요소 모두 로딩 후 처리
$(function(){
	console.log("개똥이");
	//이미지 미리보기 시작
	$("#productImage").on("change",handleImg);
	//e : change event 
	function handleImg(e){
		//첨부 파일들
		let files = e.target.files;
		//파일 배열 Object
		let fileArr = Array.prototype.slice.call(files);
		//파일 반복->파일(f)
		fileArr.forEach(function(f){
			if(!f.type.match("image.*")){
				alert("이미지 확장자만 가능합니다.");
				//함수 종료
				return;
			}
			let reader = new FileReader();
			//e : 파일 읽을 때 이벤트
			reader.onload = function(e){
				let img_html = "<img src='" + e.target.result + "' style='width:100%;' />";
				//class="col-sm-5 divImg
				$(".divImg").html(img_html);
			}
			
			//리더로 파일 읽음
			reader.readAsDataURL(f);
		});
	}
});
</script>
</head>
<body>
	<!-- /addProduct.jsp?language=en -->
<%-- 	<fmt:setLocale value='<%=request.getParameter("language")%>'/> --%>
	<!-- language=en => param -->
	<fmt:setLocale value="${param.language}"/>
<%-- 	<c:set var="language" value="${param.language}" /> --%>
<%-- 	<c:set var="language2" value="<%=request.getParameter("language")%>" /> --%>
	<!-- 
	bundle : bundle패키지
	message : message.property
	 -->
	<fmt:bundle basename="bundle.message">
	<!-- header.jsp -->
	<jsp:include page="menu.jsp" />
	
	<!-- 상품 등록 시작 -->
	<div class="jumbotron">
		<!-- container : 이 안에 내용있다 -->
		<div class="container">
			<h1 class="display-3"><fmt:message key="title" /></h1>
		</div>
	</div>
	
	<!-- 상품 등록 폼 필드  시작 -->
	<div class="container">
		<div class="text-right">
			<!-- ?language=ko => addProduct.jsp?language=ko -->
			<a href="?language=ko">Korean</a>|<a href="?language=en">English</a>
			<!-- 로그아웃 페이지 호출 -->
			<a href="logout.jsp" class="btn btn-sm btn-success pull-right">logout</a>
		</div>
		<!-- 폼 필드
		요청URL : /shopping/processAddProduct
		요청방식 : post
		요청파라미터(HTTP파라미터,QueryString) :
			{productId=P1237,pname=에어팟3,unitPrice=200000,description=굿음질,
				manufacturer=Apple,category=Table,unitsInStock=1000,condition=Old}
		 -->
		<form name="newProduct" action="/shopping/processAddProduct"
			class="form-horizontal" method="post" enctype="multipart/form-data">
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="productId" /></label>
				<div class="col-sm-3">
					<input type="text" name="productId"
						class="form-control" required />
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="pname" /></label>
				<div class="col-sm-3">
					<input type="text" name="pname"
						class="form-control" required />
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="unitPrice" /></label>
				<div class="col-sm-3">
					<input type="number" name="unitPrice"
						class="form-control" required />
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="description" /></label>
				<div class="col-sm-3">
					<textarea name="description" rows="3" cols="50" 
						class="form-control"></textarea>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="manufacturer" /></label>
				<div class="col-sm-3">
					<input type="text" name="manufacturer"
						class="form-control" />
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="category" /></label>
				<div class="col-sm-3">
					<input type="text" name="category"
						class="form-control" />
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="unitsInStock" /></label>
				<div class="col-sm-3">
					<input type="number" name="unitsInStock"
						class="form-control" />
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="condition" /></label>
				<div class="col-sm-5">
					<input type="radio" id="condition1" name="condition" value="New" />
						<label for="condition1"><fmt:message key="condition_New" /></label>
					<input type="radio" id="condition2" name="condition" value="Old" />
						<label for="condition2"><fmt:message key="condition_Old" /></label>
					<input type="radio" id="condition3" name="condition" value="Refurbished" />
						<label for="condition3"><fmt:message key="condition_Refurbished" /></label>
				</div>
			</div>
			<!-- 상품 이미지 -->
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="productImage" /></label>
				<div class="col-sm-5">
					<input type="file" id="productImage" name="productImage" 
						class="form-control" />
				</div>
			</div>
			<!-- 상품 이미지 미리보기 -->
			<div class="form-group row">
				<label class="col-sm-2"><fmt:message key="productPreImage" /></label>
				<div class="col-sm-5 divImg"></div>
			</div>
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="button" class="btn btn-primary" value="<fmt:message key="button" />"
						onclick="CheckAddProduct()" />
				</div>
			</div>
		</form>
	</div>
	<!-- 상품 등록 폼 필드  끝 -->
	<!-- footer.jsp -->
	<jsp:include page="footer.jsp" />
	</fmt:bundle>
<script type="text/javascript">
CKEDITOR.replace("description");
</script>
</body>
</html>


