<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/frame/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/jsp/include/frame/title.jsp"%>
<script type="text/javascript">
//코드목록에서 현재 선택한 코드의 인덱스 값을 저장하기 위한 전역변수
var selectedIdx;
//저장방법 구분을 위한 전역변수, 1 - 등록, 2 - 수정
var saveMethod;

$(document).ready(function() {
	//최초 페이징 처리
	jsPageNumbering($("#pageNo").val(), $("#thTotalCount").html(), $("#pageSize").val());
	
	//코드 목록 클릭시 이벤트 처리
	$("#tbodyCodeList").on("click", "tr", function(){
        $("#tbodyCodeList tr td").removeClass("selected");
        if($("#tbodyCodeList tr").eq(0).find('td').eq(1).html() !== undefined){
	        $(this).find('td').addClass("selected");
        }
    });
	
	//팝업창 창 닫기 버튼 클릭시 이벤트 처리
	$("#closePop, .btn_popup_close").click(function(e) {
		jsHideBlockLayer("#divCodeRegistPopup");
	});
	
	//등록 버튼 클릭시 이벤트 처리
	<sec:authorize access="hasRole('ROLE_C01_REG')">
	$("#btnRegCode").click(function() {
		openPop(1);
	});
	</sec:authorize>
	
	//수정 버튼 클릭시 이벤트 처리
	<sec:authorize access="hasRole('ROLE_C01_EDIT')">
	$("#btnModCode").click(function() {
		var idx = chkSelected();
		
		if(idx >= 0){
			openPop(2);
		}else{
			callAlert("수정할 항목을 선택하십시오");
		}
	});
	</sec:authorize>
	
	//삭제 버튼 클릭시 이벤트 처리
	<sec:authorize access="hasRole('ROLE_C01_DEL')">
	$("#btnDelCode").click(function() {
		var idx = chkSelected();
		
		if(idx >= 0){
			callConfirm("선택한 항목을 삭제하시겠습니까?", deleteCode);
		}else{
			callAlert("삭제할 항목을 선택하십시오");
		}
	});
	</sec:authorize>
	
	//초기화 버튼 클릭시 이벤트 처리
	$("#btnInit").click(function() {
		//$("#infoCodeForm")[0].reset();
		document.location.href = "${_context}/info/codeAdmin";
	});
	
	//저장 버튼 클릭시 이벤트 처리
	$("#btnSaveCode").click(function() {
		if(chkValid()){
			if(saveMethod == 1){
				registCode();				
			}else {
				modifyCode();
			}
		}
	});
	
	// sort button event
    $("#theadCodeList span").click(function() {
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
        	$("#theadCodeList span").removeClass("sorting-up");
        	$("#theadCodeList span").removeClass("sorting-down");
        	$("#theadCodeList span").addClass("sorting-down-disabled");
        	$(this).removeClass("sorting-down-disabled");
            $(this).addClass("sorting-down");
            $("#sortOrder").val("DESC");
        }
        <sec:authorize access="hasRole('ROLE_C01_LIST')">
    	jsGetCodeList();
    	</sec:authorize>
    }).css("cursor", "pointer");
	
	<sec:authorize access="hasRole('ROLE_C01_LIST')">
	jsGetCodeList();
	</sec:authorize>
	<sec:authorize access="!hasRole('ROLE_C01_LIST')">
	$("#tbodyCodeList").append('<tr><td colspan="7">조회 권한이 없습니다.</td></tr>');
	</sec:authorize>
});

//코드 목록을 조회한다
function jsGetCodeList() {
	$("#tbodyCodeList tr").remove();
	jsTrimInputValue("infoCodeForm");
	var data = $("#infoCodeForm").serialize();
	
	jsAjaxPost("${_context}/info/codeAdmin/getCodeList", data, function(response) {
		if (response.result == "success") {
			if(response.totalCount > 0){
				var list = response.codeList;
				for (var idx in list) {
					var item = list[idx];
					var htmlStr = '';
					
					htmlStr += '<tr>';
					htmlStr += '	<td>'+item.rowNum+'</td>';
					htmlStr += '	<td>'+item.cdGroup+'</td>';
					htmlStr += '	<td>'+item.cdVal+'</td>';
					htmlStr += '	<td>'+item.cdNm+'</td>';
					htmlStr += '	<td>'+item.seqno+'</td>';
					htmlStr += '	<td>'+item.uzYn+'</td>';
					htmlStr += '	<td>'+item.regTime+'</td>';
					htmlStr += '</tr>';
					
					$("#tbodyCodeList").append(htmlStr);
				}
				
				jsPageNumbering($("#pageNo").val(), response.totalCount, $("#pageSize").val());
			} else {
				$("#tbodyCodeList").append('<tr><td colspan="7">조회 결과가 없습니다.</td></tr>');
				$("#divPaging").children().remove();
			}
			
			$("#thTotalCount").text(response.totalCount);
		} else {
			callAlert("조회 중 오류가 발생하였습니다.");
		}
	});
}

