<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/frame/header.jsp"%>
<%@ include file="/WEB-INF/jsp/include/frame/title.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	$("#btnInitFilter").click(function() {
		document.location.href = "${_context}/info/userAdmin";
	});
	
	$("#theadUserList span").click(function() {
        $("#sortKey").val($(this).data("sort-key"));
        if ($(this).hasClass("sorting-down")) {
            $(this).removeClass("sorting-down");
            $(this).addClass("sorting-up");
            $("#sortOrder").val("ASC");
        } else if ($(this).hasClass("sorting-up")) {
            $(this).removeClass("sorting-up");
            $(this).addClass("sorting-down");
            $("#sortOrder").val("DESC");
        } else {
        	$("#theadUserList span").removeClass("sorting-up");
        	$("#theadUserList span").removeClass("sorting-down");
        	$("#theadUserList span").addClass("sorting-down-disabled");
        	$(this).removeClass("sorting-down-disabled");
            $(this).addClass("sorting-down");
            $("#sortOrder").val("DESC");
        }
        <sec:authorize access="hasRole('ROLE_C02_LIST')">
    	jsGetUserAdminList();
    	</sec:authorize>
    }).css("cursor", "pointer");
	
	jsInitFilter();
	<sec:authorize access="hasRole('ROLE_C02_LIST')">
	jsGetUserAdminList();
	</sec:authorize>
	<sec:authorize access="!hasRole('ROLE_C02_LIST')">
	$("#tbodyUserList").append('<tr><td colspan="11">조회 권한이 없습니다.</td></tr>');
	</sec:authorize>
		
	//삭제 버튼 클릭시 이벤트 처리
	<sec:authorize access="hasRole('ROLE_C02_DEL')">
	$("#btnDelCode").click(function() {
		var empNo = $("#selectedEmpNo").val();
		
		if(empNo) {
			callConfirm("선택한 항목을 삭제하시겠습니까?", jsDelete);
		}else{
			callAlert("삭제할 항목을 선택하십시오");
		}
	});
	</sec:authorize>
});

function jsInitFilter() {
	$("#brnNm").val("");
	$("#permId").val("");
	$("#permNm").val("");
	$("#yzYn").val("Y");
}

function nvl(value) {
	if (value) {
		if (value != "null") {
			return value;
		} else {
			return "-";
		}
	} else {
		return "-";
	}
}
	
function jsGetUserAdminList() {
	$("#tbodyUserList tr").remove();
	$("#selectedEmpNo").val("");
	
	jsTrimInputValue("userListForm");
	jsAjaxPost("${_context}/info/userAdmin/list", $("#userListForm").serialize(), function(response) {
		if (response.result == "success") {
			if (response.list && response.list != null && response.list.length > 0) {
				for (var idx in response.list) {
					var item = response.list[idx];
					var htmlStr = '';
					htmlStr += '<tr style="cursor: pointer;" empNo="' + item.empNo + '">';	
					htmlStr += '	<td>'+($("#pageSize").val()*($("#pageNo").val()-1)+(parseInt(idx)+1))+'</td>';
					htmlStr += '	<td>' + nvl(item.brcd) + '</td>';
					htmlStr += '	<td>' + nvl(item.brnNm) +'</td>'
					htmlStr += '	<td>' + nvl(item.empNo) + '</td>';
					htmlStr += '	<td>' + nvl(item.empNm) + '</td>';
					htmlStr += '	<td>' + nvl(item.pstNm) + '</td>';
					htmlStr += '	<td>' + nvl(item.uzYn) + '</td>';
					htmlStr += '	<td>' + nvl(item.permNm) + '</td>';
					htmlStr += '	<td>' + nvl(item.regEmpNo) + '</td>';
					htmlStr += '	<td>' + setDateTimeFormatFromDate(new Date(item.regTime)) + '</td>';
					htmlStr += '	<td>' + nvl(item.chngEmpNo) + '</td>';
					htmlStr += '	<td>' + setDateTimeFormatFromDate(new Date(item.chngTime)) + '</td>';						
					htmlStr += '</tr>';
					
					$("#tbodyUserList").append(htmlStr);
				}
				
				jsPageNumbering($("#pageNo").val(), response.totalCount, $("#pageSize").val());
				$("#thTotalCount").text(response.totalCount);
				
				$("#tbodyUserList tr").click(function(e) {
					var selected = $("#tbodyUserList tr.selected");
					if(selected) {
						selected.removeClass("selected");
						selected.find("td").removeClass("selected");
						$("#selectedEmpNo").val("");
					}
					
			 		$(this).addClass("selected");
			 		$(this).find("td").addClass("selected");
			 		$("#selectedEmpNo").val($(this).attr("empNo"));					
				});
				
			} else {
				// 데이터 없음
				$("#tbodyUserList").append('<tr><td colspan="12">조회 결과가 없습니다.</td></tr>');
				$("#divPaging").children().remove();
				$("#thTotalCount").text("0");
			}				
		} else {
			// 요청 실패
		}
	});
}

