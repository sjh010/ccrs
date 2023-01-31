<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="_context" value="${(pageContext.request.contextPath eq '/')? '': pageContext.request.contextPath}" />

<script type="text/javascript">
$(document).ready(function() {
	$(".btnPopupClose").click(function(e) {
		jsHideBlockLayer("#divStatisticsDetailPopup");
	});
	$("#btnInitStatFilter").click(function() {
		jsInitStatFilter();
	});
	$("#workTypeStat").change(function() {
		var workType = ($(this).val() == "" ? "ALL" : $(this).val());
		jsSetStatWorkCodeListSelectBoxByWorkType(workType);
	}); 
	
	var agent = navigator.userAgent.toLowerCase();
	if ((navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) 
			|| (agent.indexOf("msie") != -1) ) {
		// do nothing
	} else {
		//jsSetStatCode();
	}
	jsSetStatCode();
	jsInitStatFilter();
	jsGetStatProcList();
});

var procsStepCodeList = {};
var procsStepStatusCodeList = {};
var workTypeCodeList = {};
var workCodeList = {};
function jsSetStatCode() {
	<c:forEach var="procsStepCode" items="${procsStepCodeList }" varStatus="status">
	procsStepCodeList["<c:out value="${procsStepCode.cdVal}" />"] = "<c:out value="${procsStepCode.cdNm}" />";
	</c:forEach>
	<c:forEach var="procsStepStatusCode" items="${procsStepStatusCodeList }" varStatus="status">
	procsStepStatusCodeList["<c:out value="${procsStepStatusCode.cdVal}" />"] = "<c:out value="${procsStepStatusCode.cdNm}" />";
	</c:forEach>
	<c:forEach var="workTypeCode" items="${workTypeCodeList }" varStatus="status">
	workTypeCodeList["<c:out value="${workTypeCode.cdVal}" />"] = "<c:out value="${workTypeCode.cdNm}" />";
	</c:forEach>
	<c:forEach var="workCode" items="${workCodeList }" varStatus="status">
	workCodeList["<c:out value="${workCode.cdVal}" />"] = "<c:out value="${workCode.cdNm}" />";
	</c:forEach>
}

function jsInitStatFilter() {
	$("#cstnoStat").val("");
	$("#elecDocGroupInexNoStat").val("");
	$("#procsStepStcdStat").val("");
	jsSetFormData();
}

function jsGetStatProcList() {
	$("#tbodyStatProcList tr").remove();
	
	jsTrimInputValue("edocStatProcListForm");
	jsAjaxPost("${_context}/edoc/proc/statistics/proc/list", $("#edocStatProcListForm").serialize(), function(response) {
		if (response.result == "success") {
			if (response.list && response.list != null && response.list.length > 0) {
				for (var idx in response.list) {
					var item = response.list[idx];
					var htmlStr = '';
					htmlStr += '<tr elecDocGroupInexNo="'+item.elecDocGroupInexNo+'" style="cursor: pointer;">';	
					//htmlStr += '	<td>'+($("#pageSizeStat").val()*($("#pageNoStat").val()-1)+(parseInt(idx)+1))+'</td>';
					htmlStr += '	<td>'+setDateTimeFormatFromDate(new Date(item.regTime))+'</td>';
					htmlStr += '	<td>'+item.dsrbCd+'</td>';
					htmlStr += '	<td>'+item.dsrbNm+'</td>';
					htmlStr += '	<td>'+item.hndrNm+"&nbsp;("+item.hndrNo+')</td>';
					//htmlStr += '	<td>'+(workTypeCodeList[jsGetWorkType(item.insourceId)]?workTypeCodeList[jsGetWorkType(item.insourceId)]:jsGetWorkType(item.insourceId))+'</td>';
					htmlStr += '	<td>'+(workTypeCodeList[item.insourceId]?workTypeCodeList[item.insourceId]:item.insourceId)+'</td>';
					//htmlStr += '	<td>'+(workCodeList[item.insourceId]?workCodeList[item.insourceId]:item.insourceId)+'</td>';
					htmlStr += '	<td>'+item.elecDocGroupInexNo+'</td>';
					htmlStr += '	<td>'+(procsStepCodeList[item.procsStepCd]?procsStepCodeList[item.procsStepCd]:item.procsStepCd)+'</td>';
					htmlStr += '	<td>'+(procsStepStatusCodeList[item.procsStepStcd]?procsStepStatusCodeList[item.procsStepStcd]:item.procsStepStcd)+'</td>';
					htmlStr += '</tr>';
					
					$("#tbodyStatProcList").append(htmlStr);
				}
				
				jsPageNumbering($("#pageNoStat").val(), response.totalCount, $("#pageSizeStat").val());
				jsShowBlockLayer("#divStatisticsDetailPopup");
			} else {
				// 데이터 없음
				$("#tbodyStatProcList").append('<tr><td colspan="8">조회 결과가 없습니다.</td></tr>');
				$("#divPaging").children().remove();
			}
			
			$("#thStatTotalCount").text(response.totalCount);
		} else {
			// 요청 실패
		}
	});
}

