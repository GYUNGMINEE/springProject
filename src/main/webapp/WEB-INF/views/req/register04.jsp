<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<!-- 
요청URI : /req/register0402
요청파라미터 : {userId=gaeddongi,password=java,coin=100,dateOfBirth=2023-08-07,
			gender=female}
요청방식 : post
-->
<!-- <form action="/req/register0401" method="post"> -->
<form action="/req/register0402" method="post">
	<p>userId : <input type="text"   name="userId" value="gaeddongi" /></p>
	<p>password : <input type="text" name="password" value="java" /></p>
	<p>coin : <input type="text"     name="coin" value="100" /></p>
	<p>dateOfBirth : <input type="date" name="dateOfBirth" value="2023-08-07" /></p>
	<p>
		gender : <br />
		<p>
			<input type="radio" id="gender1" name="gender" value="male" checked />
			<label for="gender1">Male</label>
		</p>
		<p>
			<input type="radio" id="genger2" name="gender" value="female" />
			<label for="genger2">FeMale</label>
		</p>
		<p>
			<input type="radio" id="gender3" name="gender" value="other" />
			<label for="gender3">Other</label>
		</p>
	</p>
	<p>
		국적 : <select name="nationality">
				<option value="Korea" selected>대한민국</option>
				<option value="Germany">독일</option>
				<option value="Australia">호주</option>
				<option value="Canada">캐나다</option>
			  </select>
	</p>
	<p>
		자동차 : <select name="cars" multiple>
				<option value="audi">Audi</option>
				<option value="explorer">Explorer</option>
				<option value="genesisGV70" selected>GenesisGV70</option>
				<option value="perari296GTB" selected>perari296GTB</option>
			   </select>
	</p>
	<p>
		집 :    <select name="homeList" multiple>
				<option value="home01">서울현대2차</option>
				<option value="home02" selected>시그니엘레지던스320</option>
				<option value="home03" selected>반포자이 138동</option>
				<option value="home0F">해운대아이파크30</option>
			   </select>
	</p>
	<p>
		취미 : <input type="checkbox" id="hobbys1" name="hobbys" value="drive" checked />
				<label for="hobbys1">Drive</label><br />
			 <input type="checkbox" id="hobbys2" name="hobbys" value="takkondo" />
			 	<label for="hobbys2">태권도</label><br />
			 <input type="checkbox" id="hobbys3" name="hobbys" value="movie" />
			 	<label for="hobbys3">영화보기</label><br />
			 <input type="checkbox" id="hobbys4" name="hobbys" value="sleep" />
			 	<label for="hobbys4">취침</label>
	</p>
	<p>
		<!-- Y : String타입 => Y(체크) 또는 null(비체크) -->
		개발자 여부 : <input type="checkbox" name="developer" value="Y" />
	</p>	
	<p>
		<!-- false : Boolean 타입 => value속성을 작성하지 말자=> true(체크) 또는 false(비체크) -->
		외국인 여부 : <input type="checkbox" name="foreigner" />
	</p>
	<p>
		우편번호 : <input type="text" name="addressVO.zonecode" placeholder="우편번호" readonly />
		<button type="button" id="btnPostNum">우편번호검색</button><br />
		<input type="text" name="addressVO.address" placeholder="주소" /><br />
		<input type="text" name="addressVO.buildingName" placeholder="상세주소" />
	</p>
	<p>
		카드1-번호       <input type="text" name="cardVOList[0].no" /><br />
		카드1-유효년월  <input type="text" name="cardVOList[0].validMonth" /><br />
		카드2-번호       <input type="text" name="cardVOList[1].no" /><br />
		카드2-유효년월  <input type="text" name="cardVOList[1].validMonth" /><br />
		카드3-번호       <input type="text" name="cardVOList[2].no" /><br />
		카드3-유효년월  <input type="text" name="cardVOList[2].validMonth" /><br />
	</p>
	<p> <!--
			VARCHAR2(4000byte) : 1333자
			CLOB : Character(문자열) Large OBject(4GB)
			       result property="introduction" column="INTRODUCTION"
			       		jdbcType="CLOB" javaType="java.lang.String" 
		-->
		자기소개 : 
		<textarea name="introduction" rows="6" cols="50"></textarea>
	</p>
	<p>
		<input type="submit" value="register0401" />
	</p>
<script type="text/javascript">
$(function(){
	$("#btnPostNum").on("click",function(){
		new daum.Postcode({
			//다음 창에서 검색이 완료되면 콜백함수에 의해 결과 데이터가 data 객체로 들어옴
			oncomplete:function(data){
				$("input[name=\"addressVO.zonecode\"]").val(data.zonecode);
				$("input[name=\"addressVO.address\"]").val(data.address);
				$("input[name=\"addressVO.buildingName\"]").val(data.buildingName);
			}
		}).open();
	});
});
</script>
</form>