function jsAddUserPopup() {		
	jsShowBlockLayerWithUrl("#divEditUserPopup", "${_context}/info/userAdmin/edit");
}

function jsEditUserPopup() {
	var empNo = $("#selectedEmpNo").val();
	
	if(!empNo) {
		alert("사용자를 선택하세요.");
		return false;
	}

	jsShowBlockLayerWithUrl("#divEditUserPopup", "${_context}/info/userAdmin/edit?empNo=" + empNo);	
}

function jsDelete() {
	var empNo = $("#selectedEmpNo").val();
	
	if(!empNo) {
		alert("사용자를 선택하세요.");
		return false;
	}
	
	jsTrimInputValue("userEditForm");
	jsAjaxPost("${_context}/info/userAdmin/delete", $("#userEditForm").serialize(), function(response) {
		if (response.result == "success") {
			location.reload();
			
		} else if(response.result == "duplicate") {
			alert("중복된 권한명 입니다.");	
		} else {
			alert("권한 추가 실패");
		}
	});		
}	

function jsInitializePassword() {
	var isSelected = false;
	var $form = $('<form></form>');
    $form.appendTo('body');
    $("#tbodyUserList tr").each(function(index, item) {
		if ($(this).hasClass("selected")) {
			isSelected = true;
			$form.append('<input type="hidden" value="'+$(item).attr("empNo")+'" name="empno">');
		}
	});
    $form.append('<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>');
	
    if (isSelected) {
    	if (confirm("선택한 사용자의 비밀번호를 초기화 하시겠습니까?")) {
    		jsAjaxPost("${_context}/info/userAdmin/password/initialize", $form.serialize(), function(response) {
    			if (response.result == "success") {
    				alert("비밀번호가 초기화 되었습니다.");
    			} else {
    				alert("요청을 실패하였습니다.");
    			}
    		});
    	}
    } else {
    	alert("사용자를 선택해 주세요.");	
    }
}

