<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/frame/header.jsp"%>
<%@ include file="/WEB-INF/jsp/include/frame/title.jsp"%>
<script type="text/javascript">
$(document).ready(function() { 
	<c:if test="${error == 'true'}"> 
	if (${passwordErrorCount} >= 5) {
		$("#login_errtext").html("비밀번호를 5회이상 틀리셨습니다. 관리자에게 문의하세요."); 
	} else {
		$("#login_errtext").html("등록되지 않은 직원번호이거나, <br/>직원번호 또는 비밀번호가 일치하지 않습니다."); 
	}
	$("#login_errtext").show();
	</c:if>
}); 

function doLogin() {
	var checkValidation = true;
	$("#login_errtext").hide();
	if ($.trim($("#inputEmpNo").val()) == "") {
		$("#login_errtext").text("직원번호를 입력해 주십시오."); 
		$("#login_errtext").show();
		$("#inputEmpNo").focus();
		checkValidation = false;
	}
	if ($.trim($("#inputPassword").val()) == "") {
		$("#login_errtext").text("비밀번호를 입력해 주십시오."); 
		$("#login_errtext").show();
		$("#inputEmpNo").focus();
		checkValidation = false;
	}
	
	if (checkValidation) {		
		jsTrimInputValue("loginForm");
		document.getElementById("loginForm").submit();
	}
}
</script>
</head>
<body>
	<div id="login_bg">
		<div id="login_frame">
			<form:form id="loginForm" action="./j_spring_security_check" method="POST">
				<input type="hidden" id="browserType" name="browserType">
				<ul id="login_content">
					<li id="login_logo"><img src="<c:url value="/images/img_login_title.png" />"></li>
					<li id="login_errtext" style="display: none;">등록되지 않은 직원번호이거나, 직원번호 또는 비밀번호가 일치하지 않습니다.</li>
					<li>
						<ul>
							<li class="login_text">직원번호</li>
							<li><input id="inputEmpNo" class="login_input" type="text" name="empNo" placeholder="아이디를 입력하세요." value="<c:out value="${userName}" />" /></li>
							<li style="clear:both"></li>
							<li class="login_text">비밀번호</li>
							<li><input id="inputPassword" class="login_input" type="password" name="password" placeholder="비밀번호를 입력하세요."  value="<c:out value="${userPassword}" />" /></li>
							<li style="clear:both"></li><li><a href="javascript:doLogin();"><span class="btn_login"><span>로그인</span></span></a></li>
						</ul>
					</li>	
				</ul>
			</form:form>
		</div>
	</div>
</body>
</html>