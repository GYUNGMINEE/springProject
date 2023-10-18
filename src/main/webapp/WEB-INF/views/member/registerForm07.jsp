<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1>스프링 폼 태그 라이브러리</h1>
<!-- 폼 항목의 공통 속성 
1) path : 폼 항목에 바인딩(연결)되는 폼 객체의 프로퍼티(멤버변수)를 지정
2) disabled : 비활성화(submit 시 데이터가 전송안됨)
3) readonly : 읽기전용(submit 시 데이터가 전송됨)
 -->
<form:form modelAttribute="addressVO" method="post" action="/member/register">
	<p>
		우편번호 : <form:input path="zonecode" />
	</p>
	<p>
		주소 : <form:input path="address" disabled="true" />
	</p>
	<p>
		상세주소 : <input type="text" id="buildingName" readonly="readonly"
			name="buildingName" value="${addressVO.buildingName}" />
	</p>
	<p>
		<form:button name="register">등록</form:button>
	</p>	
</form:form>