<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Result</h1>
<h4>carArray</h4>
<!-- String[] carArray -->
<c:forEach var="car" items="${carArray}">
	<p>${car}</p>
</c:forEach>
<h4>carList</h4>
<!-- List<String> carList -->
<c:forEach var="car" items="${carList}">
	<p>${car}</p>
</c:forEach>





