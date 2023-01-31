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
	
	$("#dateType").val("<c:out value="${edocProcListForm.dateType}" />");
	$("#dateType").change(function(){
		jsChangeDateType($(this).val());
	});
	
	$("#btnInitFilter").click(function() {
		document.location.href = "${_context}/edoc/proc/statistics";
	});
	$("#workType").change(function() {
		var workType = ($(this).val() == "" ? "ALL" : $(this).val());
		jsSetWorkCodeListSelectBoxByWorkType(workType);
	}); 
	jsSetCode();
	jsInitFilter();
	<sec:authorize access="hasRole('ROLE_A03_LIST')">
	jsGetStatisticsList();
	</sec:authorize>
	<sec:authorize access="!hasRole('ROLE_A03_LIST')">
	$("#tbodyProcStatisticsList").append('<tr><td colspan="9">조회 권한이 없습니다.</td></tr>');
	</sec:authorize>
});

var procsTypeCodeList = {};
var procsStepCodeList = {};
var procsStepStatusCodeList = {};
var workTypeCodeList = {};
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
	<c:forEach var="workTypeCode" items="${workTypeCodeList }" varStatus="status">
	workTypeCodeList["<c:out value="${workTypeCode.cdVal}" />"] = "<c:out value="${workTypeCode.cdNm}" />";
	</c:forEach>
	<c:forEach var="workCode" items="${workCodeList }" varStatus="status">
	workCodeList["<c:out value="${workCode.cdVal}" />"] = "<c:out value="${workCode.cdNm}" />";
	</c:forEach>
}

function jsInitFilter() {
	jsChangeDateType($("#dateType").val());
	
	$("input[name='workTypeChk']").val("<c:out value="${edocProcListForm.workTypeChk}" />");
	$("#workTypeChk").prop("checked", ("<c:out value="${edocProcListForm.workTypeChk}" />" == "on" ? true : false));			
	$("#workType").val("<c:out value="${edocProcListForm.workType}" />");			// 업무유형
	$("input[name='workType']").val("<c:out value="${edocProcListForm.workType}" />");
	
	$("input[name='insourceIdChk']").val("<c:out value="${edocProcListForm.insourceIdChk}" />");
	$("#insourceIdChk").prop("checked", ("<c:out value="${edocProcListForm.insourceIdChk}" />" == "on" ? true : false));
	$("#insourceId").val("<c:out value="${edocProcListForm.insourceId}" />");		// 업무
	$("input[name='insourceId']").val("<c:out value="${edocProcListForm.insourceId}" />");
	
	$("input[name='dsrbCdChk']").val("<c:out value="${edocProcListForm.dsrbCdChk}" />");
	$("#dsrbCdChk").prop("checked", ("<c:out value="${edocProcListForm.dsrbCdChk}" />" == "on" ? true : false));
	$("#dsrbCd").val("<c:out value="${edocProcListForm.dsrbCd}" />");			// 영업점
	$("input[name='dsrbCd']").val("<c:out value="${edocProcListForm.dsrbCd}" />");
	
	$("input[name='hndrNoChk']").val("<c:out value="${edocProcListForm.hndrNoChk}" />");
	$("#hndrNoChk").prop("checked", ("<c:out value="${edocProcListForm.hndrNoChk}" />" == "on" ? true : false));
	$("#hndrNo").val("<c:out value="${edocProcListForm.hndrNo}" />");			// 거래직원번호
	$("input[name='hndrNo']").val("<c:out value="${edocProcListForm.hndrNo}" />");	
	
	//TODO:본부코드 (3차)
}

