<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1>스프링 폼 태그 라이브러리</h1>
<pre>
스프링 폼은 HTML 폼을 표시하기 위한 태그 라이브러리.
스프링 폼을 이용하면 HTML 폼(뷰, jsp)과 자바 객체(Controller의..)를 쉽게 바인딩(연결)할 수 있음
</pre>
<form:form modelAttribute="memberVO" method="post" 
	action="/member/registerForm08Post">
		<!-- path : vo의 멤버변수 -->
		<!-- 숨겨진 필드 요소 hidden -->
<%-- 		<form:hidden path="userId" /> --%>
		<form:input path="userId" />
		<!-- 만약 유효성검증 실패 시 -->
		<font color="red"><form:errors path="userId" /></font>
	<p>
		<!-- 라벨 요소 label -->
		<form:label path="userName">이름 : </form:label>
		<form:input path="userName" />
		<!-- 만약 유효성검증 실패 시 -->
		<font color="red"><form:errors path="userName" /></font>
	</p>
	<p>
		<!-- 패스워드 필드 요소<input type="password" -->
		<form:label path="password">비밀번호 : </form:label>
		<form:password path="password"/>
	</p>
	<p>
		<form:label path="email">이메일 : </form:label>
		<form:input path="email"/>
		<font color="red"><form:errors path="email" /></font>
	</p>
	<p>
		<form:label path="dateOfBirth">생일 : </form:label>
		<form:input path="dateOfBirth" placeholder="2000-01-07" />
		<font color="red"><form:errors path="dateOfBirth" /></font>
	</p>
	<p>
		우편번호 : <form:input path="addressVO.zonecode"/>
			<font color="red"><form:errors path="addressVO.zonecode" /></font><br />
		주소 : <form:input path="addressVO.address"/>
			<font color="red"><form:errors path="addressVO.address" /></font><br />
		상세주소 : <form:input path="addressVO.buildingName" />
			<font color="red"><form:errors path="addressVO.buildingName" /></font>
	</p>
	<p><!-- List<CardVO> cardVOList -->
		카드1-번호 : <form:input path="cardVOList[0].no"/>
				  <font color="red"><form:errors path="cardVOList[0].no" /></font>
		<br />
		카드1-유효연월 : <form:input path="cardVOList[0].validMonth" placeholder="202401" />
				  <font color="red"><form:errors path="cardVOList[0].validMonth" /></font>
		<br />
		<hr />
		카드2-번호 : <form:input path="cardVOList[1].no"/>
				  <font color="red"><form:errors path="cardVOList[1].no" /></font>
		<br />
		카드2-유효연월 : <form:input path="cardVOList[1].validMonth" placeholder="202407"/>
				  <font color="red"><form:errors path="cardVOList[1].validMonth" /></font>
		<br />
	</p>
	<p>
		<!-- 텍스트(text) 영역(area) 요소 -->
		<form:textarea path="introduction"/>
	</p>
	<p>
		<!-- 여러 개의 체크박스 요소 checkboxes -->
		<form:label path="hobbys">취미 : </form:label>
		<br />
<%-- 		<form:checkboxes items="${hobbyMap}" path="hobbys" /> --%>
		<!-- 체크박스 요소  checkbox -->
		<form:checkbox path="hobbys" value="sports" label="sports" />
		<form:checkbox path="hobbys" value="music" label="music" />
		<form:checkbox path="hobbys" value="movie" label="movie" />
	</p>
	<p>
		성별 :
		<!-- 여러 개의 라디오 버튼 요소 radiobuttons
		items : value와 lable을 자동 구성
		path : id와 name을 구성
		 -->
<%-- 		<form:radiobuttons path="gender" items="${genderMap}" /> --%>
		<!-- 라디오 버튼 요소 radiobutton -->
		<form:radiobutton path="gender" value="Male" label="Male" />
		<form:radiobutton path="gender" value="Female" label="Female" />
		<form:radiobutton path="gender" value="Other" label="Other" />
	</p>
	<p>
		국적 : 
		<!-- 셀렉트 박스 요소 select -->
		<form:select path="nationality" items="${nationalityMap}" />
	</p>
	<p>
		자동차 : 
		<!-- select 다중선택 -->
		<form:select path="cars" items="${carsMap}" multiple="true" />
	</p>
	<p>
		<!-- 버튼 요소 : button -->
<%-- 		<form:button name="register">등록</form:button> --%>
		<button type="submit">등록</button>
		<button type="button">목록</button>
		<input type="reset" value="초기화" />
	</p>	
</form:form>




