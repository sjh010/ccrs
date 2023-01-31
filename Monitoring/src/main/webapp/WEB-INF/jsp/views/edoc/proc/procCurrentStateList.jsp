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
		document.location.href = "${_context}/edoc/proc/current";
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
        <sec:authorize access="hasRole('ROLE_A02_LIST')">
    	jsGetProcList();
    	</sec:authorize>
    }).css("cursor", "pointer");
	
	jsSetCode();
	jsInitFilter();
	<sec:authorize access="hasRole('ROLE_A02_LIST')">
	jsGetProcList();
	</sec:authorize>
	<sec:authorize access="!hasRole('ROLE_A02_LIST')">
	$("#tbodyProcList").append('<tr><td colspan="10">조회 권한이 없습니다.</td></tr>');
	</sec:authorize>
});

var procsTypeCodeList = {};
var procsStepCodeList = {};
var procsStepStatusCodeList = {};
var workCodeList = {};
function jsSetCode() {
	<c:forEach var="procsTypeCode" items="${procsTypeCodeList }" varStatus="status">
	procsTypeCodeList["<c:out value="${procsTypeCode.cdVal}" />"] = "<c:out value="${procsTypeCode.cdNm}" />";
	</c:forEach>
	<c:forEach var="procsStepCode" items="${procsStepCodeList }" varStatus="status">
	procsStepCodeList["<c:out value="${procsStepCode.cdVal}" />"] = "<c:out value="${procsStepCode.cdNm}" />";
	</c:forEach>
	<c:forEach var="procsStepStatusCode" items="${procsStepStatusCodeList }" varStatus="status">
	procsStepStatusCodeList["<c:out value="${procsStepStatusCode.cdVal}" />"] = "<c:out value="${procsStepStatusCode.cdNm}" />";
	</c:forEach>
	<c:forEach var="workCode" items="${workCodeList }" varStatus="status">
	workCodeList["<c:out value="${workCode.cdVal}" />"] = "<c:out value="${workCode.cdNm}" />";
	</c:forEach>
}

function jsInitFilter() {
	$("#displayStartDate").val(addDash($("#startDate").val().substring(0,8)));
	$("#displayEndDate").val(addDash($("#endDate").val().substring(0,8)));
	$("#displayStartHour").val($("#startDate").val().substring(8,10));
	$("#displayEndHour").val($("#endDate").val().substring(8,10));
	$("#displayStartMinute").val($("#startDate").val().substring(10,12));
	$("#displayEndMinute").val($("#endDate").val().substring(10,12));
	$("#mainKey").val("<c:out value="${edocProcListForm.mainKey}" />");
	$("#insourceId").val("<c:out value="${edocProcListForm.insourceId}" />");
	$("#dsrbCd").val("<c:out value="${edocProcListForm.dsrbCd}" />");
	$("#hndrNo").val("<c:out value="${edocProcListForm.hndrNo}" />");
	$("#prcsTycd").val("<c:out value="${edocProcListForm.prcsTycd}" />");
	$("#procsStepCd").val("<c:out value="${edocProcListForm.procsStepCd}" />");
	$("#procsStepStcd").val("<c:out value="${edocProcListForm.procsStepStcd}" />");
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
					htmlStr += '<tr mainKey="'+item.mainKey+'" style="cursor: pointer;">';	
					htmlStr += '	<td>'+($("#pageSize").val()*($("#pageNo").val()-1)+(parseInt(idx)+1))+'</td>';
					htmlStr += '	<td>'+item.mainKey+'</td>';	
					htmlStr += '	<td>'+item.dsrbCd+'</td>';
					htmlStr += '	<td>'+item.dsrbNm+'</td>';
					htmlStr += '	<td>'+item.hndrNo+'</td>';
					htmlStr += '	<td>'+setDateTimeFormatFromDate(new Date(item.crtnTime))+'</td>';
					htmlStr += '	<td>'+(workCodeList[item.insourceId]?workCodeList[item.insourceId]:item.insourceId)+'</td>';
					if (item.procsStepStcd != 9) {
						<sec:authorize access="hasRole('ROLE_A02_DTAL')">
						htmlStr += '	<td style="text-decoration: underline;"><a href="javascript:jsDetail(\''+item.elecDocGroupInexNo+'\');">'+item.elecDocGroupInexNo+'</a></td>';
						</sec:authorize>
						<sec:authorize access="!hasRole('ROLE_A02_DTAL')">
						htmlStr += '	<td>'+item.elecDocGroupInexNo+'</td>';
						</sec:authorize>						
					} else {
						<sec:authorize access="hasRole('ROLE_A02_DTAL')">
						htmlStr += '	<td style="text-decoration: underline;"><a href="javascript:jsErrorDetail(\''+item.elecDocGroupInexNo+'\');">'+item.elecDocGroupInexNo+'</a></td>';
						</sec:authorize>
						<sec:authorize access="!hasRole('ROLE_A02_DTAL')">
						htmlStr += '	<td>'+item.elecDocGroupInexNo+'</td>';
						</sec:authorize>						
					}
					htmlStr += '	<td>'+(procsStepCodeList[item.procsStepCd]?procsStepCodeList[item.procsStepCd]:item.procsStepCd)+'</td>';
					htmlStr += '	<td>'+(procsStepStatusCodeList[item.procsStepStcd]?procsStepStatusCodeList[item.procsStepStcd]:item.procsStepStcd)+'</td>';
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
	$("#edocProcListForm").attr("action", "${_context}/edoc/proc/current");
	$("#edocProcListForm").submit();
}