function jsGetStatisticsList() {
	$("#theadProcStatisticsList tr").remove();
	$("#tbodyProcStatisticsList tr").remove();
	
	jsTrimInputValue("edocProcListForm");
	jsAjaxPost("${_context}/edoc/proc/statistics/list", $("#edocProcListForm").serialize(), function(response) {
		if (response.result == "success") {
			if (response.list && response.list != null && response.list.length > 0) {
				// set thead
				var htmlStr = '';
				htmlStr += '<tr>';
				htmlStr += '	<th rowspan="2">No</th>';
				//htmlStr += '	<th rowspan="2">본부코드</th>';
				if ($("#dsrbCdChk").is(":checked")) {
					htmlStr += '	<th rowspan="2">지부코드</th>';
					htmlStr += '	<th rowspan="2">지부명</th>';
				}
				if ($("#hndrNoChk").is(":checked")) htmlStr += '	<th rowspan="2">직원명 (직원번호)</th>';
				if ($("#workTypeChk").is(":checked")) htmlStr += '	<th rowspan="2">업무</th>';
				//if ($("#workTypeChk").is(":checked")) htmlStr += '	<th rowspan="2">업무유형</th>';
				//if ($("#insourceIdChk").is(":checked")) htmlStr += '	<th rowspan="2">업무</th>';
				htmlStr += '	<th>총 건수</th>';
				htmlStr += '	<th>처리완료</th>';
				htmlStr += '	<th>진행 중</th>';
				htmlStr += '	<th>에러</th>';
				htmlStr += '</tr>';
				$("#theadProcStatisticsList").append(htmlStr);
			
				// set tbody
				var totalPprCntTotal = 0;
				var totalPprCnt0 = 0;
				var totalPprCnt1 = 0;
				var tatalPprCnt9 = 0;
				for (var idx in response.list) {
					var item = response.list[idx];
					
					// 합계 row 계산
					totalPprCntTotal += item.pprCntTotal;
					totalPprCnt0 += item.pprCnt0;
					totalPprCnt1 += item.pprCnt1;
					tatalPprCnt9 += item.pprCnt9;
					
					// add row
					var htmlStr = '';
					htmlStr += '<tr filter="'+item.dsrbCd+":"+item.hndrNo+":"+item.workType+":"+item.insourceId+'" style="cursor: pointer;">';	
					htmlStr += '	<td>'+(parseInt(idx)+1)+'</td>';
					//htmlStr += '	<td></td>';
					if ($("#dsrbCdChk").is(":checked")) {
						htmlStr += '	<td>'+item.dsrbCd+'</td>';
						htmlStr += '	<td>'+item.dsrbNm+'</td>';
					}
					if ($("#hndrNoChk").is(":checked")) htmlStr += '	<td>'+item.hndrNm+'&nbsp;('+item.hndrNo+')</td>';
					//if ($("#workTypeChk").is(":checked")) htmlStr += '	<td>'+(workTypeCodeList[item.workType]?workTypeCodeList[item.workType]:item.workType)+'</td>';
					if ($("#workTypeChk").is(":checked")) htmlStr += '	<td>'+(workTypeCodeList[item.insourceId]?workTypeCodeList[item.insourceId]:item.insourceId)+'</td>';
					//if ($("#insourceIdChk").is(":checked")) htmlStr += '	<td style="text-align:left; padding-left:10px;" >'+(workCodeList[item.insourceId]?workCodeList[item.insourceId]:item.insourceId)+'</td>';
					htmlStr += '	<td>'+item.pprCntTotal+'</td>';
					htmlStr += '	<td>'+item.pprCnt1+'</td>';
					htmlStr += '	<td>'+item.pprCnt0+'</td>';
					<sec:authorize access="hasRole('ROLE_A03_DTAL')">
					htmlStr += '	<td class="procErrorList" style="text-decoration: underline;"><a href="javascript:jsProcErrorList(\''+item.dsrbCd+":"+item.hndrNo+":"+item.workType+":"+item.insourceId+'\', \'PPR\');">'+item.pprCnt9+'</a></td>';
					</sec:authorize>
					<sec:authorize access="!hasRole('ROLE_A03_DTAL')">
					htmlStr += '	<td class="procErrorList">'+item.pprCnt9+'</td>';
					</sec:authorize>
					htmlStr += '</tr>';
					
					$("#tbodyProcStatisticsList").append(htmlStr);
				}
				
				// add total row
				var htmlStr = '';
				htmlStr += '<tr>';	
				//htmlStr += '	<td colspan="'+($("input[type='checkbox']:checked").length+2)+'" class="title">합계</td>';
				
				if ($("input:checkbox[id='dsrbCdChk']").is(':checked')) {
					htmlStr += '	<td colspan="'+($("input[type='checkbox']:checked").length+2)+'" class="title">합계</td>';	
				} else {
					htmlStr += '	<td colspan="'+($("input[type='checkbox']:checked").length+1)+'" class="title">합계</td>';
				}
				
				
				htmlStr += '	<td>'+totalPprCntTotal+'</td>';
				htmlStr += '	<td>'+totalPprCnt1+'</td>';
				htmlStr += '	<td>'+totalPprCnt0+'</td>';
				<sec:authorize access="hasRole('ROLE_A03_DTAL')">
				htmlStr += '	<td style="text-decoration: underline;"><a href="javascript:jsProcErrorList(\'\', \'PPR\');">'+tatalPprCnt9+'</a></td>';
				</sec:authorize>
				<sec:authorize access="!hasRole('ROLE_A03_DTAL')">
				htmlStr += '	<td>'+tatalPprCnt9+'</td>';
				</sec:authorize>
				htmlStr += '</tr>';
				$("#tbodyProcStatisticsList").append(htmlStr);
				
				$("#tbodyProcStatisticsList td").not(".procErrorList").click(function(e) {
					jsDetail($(this).parent().attr("filter"));
				});
				
			} else {
				// 데이터 없음
				setTableThead();
				$("#tbodyProcStatisticsList").append('<tr><td colspan="9">조회 결과가 없습니다.</td></tr>');
			}
			$("#thTotalCount").text(response.list.length);
			
		} else {
			// 요청 실패
			setTableThead();
		}
	});
}

