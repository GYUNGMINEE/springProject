<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript">
$(function(){
	let day = "수요일";

	/* 일반 표현식 */
	console.log("오늘은 " + day + " 입니다.");

	/* 템플릿 리터럴 표현식 */
	console.log(`오늘은 ${day} 입니다.`);
	
	//아작나써유..피씨다타써
	$.ajax({
		url:"/resp/goHome07",
		type:"get",
		dataType:"text",
		success:function(result){
			console.log("result : " + result);
			if(result=="SUCCESS"){
				$("#edit").css("display","block");
			}else{
				$("#edit").css("display","none");
			}
		}
	});
	
	// 요청 : /resp/goHome09 -> 응답 : ["소설","IT","수필"]
	//public String home09() => dataType:"text"
	//public List<String> home09() => dataType:"json"
	$.ajax({
		url:"/resp/goHome09",
		type:"get",
		dataType:"json",
		success:function(result){
			//result : ["소설","IT","수필"]
			console.log("result : " + JSON.stringify(result));
	
			let sel = "";	
			//result : List<String>
			$.each(result, function(index, str){
				console.log("index : " + index + ", str : " + str);
				console.log("str : " + `${str}`);
// 				sel = "<option value='"+str+"'>"+str+"</value>";
				sel = `<option value='${str}'>${str}</value>`;
				
				$("#sel1").append(sel);
			});
		}
	});
// 	$("#sel1").append("<option value='소설'>소설</value>");
// 	$("#sel1").append("<option value='IT'>IT</value>");
// 	$("#sel1").append("<option value='수필'>수필</value>");
});
</script>
<h1>Home0202</h1>

<p>
	<select id="sel1" name="category">
		<option value="">카테고리</option>
	</select>
</p>

<!-- 1) 로그인 2) 로그인계정==작성자계정 -->
<p><button type="button" id="edit" style="display:none;">글수정</button></p>






