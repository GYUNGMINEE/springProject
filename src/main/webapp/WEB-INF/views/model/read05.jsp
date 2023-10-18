<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Result</h1>
<h4>주소</h4>
<!-- model.addAttribute("memberVO", memberVO); -->
<!-- memberVO.setAddressVO(addressVO); -->
<p>${memberVO.addressVO.zonecode}</p>
<p>${memberVO.addressVO.address}</p>
<p>${memberVO.addressVO.buildingName}</p>
<h4>보유카드</h4>
<!-- memberVO.setCardVOList(cardVOList);
memberVO.cardVOList => List<CardVO>
 -->
<c:forEach var="cardVO" items="${memberVO.cardVOList}">
	<p>${cardVO.no} / ${cardVO.validMonth}</p>
</c:forEach>