function setTableThead() {
	var htmlStr = '';
	htmlStr += '<tr>';
	htmlStr += '	<th rowspan="2">No</th>';
	//htmlStr += '	<th rowspan="2">본부코드</th>';
	if ($("#dsrbCdChk").is(":checked")) htmlStr += '	<th rowspan="2">지부</th>';
	if ($("#hndrNoChk").is(":checked")) htmlStr += '	<th rowspan="2">직원명 (직원번호)</th>';
	if ($("#workTypeChk").is(":checked")) htmlStr += '	<th rowspan="2">업무유형</th>';
	if ($("#insourceIdChk").is(":checked")) htmlStr += '	<th rowspan="2">업무</th>';
	htmlStr += '	<th>총 건수</th>';
	htmlStr += '	<th>처리완료</th>';
	htmlStr += '	<th>진행 중</th>';
	htmlStr += '	<th>에러</th>';
	htmlStr += '</tr>';
	$("#theadProcStatisticsList").append(htmlStr);
	$("#thTotalCount").text("0");
}

function jsSetWorkCodeListSelectBoxByWorkType(workType) {
	$("#insourceId option").remove();
	$("#insourceId").append('<option value="">전체</option>');
	
	if (workType == "ALL") {
		<c:forEach var="workCode" items="${workCodeList }" varStatus="status">
		$("#insourceId").append('<option value="<c:out value="${workCode.cdVal}" />"><c:out value="${workCode.cdNm}" /></option>');
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
				$("#insourceId").append('<option value="'+code+'">'+workCodeList[code]+'</option>');
			}
		}
	}
}

function jsDetail(statParamStr) {
	$("#statParamStr").val(statParamStr);
	$("#prcsTycd").val("");
	
	$("#divStatisticsDetailPopup").empty();
	$("#divStatisticsDetailPopup").load("${_context}/edoc/proc/statistics/detail");
}

