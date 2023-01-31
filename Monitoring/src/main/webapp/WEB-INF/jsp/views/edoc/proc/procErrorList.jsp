<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/frame/header.jsp"%>
<%@ include file="/WEB-INF/jsp/include/frame/title.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	$("#displayStartDate").datepicker({
		maxDate:'today',
		onSelect: function(dateText, inst) {
			$('#displayEndDate').datepicker('option', {
	            minDate: $(this).datepicker('getDate')
	        });
		}
	});
	$("#displayEndDate").datepicker({
		maxDate:'today',
		onSelect: function(dateText, inst) {
			$('#displayStartDate').datepicker('option', {
	            //maxDate: $(this).datepicker('getDate')
	        });
		}
	});
	
	$("#btnInitFilter").click(function() {
		document.location.href = "${_context}/edoc/proc/error";
	});
	
	$("input[name='checkboxAll']").click(function() {
		if ($(this).is(':checked')) {
			$("input[name='checkbox']").prop("checked", true);
		} else {
			$("input[name='checkbox']").prop("checked", false);
		}
	});
	$("input[name='checkbox']").click(function() {
		if ($("input[name='checkbox']").length == $("input[name='checkbox']:checked").length) {
			$("input[name='checkboxAll']").prop("checked", true);
		} else {
			$("input[name='checkboxAll']").prop("checked", false);
		}
	});
	
	// sort button event
    $("#theadProcList span").click(function() {
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
        	$("#theadProcList span").removeClass("sorting-up");
        	$("#theadProcList span").removeClass("sorting-down");
        	$("#theadProcList span").addClass("sorting-down-disabled");
        	$(this).removeClass("sorting-down-disabled");
            $(this).addClass("sorting-down");
            $("#sortOrder").val("DESC");
        }
        <sec:authorize access="hasRole('ROLE_A04_LIST')">
    	jsGetProcList();
    	</sec:authorize>
    }).css("cursor", "pointer");
	
	jsSetCode();
	jsInitFilter();
	jsGetProcList();	
});

var procsTypeCodeList = {};
var procsStepCodeList = {};
function jsSetCode() {
	<c:forEach var="procsTypeCode" items="${procsTypeCodeList }" varStatus="status">
	procsTypeCodeList["<c:out value="${procsTypeCode.cdVal}" />"] = "<c:out value="${procsTypeCode.cdNm}" />";
	</c:forEach>
	<c:forEach var="procsStepCode" items="${procsStepCodeList }" varStatus="status">
	procsStepCodeList["<c:out value="${procsStepCode.cdVal}" />"] = "<c:out value="${procsStepCode.cdNm}" />";
	</c:forEach>	
}

function jsInitFilter() {
	$("#displayStartDate").val(addDash($("#startDate").val().substring(0,8)));
	$("#displayEndDate").val(addDash($("#endDate").val().substring(0,8)));
	$("#displayStartHour").val($("#startDate").val().substring(8,10));
	$("#displayEndHour").val($("#endDate").val().substring(8,10));
	$("#displayStartMinute").val($("#startDate").val().substring(10,12));
	$("#displayEndMinute").val($("#endDate").val().substring(10,12));
	$("#prcsTycd").val("<c:out value="${edocProcListForm.prcsTycd}" />");
	$("#mainKey").val("<c:out value="${edocProcListForm.mainKey}" />");
	$("#procsStepCd").val("<c:out value="${edocProcListForm.procsStepCd }" />");
	$("#hndrNo").val("<c:out value="${edocProcListForm.hndrNo}" />");
	$("#dsrbCd").val("<c:out value="${edocProcListForm.dsrbCd}" />");
	$("#svrIp").val("<c:out value="${edocProcListForm.svrIp}" />");
}