function jsDetail(elecDocGroupInexNo) {
	$("#divCurrentStateDetailPopup").empty();
	$("#divCurrentStateDetailPopup").load("${_context}/edoc/proc/current/"+elecDocGroupInexNo);
}

function jsErrorDetail(elecDocGroupInexNo) {
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
	$("#edocProcListForm").attr("action", "${_context}/edoc/proc/current");
	$("#edocProcListForm").submit();
}
</script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/include/frame/top.jsp"%>
	
	<!-- contents start -->
	<form:form id="edocProcListForm" name="edocProcListForm">
		<div class="search">
			<h2>창구 전자문서 처리현황</h2>
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
						<td class="title title2" rowspan="3"><span <sec:authorize access="hasRole('ROLE_A02_LIST')">onclick="jsSearch()"</sec:authorize> class="btn1" style="height:106px;">조회</span></td>
					</tr>
					<tr class="tr1">
						<td class="title">직원번호</td>
						<td class="input"><input type="text" id="hndrNo" name="hndrNo"></td>
						<td class="title">업무</td>
						<td class="input">
							<select id="insourceId" name="insourceId">
								<option value="">전체</option>
								<c:forEach var="workCode" items="${workCodeList }" varStatus="status">
	                                <option value="<c:out value="${workCode.cdVal }" />"><c:out value="${workCode.cdNm }" /></option>
	                            </c:forEach>
							</select>
						</td>
					</tr>
					<tr class="tr1">
						<td class="title">통합전자문서키</td>
						<td class="input"><input type="text" id="mainKey" name="mainKey"></td>
						<td class="title">처리단계</td>
						<td class="input">
							<select id="procsStepCd" name="procsStepCd">
								<option value="">전체</option>
								<c:forEach var="procsStepCode" items="${procsStepCodeList }" varStatus="status">
	                                <option value="<c:out value="${procsStepCode.cdVal }" />"><c:out value="${procsStepCode.cdNm }" /></option>
	                            </c:forEach>
							</select>
						</td>
					</tr>
					<tr class="tr1">
						<td class="title">상태</td>
						<td class="input">
							<select id="procsStepStcd" name="procsStepStcd">
								<option value="">전체</option>
								<c:forEach var="procsStepStatusCode" items="${procsStepStatusCodeList }" varStatus="status">
	                                <option value="<c:out value="${procsStepStatusCode.cdVal }" />"><c:out value="${procsStepStatusCode.cdNm }" /></option>
	                            </c:forEach>
							</select>
						</td>
						<td class="title"></td>
						<td class="input"></td>
						<td class="title title2">
							<span id="btnInitFilter" class="btn1 refresh">
								<img src="<c:url value="/images/btn_refresh.png" />" style="float:left; margin-top:4px; margin-left:18px; margin-right:-20px;">
							초기화</span>
						</td>
					</tr>
				</table>
			</div>
			<table id="count" summary="전자문서열람건수" style="margin-top:30px; clear:both;">
				<colgroup>
					<col span="3" style="border:none;">
				</colgroup>
				<tr>
					<th>조회결과&nbsp;</th>
					<th id="thTotalCount">0</th>
					<th>&nbsp;건</th>
				</tr>
			</table>
			<table class="output" width="1220px">
				<thead id="theadProcList">
					<tr>
						<th>No</th>
						<th>통합전자문서키<span data-sort-key="mainKey" class="btn-sorting sorting-down-disabled"></span></th>
						<th>지부코드<span data-sort-key="dsrbCd" class="btn-sorting sorting-down-disabled"></span></th>
						<th>지부명<span data-sort-key="dsrbNm" class="btn-sorting sorting-down-disabled"></span></th>
						<th>직원번호<span data-sort-key="hndrNo" class="btn-sorting sorting-down-disabled"></span></th>
						<th>거래일시<span data-sort-key="crtnTime" class="btn-sorting sorting-down"></span></th>
						<th>업무<span data-sort-key="insourceId" class="btn-sorting sorting-down-disabled"></span></th>
						<th>전자문서번호<span data-sort-key="elecDocGroupInexNo" class="btn-sorting sorting-down-disabled"></span></th>
						<th>처리단계<span data-sort-key="procsStepCd" class="btn-sorting sorting-down-disabled"></span></th>
						<th>상태<span data-sort-key="procsStepStcd" class="btn-sorting sorting-down-disabled"></span></th>
					</tr>
				</thead>
				<tbody id="tbodyProcList"></tbody>
			</table>
			
			<!-- paging -->
			<div id="divPaging" class="prev"></div>
			
			<%@ include file="/WEB-INF/jsp/include/frame/bottom.jsp"%>
		</div>
		
		<input type="hidden" id="startDate" name="startDate" value="<c:out value="${edocProcListForm.startDate }" />" />
		<input type="hidden" id="endDate" name="endDate" value="<c:out value="${edocProcListForm.endDate }" />" />
		<input type="hidden" id="pageNo" name="pageNo" value="<c:out value="${edocProcListForm.pageNo }" />" />
		<input type="hidden" id="pageSize" name="pageSize" value="<c:out value="${edocProcListForm.pageSize }" />" />
		<input type="hidden" id="pageBlock" name="pageBlock" value="<c:out value="${edocProcListForm.pageBlock }" />" />
		<input type="hidden" id="sortKey" name="sortKey" value="<c:out value="${edocProcListForm.sortKey }" />">
        <input type="hidden" id="sortOrder" name="sortOrder" value="<c:out value="${edocProcListForm.sortOrder }" />">
		
	</form:form>
	<!-- contents end -->
	
	<!-- 진행중/완료 상세 조회 팝업 -->
	<div id="divCurrentStateDetailPopup" class="popup-wrapper" style="width:960px; display: none;"></div>
	<!-- <div id="divCurrentStateDetailPopup" style="display: none;"></div> -->
	
	<!-- 실패 상세 조회 팝업 -->
	<div id="divErrorDetailPopup" class="popup-wrapper" style="width:760px; height:auto; display: none;"></div>
	
	<%@ include file="/WEB-INF/jsp/include/frame/footer.jsp"%>
</body>
</html>