//페이징 처리
function jsPage(num) {
	if(num === null || num === undefined){
		$("#pageNo").val("1");
	}else{
		$("#pageNo").val(num);
	}
	
	jsGetCodeList();
}

//팝업창을 연다. type 1 - 등록창, type 2 - 수정창
function openPop(type){
	//등록과 수정 분기
	if(type == 1){
		saveMethod = 1;

		$("#poptitle").text("코드 정보 등록");
		$("#regCodeForm")[0].reset();
	}else{
		saveMethod = 2;
		
		$("#poptitle").text("코드 정보 수정");
		var codeTd = $("#tbodyCodeList tr").eq(selectedIdx).find('td');
		$("#regCodeForm #cdGroup").val(codeTd.eq(1).html());
		$("#regCodeForm #cdVal").val(codeTd.eq(2).html());
		$("#regCodeForm #cdNm").val(codeTd.eq(3).html());
		$("#regCodeForm #seqno").val(codeTd.eq(4).html());
		$("#regCodeForm #uzYn").val(codeTd.eq(5).html());
	}
	
	jsShowBlockLayer("#divCodeRegistPopup");
}

//리스트에서 선택된 항목이 있는지 체크한다
function chkSelected(){
	//전역변수에 셋팅
	selectedIdx = $("#tbodyCodeList").find('.selected').parent().index();
	
	return selectedIdx;
}

//코드 등록 수정 전 유효성 체크를 한다
function chkValid(){
	if($("#cdGroup").val() === null || $.trim($("#cdGroup").val()) == ""
			|| $("#cdGroup").val() === undefined){
		callAlert("코드 그룹을 입력하십시오.", function(){jsShowBlockLayer("#divCodeRegistPopup");});
		//focus()를 사용할때 $("#regCodeForm #cdGroup") selector가 적용되지 않는 문제가 있어 아래와 같이 처리
		setTimeout(function(){$("#cdGroup").focus();}, 1000);
		return;
		
	}else if($("#cdVal").val() === null || $.trim($("#cdVal").val()) == ""
		|| $("#cdVal").val() === undefined){
		callAlert("코드 값을 입력하십시오.", function(){jsShowBlockLayer("#divCodeRegistPopup");});
		setTimeout(function(){$("#cdVal").focus();}, 1000);
		return;
		
	}else if($("#cdNm").val() === null || $.trim($("#cdNm").val()) == ""
		|| $("#cdNm").val() === undefined){
		callAlert("코드명을 입력하십시오.", function(){jsShowBlockLayer("#divCodeRegistPopup");});
		setTimeout(function(){$("#cdNm").focus();}, 1000);
		return;
		
	}else if($("#seqno").val() === null || $.trim($("#seqno").val()) == ""
		|| $("#seqno").val() === undefined){
		callAlert("순서를 입력하십시오.", function(){jsShowBlockLayer("#divCodeRegistPopup");});
		setTimeout(function(){$("#seqno").focus();}, 1000);
		return;
		
	}else if(isNaN($("#seqno").val())){
		callAlert("순서는 숫자만 입력가능 합니다.", function(){jsShowBlockLayer("#divCodeRegistPopup");});
		setTimeout(function(){
			$("#seqno").val("");
			$("#seqno").focus();
		}, 1000);
		return;
		
	}else{
		var resultCode = chkDupCode();
		if(resultCode == -1){
			callAlert("중복 된 코드 값입니다.", function(){jsShowBlockLayer("#divCodeRegistPopup");});
			return;
		}else if(resultCode == -2){
			callAlert("중복 된 코드명입니다.", function(){jsShowBlockLayer("#divCodeRegistPopup");});
			return;
		}else if(resultCode == -3){
			callAlert("중복 된 순서입니다.", function(){jsShowBlockLayer("#divCodeRegistPopup");});
			return;
		}else if(resultCode == -99){
			callAlert("코드 중복 확인 중 오류가 발생하였습니다.");
			return;
		}
	}
	
	return true;
}