function jsGetProcList() {
	$("#tbodyProcList tr").remove();
	
	jsTrimInputValue("edocProcListForm");
	jsAjaxPost("${_context}/edoc/proc/list", $("#edocProcListForm").serialize(), function(response) {
		if (response.result == "success") {
			if (response.list && response.list != null && response.list.length > 0) {
				for (var idx in response.list) {
					var item = response.list[idx];
					var htmlStr = '';
					htmlStr += '<tr elecDocGroupInexNo="'+item.elecDocGroupInexNo+'" style="cursor: pointer;">';
					htmlStr += '	<td><input type="checkbox" name="checkbox" class="table_checkbox2"></td>';
					htmlStr += '	<td>'+item.mainKey+'</td>';
					htmlStr += '	<td>'+setDateTimeFormatFromDate(new Date(item.crtnTime))+'</td>';
					htmlStr += '	<td>'+item.dsrbCd+'</td>';
					htmlStr += '	<td>'+item.dsrbNm+'</td>';
					//htmlStr += '	<td style="text-align:left; padding-left:10px;" >'+item.hndrNo+'</td>';
					htmlStr += '	<td>'+item.hndrNo+'</td>';
					htmlStr += '	<td style="text-decoration: underline;"><a href="javascript:jsDetail(\''+item.elecDocGroupInexNo+'\');">'+item.elecDocGroupInexNo+'</a></td>';
					htmlStr += '	<td>'+(procsStepCodeList[item.procsStepCd] ? procsStepCodeList[item.procsStepCd] : item.procsStepCd)+'</td>';
					htmlStr += '	<td>'+item.svrIp+'</td>';
					htmlStr += '	<td>'+setDateTimeFormatFromDate(new Date(item.regTime))+'</td>';
					htmlStr += '</tr>';
					
					$("#tbodyProcList").append(htmlStr);
				}
				
				jsPageNumbering($("#pageNo").val(), response.totalCount, $("#pageSize").val());
			} else {
				// 데이터 없음
				$("#tbodyProcList").append('<tr><td colspan="10">조회 결과가 없습니다.</td></tr>');
				$("#divPaging").children().remove();
			}
			
			$("#thTotalCount").text(response.totalCount);
		} else {
			// 요청 실패
		}
	});
}

function jsPage(pageNo) {
	$("#pageNo").val(pageNo);
	
	jsTrimInputValue("edocProcListForm");
	$("#edocProcListForm").attr("method", "post");
	$("#edocProcListForm").attr("action", "${_context}/edoc/proc/error");
	$("#edocProcListForm").submit();
}

function jsDetail(elecDocGroupInexNo) {
	$("#divErrorDetailPopup").empty();
	$("#divErrorDetailPopup").load("${_context}/edoc/proc/error/"+elecDocGroupInexNo);
}

function jsSearch() {
	// TODO : validation 체크
	$("#startDate").val(removeDash($("#displayStartDate").val())+"000000");
	$("#endDate").val(removeDash($("#displayEndDate").val())+"235959");
	$("#pageNo").val(1);
	
	jsTrimInputValue("edocProcListForm");
	$("#edocProcListForm").attr("method", "post");
	$("#edocProcListForm").attr("action", "${_context}/edoc/proc/error");
	$("#edocProcListForm").submit();
}