function jsGetWorkType(insourceId) {
	if (parseInt(insourceId)) {
		var _insourceId = parseInt(insourceId);
		var _workType = "0";
		if (_insourceId >= 0 && _insourceId < 10) {
			_workType += "1";
		} else {
			_workType += Math.floor(_insourceId/10);
		} 
		return (workTypeCodeList[_workType]?workTypeCodeList[_workType]:_workType);	
	} else {
		return "-";
	}
	
}

function jsSetStatWorkCodeListSelectBoxByWorkType(workType) {
	$("#insourceIdStat option").remove();
	$("#insourceIdStat").append('<option value="">전체</option>');
	
	if (workType == "ALL") {
		<c:forEach var="workCode" items="${workCodeList }" varStatus="status">
		$("#insourceIdStat").append('<option value="<c:out value="${workCode.cdVal}" />"><c:out value="${workCode.cdNm}" /></option>');
		</c:forEach>
	} else {
		var _workType = parseInt(workType);
		var startIndex = 0;
		var endIndex = 0;
		if (_workType == 1) {
			startIndex = 0;
			endIndex = 20;
		} else {
			startIndex = _workType*10;
			endIndex = (_workType+1)*10;
		}
		
		for (var i = startIndex; i < endIndex; i++) {
			var code = (i<10?"0"+i:i+"");
			if (workCodeList[code]) {
				$("#insourceIdStat").append('<option value="'+code+'" '+(originWorkCode == code ? "selected" : "")+'>'+workCodeList[code]+'</option>');
			}
		}
	}
}

function jsPage(pageNo) {
	$("#pageNoStat").val(pageNo);
	jsGetStatProcList();
}

function jsStatSearch() {
	jsGetStatProcList();
}