//코드 중복을 체크한다
function chkDupCode(){
	var result = 0;
	
	if(saveMethod == 2){ // 수정일 경우 수정 전 값도 전송
		setExVal();		
	}
	
	jsTrimInputValue("regCodeForm");
	var data = $("#regCodeForm").serialize();
	
	//비동기방식으로 처리할 경우 리턴 되는 값을 받지 못해 오류가 발생하여 동기방식으로 처리
	jsAjaxSyncPost("${_context}/info/codeAdmin/chkDupCode", data, function(response) {
		$("#exCdGroup").val("");
		$("#exCdVal").val("");
		
		if (response.result == "success") {
			result = response.existCode;
		} else {
			result = -99;
		}
	});
	return result;
}

//코드를 등록한다
function registCode(){
	jsTrimInputValue("regCodeForm");
	var data = $("#regCodeForm").serialize();

	jsAjaxPost("${_context}/info/codeAdmin/registCode", data, function(response) {
		if (response.result == "success") {
			//callAlert("코드가 정상적으로 등록 되었습니다.", jsPage);
			callAlert("코드가 정상적으로 등록 되었습니다.");
			jsPage($("#pageNo").val());
			jsHideBlockLayer("#divCodeRegistPopup");
		} else {
			callAlert("코드 등록 중 오류가 발생하였습니다.");
		}
	});
}

//코드를 수정한다
function modifyCode(){
	setExVal();
	
	jsTrimInputValue("regCodeForm");
	var data = $("#regCodeForm").serialize();

	jsAjaxPost("${_context}/info/codeAdmin/modifyCode", data, function(response) {
		$("#exCdGroup").val("");
		$("#exCdVal").val("");
		
		if (response.result == "success") {
			//callAlert("코드가 정상적으로 수정 되었습니다.", jsPage);
			callAlert("코드가 정상적으로 수정 되었습니다.");
			jsPage($("#pageNo").val());
			jsHideBlockLayer("#divCodeRegistPopup");
		} else {
			callAlert("코드 수정 중 오류가 발생하였습니다.");
		}
	});
}

//코드를 삭제한다
function deleteCode(){
	var cdGroup = $("#tbodyCodeList tr").eq(selectedIdx).find('td').eq(1).text();
	var cdVal = $("#tbodyCodeList tr").eq(selectedIdx).find('td').eq(2).text();
	
	if (cdGroup === undefined || cdVal === undefined) {
		callAlert("삭제할 항목을 선택하십시오");
	} else {
		var data = "cdGroup=" + cdGroup + "&cdVal=" + cdVal + "&${_csrf.parameterName}=${_csrf.token}";
		jsAjaxPost("${_context}/info/codeAdmin/deleteCode", data, function(response) {
			if (response.result == "success") {
				//callAlert("정상적으로 삭제 처리 되었습니다.", jsPage);
				callAlert("정상적으로 삭제 처리 되었습니다.");
				jsPage($("#pageNo").val());
			} else {
				callAlert("삭제 중 오류가 발생하였습니다.");
			}
		});
	}
}

//수정 전 값을 hidden 셋팅한다
function setExVal(){
	var codeTd = $("#tbodyCodeList tr").eq(selectedIdx).find('td');
	$("#exCdGroup").val(codeTd.eq(1).html());
	$("#exCdVal").val(codeTd.eq(2).html());
}