function jsProcErrorList(statParamStr, procsType) {
	var paramArray = statParamStr.split(":");
	$("input[name='dsrbCd']").val(paramArray[0]);
	$("input[name='hndrNo']").val(paramArray[1]);
	$("input[name='workType']").val(paramArray[2]);
	$("input[name='insourceId']").val(paramArray[3]);
	
	$("#prcsTycd").val(procsType);
	if ($("#dateType").val() == "D") {
		startDate = removeDash($("#displayDate").val());
		endDate = removeDash($("#displayDate").val());
	} else if ($("#dateType").val() == "S") {
		startDate = removeDash($("#displayStartDate").val());
		endDate = removeDash($("#displayEndDate").val());
	} else {
		var displayDate = $("#displayDate").val().replace(/\s/gi, "");
		var dateArray = displayDate.split("~");
		startDate = removeDash(dateArray[0]);
		endDate = removeDash(dateArray[1]);
	}
	$("#startDate").val(jsGetTimeFormat(startDate, "00", "00"));
	$("#endDate").val(jsGetTimeFormat(endDate, "23", "59"));
	
	jsTrimInputValue("edocProcListForm");
	$("#edocProcListForm").attr("method", "post");
	$("#edocProcListForm").attr("action", "${_context}/edoc/proc/error");
	$("#edocProcListForm").submit();
}

function jsSearch() {
	//validation
	if ($("input[type='checkbox']:checked").length == 0) {
		alert("체크박스를 1개 이상 선택해 주세요.");
		return false;
	}
	
	jsSetPeriod();
	
	if ($("#workTypeChk").is(":checked")) {
		$("input[name='workTypeChk']").val("on");
		$("input[name='workType']").val($("#workType").val());			// 업무유형
	} else {
		$("input[name='workTypeChk']").val("off");
		$("input[name='workType']").val("");
	}
	if ($("#insourceIdChk").is(":checked")) {
		$("input[name='insourceIdChk']").val("on");
		$("input[name='insourceId']").val($("#insourceId").val());			// 업무
	} else {
		$("input[name='insourceIdChk']").val("off");
		$("input[name='insourceId']").val("");
	}
	if ($("#dsrbCdChk").is(":checked")) {
		$("input[name='dsrbCdChk']").val("on");
		$("input[name='dsrbCd']").val($("#dsrbCd").val());				// 영업점
	} else {
		$("input[name='dsrbCdChk']").val("off");
		$("input[name='dsrbCd']").val("");
	}
	if ($("#hndrNoChk").is(":checked")) {
		$("input[name='hndrNoChk']").val("on");
		$("input[name='hndrNo']").val($("#hndrNo").val());				// 거래직원번호
	} else {
		$("input[name='hndrNoChk']").val("off");
		$("input[name='hndrNo']").val("");
	}
	
	jsTrimInputValue("edocProcListForm");
	$("#edocProcListForm").attr("method", "post");
	$("#edocProcListForm").attr("action", "${_context}/edoc/proc/statistics");
	$("#edocProcListForm").submit();
}

