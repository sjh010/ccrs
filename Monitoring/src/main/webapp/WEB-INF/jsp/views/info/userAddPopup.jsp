<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/frame/taglib.jsp"%>
<%@ page import="java.lang.String" %>
<%@ page import="com.mobileleader.edoc.monitoring.utils.CleanXssUtils" %>
<c:set var="_context" value="${(pageContext.request.contextPath eq '/')? '': pageContext.request.contextPath}" />
<%
	String mode = (String) request.getAttribute("mode");
	// clean XSS
	mode = CleanXssUtils.cleanXSS(mode);
%>
<script type="text/javascript">	
$(document).ready(function() {
	var mode = "<%=mode%>";

	// cleanXss
	//mode = cleanXss(mode);
	
	if(mode == "add") {
		$("#titleTxt").text("사용자 등록");
		$("#empNoTxt").css("display", "none");
		$("#empNmTxt").css("display", "none");
	} else {
		$("#titleTxt").text("사용자 정보 수정");
		$("#empNoInput").css("display", "none");
		$("#empNmInput").css("display", "none");
		
		//
		$("#brnNmInput").css("display", "none");
		$("#brcdInput").css("display", "none");
		$("#pstNmInput").css("display", "none");
		
		var permId = "<c:out value='${userInfo.permId}' />";
		var uzYn = "<c:out value='${userInfo.uzYn}' />";
		
		// cleanXss
		permId = cleanXss(permId);
		uzYn = cleanXss(uzYn);
	
		$("#permIdInput option[value='" + permId + "']").prop("selected", true);
		$("#uzYnInput").val(uzYn);
	}
	
	$("#divEditUserPopup .btn_popup_close").click(function(e) {
		jsHideBlockLayer("#divEditUserPopup");
	});
	
	$("#divEditUserPopup .close").click(function(e) {
		jsHideBlockLayer("#divEditUserPopup");  
	});
	
	// 부서 선택 이벤트
	$("#department").change(function() {
		getEmployeeInfo();
	});
	
	// 직원 선택 이벤트
	$("#employee").change(function() {
		setEmployeeInfo();
	});
});

//사용자 등록 validation check. 
function chkValid(){	
	if ($("#brcdInput").val() === null || $.trim($("#brcdInput").val()) == "" || $("#brcdInput").val() === undefined) {
		callAlert("영업점 코드를 입력하십시오.", function(){jsShowBlockLayer("#popup_top");});		
		setTimeout(function(){$("#brcdInput").focus();}, 1000);
		return;		
	} else if ($("#brnNmInput").val() === null || $.trim($("#brnNmInput").val()) == "" || $("#brnNmInput").val() === undefined) {
		callAlert("영업점명을 입력하십시오.", function(){jsShowBlockLayer("#popup_top");});
		setTimeout(function(){$("#brnNmInput").focus();}, 1000);
		return;		
	} else if ($("#empNoInput").val() === null || $.trim($("#empNoInput").val()) == "" || $("#empNoInput").val() === undefined) {
		callAlert("직원번호를 입력하십시오.", function(){jsShowBlockLayer("#popup_top");});
		setTimeout(function(){$("#empNoInput").focus();}, 1000);
		return;		
	} else if ($("#empNmInput").val() === null || $.trim($("#empNmInput").val()) == "" || $("#empNmInput").val() === undefined) {
		callAlert("직원명을 입력하십시오.", function(){jsShowBlockLayer("#popup_top");});
		setTimeout(function(){$("#empNmInput").focus();}, 1000);
		return;		
	} else if ($("#pstNmInput").val() === null || $.trim($("#pstNmInput").val()) == "" || $("#pstNmInput").val() === undefined) {
		callAlert("직위를 입력하십시오.", function(){jsShowBlockLayer("#popup_top");});
		setTimeout(function(){$("#pstNmInput").focus();}, 1000);
		return;		
	} else if ($("#permIdInput").val() === null || $.trim($("#permIdInput").val()) == "" || $("#permIdInput").val() === undefined) { // 권한 체크 추가
		callAlert("권한을 선택하십시오.", function(){jsShowBlockLayer("#popup_top");});
		setTimeout(function(){$("#permIdInput").focus();}, 1000);
		return;
	}
	
	return true;
}

function jsSave() {
	if(chkValid()){
		var mode = "<%=mode%>";
		var reqUrl = mode == "add" ?  "${_context}/info/userAdmin/add" : "${_context}/info/userAdmin/update";
		
		jsTrimInputValue("userForm");
		jsAjaxPost(reqUrl, $("#userForm").serialize(), function(response) {
			if (response.result == "success") {
				location.reload();				
			} else if(response.result == "duplicate") {
				alert("중복된 사용자 입니다.");	
			} else {
				alert("사용자 추가를 실패하였습니다.");
			}
		});
	}

}

// 직원 정보 조회
function getEmployeeInfo() {
	var departmentCode = $("#department").val();
	
	if (departmentCode) {
		$.ajax({
			url : "${_context}/info/userAdmin/employee/list",
			method : "POST",
			data : {"departmentCode" : departmentCode},
			success : function(response) {
				
				if (response) {
					var html = "<option value=''>선택</option>";
					
					$.each(response, function(index, employee) {
						html += "<option data-positionName='" + employee.positionName + "' value='" + employee.employeeNo +"'>" + employee.employeeName
						html += "</option>";
					});

					$("#employee").html(html);
				} else {
					alert("직원 조회를 실패했습니다.");
				}
			},
			failure : function() {
				alert("직원 조회를 실패했습니다.");
			}
		});	
	} else {
		$("#userForm")[0].reset();
	}
}