var originWorkCode = "";
function jsSetFormData() {
	var paramStr = $("#statParamStr").val();
	var paramArray = paramStr.split(":");

	$("#dsrbCdStat").val(paramArray[0]);
	$("#hndrNoStat").val(paramArray[1]);
	$("#workTypeStat").val(paramArray[3]);
	//$("#workTypeStat").val(paramArray[2]);
	//$("#insourceIdStat").val(paramArray[3]);
	
	// 탭 - 프로세스 유형
	$("#prcsTycdStat").val($("#prcsTycd").val());
	
	// 목록 필터링
	$("#startDateStat").val($("#startDate").val());
	$("#endDateStat").val($("#endDate").val());
	
	if ($("#workTypeStat").val() && $("#workTypeStat").val() != "") {
		originWorkCode = paramArray[3];
		jsSetStatWorkCodeListSelectBoxByWorkType($("#workTypeStat").val());
	}
}
</script>
	<form:form id="edocStatProcListForm" name="edocStatProcListForm">
		<div class="popup_top">
			<h2>전자문서 처리내역</h2>	
		</div>
		<a href="javascript:;" class="btn_popup_close btnPopupClose"></a>
		<div class="table1" style="width: 100%;">
			<table class="data" style="width: 100%;">
				<tr class="tr1">
					<td class="title">지부</td>
					<td class="input">
						<input type="text" id="dsrbCdStat" name="dsrbCd" value="<c:out value="${edocStatProcListForm.dsrbCd }" />" style="width:160px; margin-right:4px">
					</td>
					<td class="title">직원번호</td>
					<td class="input"><input type="text" id="hndrNoStat" name="hndrNo" value="<c:out value="${edocStatProcListForm.hndrNo }" />" style="width:156px; margin-right:4px"></td>
					<td class="title title2" rowspan="2"><span onclick="jsStatSearch()" class="btn1" style="height:72px; width: 100%;">조회</span></td>
				</tr>
				<tr class="tr1">
					<td class="title">업무유형</td>
					<td class="input">
						<select id="workTypeStat" name="workType">
							<option value="">전체</option>
							<c:forEach var="workTypeCode" items="${workTypeCodeList }" varStatus="status">
                                <option value="<c:out value="${workTypeCode.cdVal }" />"><c:out value="${workTypeCode.cdNm }" /></option>
                            </c:forEach>
						</select>
						<%-- <select id="insourceIdStat" name="insourceId">
							<option value="">전체</option>
							<c:forEach var="workCode" items="${workCodeList }" varStatus="status">
                                <option value="<c:out value="${workCode.cdVal }" />"><c:out value="${workCode.cdNm }" /></option>
                            </c:forEach>
						</select> --%>
					</td>
					<td class="title">상태</td>
					<td class="input">
						<select id="procsStepStcdStat" name="procsStepStcd">
							<option value="">전체</option>
							<c:forEach var="procsStepStatusCode" items="${procsStepStatusCodeList }" varStatus="status">
                                <option value="<c:out value="${procsStepStatusCode.cdVal }" />"><c:out value="${procsStepStatusCode.cdNm }" /></option>
                            </c:forEach>
						</select>
					</td>
				</tr>
				<tr class="tr1">
					<td class="title">전자문서 번호</td>
					<td class="input"><input type="text" id="elecDocGroupInexNoStat" name="elecDocGroupInexNo" style="width:300px; margin-right:4px"></td>
					<td class="title"></td>
					<td class="input"></td>
					<td class="title title2">
						<span id="btnInitStatFilter" class="btn1 refresh" style="height:100%; width: 100%;"><img class="refresh_img" src="<c:url value="/images/btn_refresh.png" />">초기화</span>
					</td>
				</tr>
			</table>
		</div>
		<div class="popup_search">
			<table id="count" summary="전자문서열람건수" style="margin-top:10px;">
				<colgroup>
					<col span="3" style="border:none;">
				</colgroup>
				<tr>
					<th>조회결과&nbsp;</th>
					<th id="thStatTotalCount"></th>
					<th>&nbsp;건</th>
				</tr>
			</table>
			<table class="output" style="width: 100%">
				<thead>
					<tr>	
						<!-- <th>No</th> -->
						<th>거래 일시</th>
						<th>지부코드</th>
						<th>지부명</th>
						<th>직원명 (직원번호)</th>
						<!-- <th>업무유형</th> -->
						<th>업무</th>
						<th>전자문서 번호</th>
						<th>처리단계</th>
						<th>상태</th>
					</tr>			
				</thead>
				<tbody id="tbodyStatProcList"></tbody>
			</table>
			
			<!-- paging -->
			<div id="divPaging" class="prev"></div>
			
		</div>
		<div class="popupbutton" style="float:right !important;">
			<span class="close btnPopupClose">닫기</span>
		</div>
		
		<!-- 통계 화면 목록 필터링 파라미터 -->
		<input type="hidden" id="startDateStat" name="startDate" value="<c:out value="${edocStatProcListForm.startDate }" />" />
		<input type="hidden" id="endDateStat" name="endDate" value="<c:out value="${edocStatProcListForm.endDate }" />" />
		<input type="hidden" id="pageNoStat" name="pageNo" value="<c:out value="${edocStatProcListForm.pageNo }" />" />
		<input type="hidden" id="pageSizeStat" name="pageSize" value="<c:out value="${edocStatProcListForm.pageSize }" />" />
		<input type="hidden" id="pageBlockStat" name="pageBlock" value="<c:out value="${edocStatProcListForm.pageBlock }" />" />
		
	</form:form>