var _today = new Date();
var monthpickerYear = _today.getFullYear(); 
function jsChangeDateType(type) {
	// 화면에 표시되는 날짜 정보  및 UI 설정
	if (type == "S") {
		$("#displayStartDate").val(addDash($("#startDate").val().substring(0,8)));
		$("#displayEndDate").val(addDash($("#endDate").val().substring(0,8)));
		$("#displayDate").hide();
		$("#displayDateSetting").show();
	} else {
		$("#displayDate").css("width", type == "D" ? "140px" : "250px");
		$("#displayDate").val(jsGetPeriodStr(type));
		$("#displayDate").show();
		$("#displayDateSetting").hide();
	}
	
	if (type == "Y") {
		var options = {
			    selectedYear: 2019,
			    startYear: 2019,
			    finalYear: 2025,
			    openOnFocus: false // Let's now use a button to show the widget
			};
		$('#displayDate').monthpicker().unbind('monthpicker-click-month');
		$('#displayDate').monthpicker().unbind('monthpicker-change-year');
		$('#displayDate').monthpicker({
			pattern: 'yyyy-mm',
			selectedYear: monthpickerYear,
			monthNames : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
		}).bind('monthpicker-click-month', function (e, month) {
			// do nothing.
		}).bind('monthpicker-change-year', function (e, year) {
			$('#displayDate').val(getPeriodYearOfDate(year));
			$(".mtz-monthpicker").parent().hide();
		});
		$('#displayDate').focus(function() {
			$(".mtz-monthpicker").parent().show();
			$("table.mtz-monthpicker").hide();
			$("#ui-datepicker-div").hide();
		});
	} else if (type == "M") {
		$('#displayDate').monthpicker().unbind('monthpicker-click-month');
		$('#displayDate').monthpicker().unbind('monthpicker-change-year');
		$('#displayDate').monthpicker({
			pattern: 'yyyy-mm',
			selectedYear: monthpickerYear,
			monthNames : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
		}).bind('monthpicker-click-month', function (e, month) {
			$('#displayDate').val(getPeriodMonthOfDate(monthpickerYear, month));
			$(".mtz-monthpicker").parent().hide();
		}).bind('monthpicker-change-year', function (e, year) {
			monthpickerYear = year;
		});
		$('#displayDate').focus(function() {
			$(".mtz-monthpicker").parent().show();
			$("table.mtz-monthpicker").show();
			$("#ui-datepicker-div").hide();
		});
	} else if (type == "W") {
		$("#ui-datepicker-div").css("display", "");
		$("#displayDate").datepicker("destroy");
		$("#displayDate").datepicker({
			maxDate:'today',
			onSelect: function(dateText, inst) {
				var dateStr = removeDash(dateText);
				var year = dateStr.substring(0, 4);
				var month = dateStr.substring(4, 6);
				var date = dateStr.substring(6, 8);
				$("#displayDate").val(getPeriodWeekOfDate(parseInt(year), parseInt(month), parseInt(date)));
			}
		});
		$("#displayDate").focus(function() {
			$(".mtz-monthpicker").parent().hide();
			$("#ui-datepicker-div").show();
		});
	} else if (type == "D") {
		$("#displayDate").datepicker("destroy");
		$("#displayDate").datepicker({
			maxDate:'today'
		});
		$("#displayDate").focus(function() {
			$(".mtz-monthpicker").parent().hide();
			$("#ui-datepicker-div").show();
		});
	} 
	
	if (type != "S") $("#displayDate").val(jsSetDisplayDateStr());
}

function jsGetPeriodStr(type) {
	var str = "";
	var year = _today.getFullYear();
	var month = _today.getMonth();
	var date = _today.getDate();
	
	if (type == "Y") {
		str = getPeriodYearOfDate(year);
	} else if (type == "M") {
		str = getPeriodMonthOfDate(year, month+1);
	} else if (type == "W") {
		str = getPeriodWeekOfDate(year, month+1, date);
	} else if (type == "D") {
		str = getToday();
	}
	return str;
}

function jsSetDisplayDateStr() {
	var str = "";
	var startDate = $("#startDate").val().substring(0,8);
	var endDate = $("#endDate").val().substring(0,8);
	var type = $("#dateType").val();
	var year = parseInt(startDate.substring(0,4));
	var month = parseInt(startDate.substring(4,6));
	var date = parseInt(startDate.substring(6,8));
	
	if (type == "Y") {
		str = getPeriodYearOfDate(year);
	} else if (type == "M") {
		str = getPeriodMonthOfDate(year, month);
	} else if (type == "W") {
		str = getPeriodWeekOfDate(year, month, date);
	} else if (type == "D") {
		str = addDash($("#startDate").val().substring(0,8));
	}
	return str;
}

function jsSetPeriod() {
	var dateType = $("#dateType").val();
	var startDate = removeDash(getToday());
	var endDate = removeDash(getToday());
	
	if (dateType == "D") {
		startDate = removeDash($("#displayDate").val());
		endDate = removeDash($("#displayDate").val());
	} else if (dateType == "S") {
		startDate = removeDash($("#displayStartDate").val());
		endDate = removeDash($("#displayEndDate").val());
	} else {
		var displayDate = $("#displayDate").val().replace(/\s/gi, "");
		var dateArray = displayDate.split("~");
		startDate = removeDash(dateArray[0]);
		endDate = removeDash(dateArray[1]);
	}
	$("#startDate").val(startDate+"0000");
	$("#endDate").val(endDate+"2359");
}

