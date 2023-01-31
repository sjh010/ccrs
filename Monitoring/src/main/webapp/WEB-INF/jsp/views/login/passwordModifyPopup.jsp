<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="_context" value="${(pageContext.request.contextPath eq '/')? '': pageContext.request.contextPath}" />

<script type="text/javascript">
$(document).ready(function() {
	$(".btnPopupClose").click(function(e) {
		jsHideBlockLayer("#divModifyPasswordPopup");
	});
	
	$("#btnModifyPassword").click(function() {
		jsModifyPasswordProcess();
	});
});

function jsModifyPasswordProcess() {
	var currentPassword = $.trim($("#currentPassword").val());
	var newPassword = $.trim($("#newPassword").val());
	var newPassword2 = $.trim($("#newPassword2").val());

	if (currentPassword == null || currentPassword == "") {
		alert("현재 비밀번호를 입력해 주세요.");
		$("#currentPassword").focus();
		return false;
	}
	
	if (newPassword == null || newPassword == "") {
		alert("새 비밀번호를 입력해 주세요.");
		$("#newPassword").focus();
		return false;
	}
	
	if (newPassword2 == null || newPassword2 == "") {
		alert("새 비밀번호 확인을 입력해 주세요.");
		$("#newPassword2").focus();
		return false;
	}
	
	if (newPassword != newPassword2) {
		alert("새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.");
		$("#newPassword2").focus();
		return false;
	}
	
	var passwordCount = 0;
	
	var pattern1 = /[A-Z]/;
	if (pattern1.test(newPassword) == true) {
		passwordCount++;
    }
	
    var pattern2 = /[a-z]/;
    if (pattern2.test(newPassword) == true) {
    	passwordCount++;
    }
    
    var pattern3 = /[0-9]/;
    if (pattern3.test(newPassword) == true) {
    	passwordCount++;
    }
	
	var pattern4 = /[~!@#$%^&*()_+|<>?:{}]/;
    if (pattern4.test(newPassword) == true) {
    	passwordCount++;
    }
    
    if (passwordCount < 2) {
    	alert("영문 대문자, 소문자, 숫자, 특수문자 조합 중 3개 조합 8자리 이상 또는 2개 조합 10자리 이상으로 설정하시기 바랍니다.");
    	return false;
    } else if (passwordCount == 2) {
    	if (newPassword.length < 10) {
    		alert("영문 대문자, 소문자, 숫자, 특수문자 조합 중 2개 조합일 경우, 10자리 이상이어야 합니다.");
    		return false;
    	}
    } else if (passwordCount > 2) {
    	if (newPassword.length < 8) {
    		alert("영문 대문자, 소문자, 숫자, 특수문자 조합 중 3개 이상 조합일 경우, 8자리 이상이어야 합니다.")
    		return false;
    	}
    }
    
	jsTrimInputValue("passwordModifyForm");
	jsAjaxPost("${_context}/password/modify/process", $("#passwordModifyForm").serialize(), function(response) {
		if (response.result == "success") {
			alert("비밀번호가 변경되었습니다.");
			document.location.href = "${_context}/logout";
		} else if (response.result == "invalidPassword") {
			alert("현재 비밀번호를 확인 후 다시 입력해 주세요.");
		} else {
			alert("비밀번호 변경을 실패하였습니다.");
		}
	});
}
</script>

	<div class="popup_top">
		<h2>비밀번호 변경</h2>	
	</div>
	<a href="javascript:;" class="btn_popup_close btnPopupClose"></a>
	<form:form id="passwordModifyForm" name="passwordModifyForm">
	<div class="table1">
		<table class="data" style="width:260px">
			<tr class="tr1">
				<td class="title">현재 비밀번호</td>
				<td class="input" style="border-right:none; float:left; width:238px"><input type="password" id="currentPassword" name="currentPassword" style="width:220px;"></td>
			</tr>
			<tr class="tr1">
				<td class="title">새 비밀번호</td>
				<td class="input" style="border-right:none; float:left; width:238px"><input type="password" id="newPassword" name="newPassword" style="width:220px;"></td>
			</tr>
			<tr class="tr1">
				<td class="title">새 비밀번호 확인</td>
				<td class="input" style="border-right:none; float:left; width:238px"><input type="password" id="newPassword2" style="width:220px;"></td>
			</tr>
		</table>
	</div>
	<h4 style="color: red;">* 비밀번호는 영문 대문자, 소문자, 숫자, 특수문자 조합 중<br/>3개 조합 8자리 이상 또는 2개 조합 10자리 이상으로 입력해주세요.</h4>
	</form:form>
	<div class="popupbutton" style="float:right; margin-top:10px">
		<span id="btnModifyPassword" class="download" style="width:90px !important">확인</span>
		<span class="close btnPopupClose">취소</span>
	</div>
	
<script>
$(document).ready(function() {
	jsShowBlockLayer("#divModifyPasswordPopup")
});
</script>