</script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/include/frame/top.jsp"%>
	
	<!-- contents start -->
	<form:form id="infoCodeForm" name="infoCodeForm">
		<div class="search">
			<h2>코드 관리</h2>
			<div class="refinebutton" style="margin-bottom: 5px;">				
				<sec:authorize access="hasRole('ROLE_C01_REG')"><span id="btnRegCode" class="btn4">등록</span></sec:authorize>
			</div>
			<div id="divCdList" class="table1">
				<table class="data" style="min-width: 100%">
					<tr class="tr1">
						<td class="title">코드그룹</td>
						<td class="input"><input type="text" id="srchCdGroup" name="cdGroup"></td>
						<td class="title">코드 값</td>
						<td class="input"><input type="text" id="srchCdVal" name="cdVal"></td>
						<td class="title title2"><span <sec:authorize access="hasRole('ROLE_C01_LIST')">onclick="jsPage()"</sec:authorize> class="btn1">조회</span></td>						
					</tr>
					<tr class="tr1">
						<td class="title">코드명</td>
						<td class="input"><input type="text" id="srchCdNm" name="cdNm"></td>
						<td class="title">사용여부</td>
						<td class="input">
							<select id="srchUzYn" name="uzYn">
								<option value="">전체</option>
								<option value="Y">Y</option>
								<option value="N">N</option>								
							</select>
						</td>
						<td class="title title2">
							<span id="btnInit" class="btn1 refresh">
								<img src="<c:url value="/images/btn_refresh.png" />" style="float:left; margin-top:4px; margin-left:18px; margin-right:-20px;">
								초기화
							</span>
						</td>						 
					</tr>					 
				</table>
			</div>
			<table id="count" summary="조회 건수" style="margin-top:16px; float: left">
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
				<sec:authorize access="hasRole('ROLE_C01_EDIT')"><span id="btnModCode" class="btn4">수정</span></sec:authorize>
				<sec:authorize access="hasRole('ROLE_C01_DEL')"><span id="btnDelCode" class="btn4">삭제</span></sec:authorize>
			</div>
			<table class="output" style="width:1220px; clear:both;">
				<thead id="theadCodeList">
					<tr>
						<th style="width:10%;">번호</th>
						<th style="width:15%;">코드 그룹<span data-sort-key="cdGroup" class="btn-sorting sorting-up"></span></th>
						<th style="width:15%;">코드 값<span data-sort-key="cdVal" class="btn-sorting sorting-down-disabled"></span></th>
						<th style="width:20%;">코드 명<span data-sort-key="cdNm" class="btn-sorting sorting-down-disabled"></span></th>
						<th style="width:10%;">순서<span data-sort-key="seqno" class="btn-sorting sorting-down-disabled"></span></th>
						<th style="width:10%;">사용 여부<span data-sort-key="uzYn" class="btn-sorting sorting-down-disabled"></span></th>
						<th style="width:20%;">등록일시<span data-sort-key="regTime" class="btn-sorting sorting-down-disabled"></span></th>						
					</tr>			
				</thead>
				<tbody id="tbodyCodeList"></tbody>
			</table>
			
			<!-- paging -->
			<div id="divPaging" class="prev"></div>
			
			<%@ include file="/WEB-INF/jsp/include/frame/bottom.jsp"%>
		</div>
		
		<input type="hidden" id="pageNo" name="pageNo" value="<c:out value="${infoCodeForm.pageNo }" />" />
		<input type="hidden" id="pageSize" name="pageSize" value="<c:out value="${infoCodeForm.pageSize }" />" />
		<input type="hidden" id="pageBlock" name="pageBlock" value="<c:out value="${infoCodeForm.pageBlock }" />" />
		<input type="hidden" id="sortKey" name="sortKey" value="<c:out value="${infoCodeForm.sortKey }" />">
        <input type="hidden" id="sortOrder" name="sortOrder" value="<c:out value="${infoCodeForm.sortOrder }" />">
		
	</form:form>
	<!-- contents end -->
	
	<!-- 코드 등록 팝업 -->
	<div id="divCodeRegistPopup" class="popup-wrapper" style="width:375px; display: none;">
		<div class="popup_top">
			<h2><span id="poptitle"></span></h2>	
		</div>
		<a href="javascript:void(0)" class="btn_popup_close"></a>
		<form:form id="regCodeForm" name="regCodeForm">
			<input type="hidden" id="exCdGroup" name="exCdGroup" />
			<input type="hidden" id="exCdVal" name="exCdVal" />
			
			<div class="table1">
				<table class="data" style="width:260px">
					<tr class="tr1">
						<td class="title">코드 그룹</td>
						<td class="input" style="border-right:none; float:left; width:238px">
							<input type="text" id="cdGroup" name="cdGroup" style="width:220px;" />
						</td>
					</tr>
					<tr class="tr1">
						<td class="title">코드 값</td>
						<td class="input" style="border-right:none; float:left; width:238px">
							<input type="text" id="cdVal" name="cdVal" style="width:220px;" />
						</td>
					</tr>
					<tr class="tr1">
						<td class="title">코드명</td>
						<td class="input" style="border-right:none; float:left; width:238px">
							<input type="text" id="cdNm" name="cdNm" style="width:220px;" />
						</td>
					</tr>
					<tr class="tr1">
						<td class="title">순서</td>
						<td class="input" style="border-right:none; float:left; width:238px">
							<input type="text" id="seqno" name="seqno" style="width:220px;" maxlength="7" />
						</td>
					</tr>
					<tr class="tr1">
						<td class="title">사용여부</td>
						<td class="input" style="border-right:none; float:left; width:238px">
							<select id="uzYn" name="uzYn" style="width:224px">
								<option value="Y">Y</option>
								<option value="N">N</option>
							</select>
						</td>
					</tr>
				</table>
			</div>
		</form:form>
		<div class="popupbutton" style="float:right; margin-top:10px">
			<span id="btnSaveCode" class="download" style="width:90px !important">저장</span>
			<span id="closePop" class="close">취소</span>
		</div>
	</div>
	
	<%@ include file="/WEB-INF/jsp/include/frame/footer.jsp"%>
	
</body>
</html>