function jsPage(pageNo) {
	$("#pageNo").val(pageNo);
	
	jsGetUserAdminList();
}
</script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/include/frame/top.jsp"%>

	<!-- content -->
	<form:form id="userEditForm" name="userEditForm">
		<input type="hidden" id="selectedEmpNo" name="empNo" />
	</form:form>
	<form:form id="userListForm" name="userListForm">
	
		<div class="search">
			<h2>사용자 관리</h2>
			<div class="refinebutton" style="margin-bottom: 5px;">
				<sec:authorize access="hasRole('ROLE_C02_REG')">
					<span class="btn4" onclick="jsAddUserPopup()">등록</span>
				</sec:authorize>		
			</div>
			<div class="table1">
				<table class="data" style="min-width: 100%">
					<tr class="tr1">
						<td class="title">지부</td>
						<td class="input">
							<input id="brcd" type="text" name="brcd">
						</td>
						<td class="title">직원번호</td>
						<td class="input">
							<input id="empNo" type="text" name="empNo">
						</td>
						<td class="title title2"><span <sec:authorize access="hasRole('ROLE_C02_LIST')">onclick="jsGetUserAdminList()"</sec:authorize> class="btn1" style="height:36px;">조회</span></td>
					</tr>
					<tr class="tr1">
						<td class="title">권한</td>
						<td class="input">
							<select id="permFilter" name="permId">
								<option value="0">전체</option>
								<c:forEach items="${permList}" var="perm">
									<option value="${perm.permId}">${perm.permNm}</option>								
								</c:forEach>
							</select>
						</td>
						<td class="title">사용</td>
						<td class="input">
							<select id="uzYnFilter" name="uzYn">
								<option value="">전체</option>
								<option value="Y">Y</option>
								<option value="N">N</option>								
							</select>
						</td>
						<td class="title title2">
							<span id="btnInitFilter" class="btn1 refresh">
								<img class="refresh_img" src="<c:url value="/images/btn_refresh.png" />">
							초기화</span>
						</td>
					</tr>
				</table>
			</div>			
			<table id="count" summary="조회결과" style="margin-top:16px; float: left; clear:both;">
				<colgroup>
					<col span="3" style="border:none;">
				</colgroup>
				<tr>
					<th>조회결과&nbsp;</th>
					<th id="thTotalCount">0</th>
					<th>&nbsp;건</th>
				</tr>
			</table>
			<div class="refinebutton">
				<sec:authorize access="hasRole('ROLE_C02_EDIT')">
					<span class="btn4" style="width: 130px;" onclick="jsInitializePassword()">비밀번호 초기화</span>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_C02_EDIT')">
					<span class="btn4" onclick="jsEditUserPopup()">수정</span>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_C02_DEL')">
					<span id="btnDelCode" class="btn4">삭제</span>
				</sec:authorize>
			</div>
			<table class="output" style="width: 1220px;">
				<thead id="theadUserList">
					<tr>
						<th>번호</th>
						<th>지부코드<span data-sort-key="brcd" class="btn-sorting sorting-down-disabled"></span></th>
						<th>지부명<span data-sort-key="brnNm" class="btn-sorting sorting-down-disabled"></span></th>
						<th>직원번호<span data-sort-key="empNo" class="btn-sorting sorting-down-disabled"></span></th>
						<th>이름<span data-sort-key="empNm" class="btn-sorting sorting-down-disabled"></span></th>
						<th>직위<span data-sort-key="pstNm" class="btn-sorting sorting-down-disabled"></span></th>
						<th>사용<span data-sort-key="uzYn" class="btn-sorting sorting-down-disabled"></span></th>
						<th>권한<span data-sort-key="permNm" class="btn-sorting sorting-down-disabled"></span></th>
						<th>등록직원번호<span data-sort-key="regEmpNo" class="btn-sorting sorting-down-disabled"></span></th>
						<th>등록일시<span data-sort-key="regTime" class="btn-sorting sorting-down"></span></th>
						<th>변경직원번호<span data-sort-key="chngEmpNo" class="btn-sorting sorting-down-disabled"></span></th>
						<th>변경일시<span data-sort-key="chngTime" class="btn-sorting sorting-down-disabled"></span></th>
					</tr>
				</thead>
				<tbody id="tbodyUserList"></tbody>
			</table>
			<!-- paging -->
			<div id="divPaging" class="prev"></div>
				
			<%@ include file="/WEB-INF/jsp/include/frame/bottom.jsp"%>
		</div>
		
		<input type="hidden" id="pageNo" name="pageNo" value="<c:out value="${userListForm.pageNo }" />" />
		<input type="hidden" id="pageSize" name="pageSize" value="<c:out value="${userListForm.pageSize }" />" />
		<input type="hidden" id="pageBlock" name="pageBlock" value="<c:out value="${userListForm.pageBlock }" />" />
		<input type="hidden" id="sortKey" name="sortKey" value="<c:out value="${userListForm.sortKey }" />">
        <input type="hidden" id="sortOrder" name="sortOrder" value="<c:out value="${userListForm.sortOrder }" />">
	</form:form>
	
	<!-- 사용자 추가/수정 팝업 -->
	<div id="divEditUserPopup" class="popup-wrapper" style="width: 380px; display: none;"></div>
	
	<%@ include file="/WEB-INF/jsp/include/frame/footer.jsp"%>

</body>
</html>