function jsDownloadExcel() {
	location.href = "${_context}/edoc/proc/statistics/excel?"+$("#edocProcListForm").serialize();
}

function jsGetTimeFormat(date, hour, min) {
	var _date, _hour, _min;
	if (parseInt(min)%5 == 0) {
		_min = min;
	} else {
		if (parseInt(min)%5 > 0 && parseInt(min)%5 < 3) {
			_min = Math.floor(parseInt(min)/5)*5;
		} else {
			_min = (Math.floor(parseInt(min)/5)+1)*5;
		}
	}
	if (_min == 60) {
		var d = new Date(parseInt(date.substring(0,4)), parseInt(date.substring(4,6))-1, parseInt(date.substring(6,8)), parseInt(hour)+1);
		_date = "" + d.getFullYear() + ((d.getMonth()+1)<10?"0"+(d.getMonth()+1):(d.getMonth()+1)) + (d.getDate()<10?"0"+d.getDate():d.getDate());
		_hour = "" + (d.getHours()<10?"0"+d.getHours():d.getHours());
		_min = "00";
	} else {
		_date = "" + date;
		_hour = "" + hour;
		_min = "" + _min;
	}
	return _date + _hour + _min;
}
</script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/include/frame/top.jsp"%>
	
	<!-- contents start -->
	<form:form id="edocProcListForm" name="edocProcListForm">
		<div class="search">
			<h2>창구 전자문서 처리통계</h2>
			<sec:authorize access="hasRole('ROLE_A03_LIST')">
			<div class="refinebutton" style="padding-bottom: 5px;">
				<span onclick="jsDownloadExcel()" class="btn4" style="width:120px">엑셀다운로드</span>
			</div>
			</sec:authorize>
			<div class="table1">
				<table class="data" style="min-width: 100%">
					<tr class="tr1">
						<td class="title">조회 기간</td>
						<td class="input">
							<select id="dateType" name="dateType" style="width:60px">
								<option value="Y">년간</option>
								<option value="M">월간</option>
								<option value="W">주간</option>
								<option value="D">일간</option>
								<option value="S">설정</option>
							</select>
							<input type="text" id="displayDate" class="inputdate" style="width:154px">
							<div id="displayDateSetting" style="display: none;">
								<input type="text" id="displayStartDate" class="inputdate" style="width:130px"><span>~</span>
								<input type="text" id="displayEndDate" class="inputdate" style="width:130px">
							</div>
						</td>
						<td class="title">본부코드</td>
						<td class="input">
							<input type="checkbox" id="" name="" class="table_checkbox" disabled="disabled"><p style="float:left; margin-top:6px">포함</p>
							<input type="text" disabled="disabled">
							<input type="hidden">
						</td>
						<td class="title title2" rowspan="2"><span <sec:authorize access="hasRole('ROLE_A03_LIST')">onclick="jsSearch()"</sec:authorize> class="btn1" style="height:72px;">조회</span></td>
					</tr>
					<tr class="tr1">
						<td class="title">지부</td>
						<td class="input">
							<input type="checkbox" id="dsrbCdChk" class="table_checkbox"><p style="float:left; margin-top:6px">포함</p>
							<input type="text" id="dsrbCd">
							<input type="hidden" name="dsrbCd">
							<input type="hidden" name="dsrbCdChk">
						</td>
						<td class="title">직원번호</td>
						<td class="input">
							<input type="checkbox" id="hndrNoChk" class="table_checkbox"><p style="float:left; margin-top:6px">포함</p>
							<input type="text" id="hndrNo">
							<input type="hidden" name="hndrNo">
							<input type="hidden" name="hndrNoChk">
						</td>
					</tr>
					<tr class="tr1">
						<td class="title">업무</td>
						<td class="input">
							<input type="checkbox" id="workTypeChk" class="table_checkbox"><p style="float:left; margin-top:6px">포함</p>
							<select id="workType">
								<option value="">전체</option>
								<c:forEach var="workTypeCode" items="${workTypeCodeList }" varStatus="status">
	                                <option value="<c:out value="${workTypeCode.cdVal }" />"><c:out value="${workTypeCode.cdNm }" /></option>
	                            </c:forEach>
							</select>
							<input type="hidden" name="workType">
							<input type="hidden" name="workTypeChk">
						</td>
						<td class="title"></td>
						<td class="input">
							<%-- <input type="checkbox" id="insourceIdChk" class="table_checkbox"><p style="float:left; margin-top:6px">포함</p>
							<select id="insourceId">
								<option value="">전체</option>
								<c:forEach var="workCode" items="${workCodeList }" varStatus="status">
	                                <option value="<c:out value="${workCode.cdVal }" />"><c:out value="${workCode.cdNm }" /></option>
	                            </c:forEach>
							</select>
							<input type="hidden" name="insourceId">
							<input type="hidden" name="insourceIdChk"> --%>
						</td>
						<td class="title title2">
							<span id="btnInitFilter" class="btn1 refresh">
								<img src="<c:url value="/images/btn_refresh.png" />" style="float:left; margin-top:4px; margin-left:18px; margin-right:-20px;">
							초기화</span>
						</td>
					</tr>
				</table>
			</div>

			<div class="content_wrap" style="margin-top:10px">
				<div class="tabcontent receive_list_userlist"> 	
					<table id="count" summary="창구 전자문서 처리현황" style="margin-top:8px; float:left; margin-right:10px">
						<colgroup>
							<col span="3" style="border:none;">
						</colgroup>
						<tr>
							<th>조회결과&nbsp;</th>
							<th id="thTotalCount">0</th>
							<th>&nbsp;건</th>
						</tr>
					</table>
					<div style="clear:both"></div>
					<div>
						<table class="output" width="1212px" style="margin:8px 4px 0 4px;">
							<thead id="theadProcStatisticsList">
								<tr>
									<th>No</th>
									<!-- <th rowspan="2">본부코드</th> -->
									<th>지부코드</th>
									<th>지부명</th>
									<th>직원명 (직원번호)</th>
									<th>업무유형</th>
									<th>업무</th>
									<th>총 건수</th>
									<th>처리완료</th>
									<th>진행 중</th>
									<th>에러</th>
								</tr>
							</thead>
							<tbody id="tbodyProcStatisticsList"></tbody>
						</table>
					</div>
				</div>
			</div>
			
			<%@ include file="/WEB-INF/jsp/include/frame/bottom.jsp"%>
		</div>
		
		<input type="hidden" id="statParamStr" name="statParamStr" value="<c:out value="${edocProcListForm.statParamStr }" />" />
		<input type="hidden" id="prcsTycd" name="prcsTycd" value="<c:out value="${edocProcListForm.prcsTycd }" />" />
		<input type="hidden" id="startDate" name="startDate" value="<c:out value="${edocProcListForm.startDate }" />" />
		<input type="hidden" id="endDate" name="endDate" value="<c:out value="${edocProcListForm.endDate }" />" />
		<input type="hidden" id="pageNo" name="pageNo" value="<c:out value="${edocProcListForm.pageNo }" />" />
		<input type="hidden" id="pageSize" name="pageSize" value="<c:out value="${edocProcListForm.pageSize }" />" />
		<input type="hidden" id="pageBlock" name="pageBlock" value="<c:out value="${edocProcListForm.pageBlock }" />" />
		
	</form:form>
	<!-- contents end -->
	
	<!-- 창구 전자문서 처리 통계 상세 팝업 -->
	<div id="divStatisticsDetailPopup" class="popup-wrapper" style="width:1200px; display: none;"></div>
	
	<%@ include file="/WEB-INF/jsp/include/frame/footer.jsp"%>
</body>
</html>