// 직원 정보 세팅
function setEmployeeInfo() {
	$departmentInfo = $("#department option:selected");
	$employeeInfo = $("#employee option:selected");
	
	var departmentCode = $departmentInfo.val();
	var departmentName = $departmentInfo.text();
	var employeeNo = $employeeInfo.val();
	var employeeName = $employeeInfo.text();
	var positionName = $employeeInfo.data("positionname");
	
	$("#brcdInput").val(departmentCode);
	$("#brnNmInput").val(departmentName);
	$("#empNoInput").val(employeeNo);
	$("#empNmInput").val(employeeName);
	$("#pstNmInput").val(positionName);
}

function replaceAll(str, searchStr, replaceStr) {
	return str.split(searchStr).join(replaceStr);
}

function cleanXss(value) {
	if (value != null) {
		value = replaceAll(value, "<", "&lt;");
		value = replaceAll(value, ">", "&gt;");
		value = replaceAll(value, "\\(", "&#40;");
		value = replaceAll(value, "\\)", "$#41;");
		value = replaceAll(value, "'", "&#39;");
		value = replaceAll(value, "eval\\((.*)\\)", "");
		value = replaceAll(value, "[\\\\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = replaceAll(value, "script", "");
	}
	
	return value;
}
</script>
	<div class="popup_top">
			<h2 id="titleTxt">사용자 정보 수정</h2>	
		</div>
		<a href="#" class="btn_popup_close"></a>
		<div class="table1">
			<form:form id="userForm" name="userForm">				
			<table class="data" style="width:260px">
				<c:if test="${mode eq 'add' }">
					<tr class="tr1">
						<td class="title" style="height:34px">부서 선택</td>
						<td class="input" style="border-right:none; width:238px;">
							<select id="department" style="width: 224px;">
								<option value="">선택</option>
								<c:forEach items="${departments}" var="department">
									<option value="<c:out value="${department.departmentCode}" />"><c:out value="${department.departmentName}" /></option>								
								</c:forEach>
							</select>	
						</td>
					</tr>
				</c:if>		
						
				<c:if test="${mode eq 'add' }">
					<tr class="tr1">
						<td class="title" style="height: 34px">직원 선택</td>
						<td class="input" style="border-right:none; float:left; width:238px;">
							<select id="employee" style="width: 224px;"></select>
						</td>
					</tr>	
				</c:if>
				<tr class="tr1">
					<td class="title" style="height:34px">부서코드</td>
					<td class="input" style="border-right:none; float:left; width:238px">
						<p id="brcdTxt" style="text-indent: 20px; line-height: 34px"><c:out value="${userInfo.brcd}" /></p>
						<input type="text" id="brcdInput" name="brcd" style="width:220px; height: 34px" value="<c:out value="${userInfo.brcd}" />" readonly="readonly">
					</td>
				</tr>
				<tr class="tr1">
					<td class="title" style="height:34px">부서명</td>
					<td class="input" style="border-right:none; float:left; width:238px">
						<p id="brnNmTxt" style="text-indent: 20px; line-height: 34px"><c:out value="${userInfo.brnNm}" /></p>
						<input type="text" id="brnNmInput" name="brnNm" style="width:220px; height: 34px" value="<c:out value="${userInfo.brnNm}" />" readonly="readonly">
					</td>
				</tr>
				<tr class="tr1">
					<td class="title" style="height:34px">직원번호</td>
					<td class="input" style="border-right:none; float:left; width:238px">
						<p id="empNoTxt" style="text-indent: 20px; line-height: 34px"><c:out value="${userInfo.empNo}" /></p>						
						<input type="text" id="empNoInput" name="empNo" style="width:220px; height: 34px" value="<c:out value="${userInfo.empNo}" />" readonly="readonly">
					</td>
				</tr>
				<tr class="tr1">
					<td class="title" style="height:34px">직원명</td>
					<td class="input" style="border-right:none; float:left; width:238px">				
						<p id="empNmTxt" style="text-indent: 20px; line-height: 34px"><c:out value="${userInfo.empNm}" /></p>		
						<input type="text" id="empNmInput" name="empNm" style="width:220px; height: 34px" value="<c:out value="${userInfo.empNm}" />" readonly="readonly">
					</td>
				</tr>
				<tr class="tr1">
					<td class="title" style="height:34px">직위</td>
					<td class="input" style="border-right:none; float:left; width:238px">
						<p id="pstNmTxt" style="text-indent: 20px; line-height: 34px"><c:out value="${userInfo.pstNm}" /></p>						
						<input type="text" id="pstNmInput" name="pstNm" style="width:220px; height: 34px" value="<c:out value="${userInfo.pstNm}" />" readonly="readonly">
					</td>
				</tr>
				<tr class="tr1">
					<td class="title">권한</td>
					<td class="input" style="border-right:none; float:left; width:238px">
						<select id="permIdInput" name="permId" style="width:224px" >
								<option value="">선택</option>
								<c:forEach items="${permList}" var="perm">
									<option value="<c:out value="${perm.permId}" />" ><c:out value="${perm.permNm}" /></option>								
								</c:forEach>
						</select>
					</td>
				</tr>
				<tr class="tr1">
					<td class="title">사용여부</td>
					<td class="input" style="border-right:none; float:left; width:238px">
						<select id="uzYnInput" name="uzYn" style="width:224px">
							<option value="Y">Y</option>
							<option value="N">N</option>
						</select>
					</td>
				</tr>
			</table>
			</form:form>
		</div>
		<div class="popupbutton" style="float:right; margin-top:10px">
			<span class="download" style="width:90px !important" onclick="jsSave();">저장</span>
			<span class="close">취소</span>
		</div>