function jsReprocessing() {
	if ($("#tbodyProcList input[type='checkbox']:checked").length == 0) {
		alert("재시도할 항목을 선택해 주세요.");
		return false;
	}
	
	if (confirm("선택한 항목을 재시도 하시겠습니까?")) {
		var $form = $('<form></form>');
	    $form.appendTo('body');
	    $("#tbodyProcList input[type='checkbox']:checked").each(function(index, item) {
	    	$form.append('<input type="hidden" value="'+$(item).parent().parent().attr("elecDocGroupInexNo")+'" name="edocIdxNo">');
	    });
	    $form.append('<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>');
	    
		jsAjaxPost("${_context}/edoc/proc/retry", $form.serialize(), function(response) {
			if (response.result == "success") {
				alert("파일 처리를 재시도하도록 설정 되었습니다.");
			} else {
				alert("요청을 실패하였습니다.");
			}
		});
	}
}
</script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/include/frame/top.jsp"%>
	
	<!-- contents start -->
	<form:form id="edocProcListForm" name="edocProcListForm">
		<div class="search">
			<h2>미처리 현황</h2>
			<div class="table1">
				<table class="data" style="min-width: 100%">
					<tr class="tr1">
						<td class="title">거래일</td>
						<td class="input">
							<input type="text" id="displayStartDate" class="inputdate"> 
							<span style="padding: 0 3px"> ~ </span>
							<input type="text" id="displayEndDate" class="inputdate">
						</td>
						<td class="title">지부</td>
						<td class="input"><input type="text" id="dsrbCd" name="dsrbCd"></td>
						<td class="title title2" rowspan="2"><span <sec:authorize access="hasRole('ROLE_A04_LIST')">onclick="jsSearch()"</sec:authorize> class="btn1" style="height:72px;">조회</span></td>
					</tr>
					<tr class="tr1">
						<td class="title">직원번호</td>
						<td class="input"><input type="text" id="hndrNo" name="hndrNo"></td>
						<td class="title">통합전자문서키</td>
						<td class="input"><input type="text" id="mainKey" name="mainKey"></td>
					</tr>
					<tr class="tr1">
						<td class="title">처리단계</td>
						<td class="input">
							<select id="procsStepCd" name="procsStepCd">
								<option value="">전체</option>
								<c:forEach var="procsStepCode" items="${procsStepCodeList }" varStatus="status">
	                                <option value="<c:out value="${procsStepCode.cdVal }" />"><c:out value="${procsStepCode.cdNm }" /></option>
	                            </c:forEach>
							</select>
						</td>
						<td class="title">서버 IP</td>
						<td class="input"><input type="text" id="svrIp" name="svrIp"></td>
						<td class="title title2">
							<span id="btnInitFilter" class="btn1 refresh">
								<img src="<c:url value="/images/btn_refresh.png" />" style="float:left; margin-top:4px; margin-left:18px; margin-right:-20px;">
							초기화</span>
						</td>
					</tr>
				</table>
			</div>
			<table id="count" summary="미처리 현황 건수" style="margin-top:16px; float: left;  clear:both;">
				<colgroup>
					<col span="3" style="border:none;">
				</colgroup>
				<tr>
					<th>조회결과&nbsp;</th>
					<th id="thTotalCount">0</th>
					<th>&nbsp;건</th>
				</tr>
			</table>
			<sec:authorize access="hasRole('ROLE_A04_EDIT')">
			<div class="refinebutton">
				<span onclick="jsReprocessing()" class="btn4" style="width:110px">재시도</span>
			</div>
			</sec:authorize>
			<table class="output" width="1220px">
				<thead id="theadProcList">
					<tr>
						<th><input type="checkbox" name="checkboxAll" class="table_checkbox2"></th>
						<th>통합전자문서키<span data-sort-key="mainKey" class="btn-sorting sorting-down-disabled"></span></th>
						
						<th>거래일자<span data-sort-key="crtnTime" class="btn-sorting sorting-down"></span></th>
						<th>지부코드<span data-sort-key="dsrbCd" class="btn-sorting sorting-down-disabled"></span></th>
						<th>지부명<span data-sort-key="dsrbNm" class="btn-sorting sorting-down-disabled"></span></th>
						<th>직원번호<span data-sort-key="hndrNo" class="btn-sorting sorting-down-disabled"></span></th>
						<th>전자문서번호<span data-sort-key="elecDocGroupInexNo" class="btn-sorting sorting-down-disabled"></span></th>
						<th>처리 단계<span data-sort-key="procsStepCd" class="btn-sorting sorting-down-disabled"></span></th>
						<th>서버 IP<span data-sort-key="svrIp" class="btn-sorting sorting-down-disabled"></span></th>
						<th>등록일시<span data-sort-key="regTime" class="btn-sorting sorting-down-disabled"></span></th>
					</tr>
				</thead>
				<tbody id="tbodyProcList"></tbody>
			</table>
			
			<!-- paging -->
			<div id="divPaging" class="prev"></div>
			
			<%@ include file="/WEB-INF/jsp/include/frame/bottom.jsp"%>
		</div>
		
		<input type="hidden" id="procsStepStcd" name="procsStepStcd" value="9" />
		<input type="hidden" id="startDate" name="startDate" value="<c:out value="${edocProcListForm.startDate }" />" />
		<input type="hidden" id="endDate" name="endDate" value="<c:out value="${edocProcListForm.endDate }" />" />
		<input type="hidden" id="pageNo" name="pageNo" value="<c:out value="${edocProcListForm.pageNo }" />" />
		<input type="hidden" id="pageSize" name="pageSize" value="<c:out value="${edocProcListForm.pageSize }" />" />
		<input type="hidden" id="pageBlock" name="pageBlock" value="<c:out value="${edocProcListForm.pageBlock }" />" />
		<input type="hidden" id="sortKey" name="sortKey" value="<c:out value="${edocProcListForm.sortKey }" />">
        <input type="hidden" id="sortOrder" name="sortOrder" value="<c:out value="${edocProcListForm.sortOrder }" />">
		
	</form:form>
	<!-- contents end -->
	
	<!-- 실패 상세 조회 팝업 -->
	<div id="divErrorDetailPopup" class="popup-wrapper" style="width:760px; display: none;"></div>
	
	<%@ include file="/WEB-INF/jsp/include/frame/footer.jsp"%>
</body>
</html>