<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/frame/header.jsp"%>
<%@ include file="/WEB-INF/jsp/include/frame/title.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	$("#displayStartDate").datepicker({
		maxDate: 'today',
		onSelect: function(dateText, inst) {
			if ($("#displayEndDate").val() == getToday() && dateText == $("#displayEndDate").val()) {
				jsSetTodayTime();
			} else {
				jsSetPeriodTime();
			}
			$('#displayEndDate').datepicker('option', {
	            minDate: $(this).datepicker('getDate')
	        });
		}
	});
	
	$("#displayEndDate").datepicker({
		maxDate: 'today',
		onSelect: function(dateText, inst) {
			if ($("#displayEndDate").val() == getToday() && dateText == $("#displayStartDate").val()) {
				jsSetTodayTime();
			} else {
				jsSetPeriodTime();
			}
			$('#displayStartDate').datepicker('option', {
	            //maxDate: $(this).datepicker('getDate')
	        });
		}
	});
	jsInitData();
	jsGetMonitoringList();
});

function jsSetTodayTime() {
	$("#displayStartHour").val("00");
	$("#displayStartMin").val("00");
	var today = new Date();
	today.setMinutes(today.getMinutes() - 5);			// 5분전 
	$("#displayEndHour").val(today.getHours() < 10 ? "0" + today.getHours() : today.getHours());
	$("#displayEndMin").val(today.getMinutes() < 10 ? "0" + today.getMinutes() : today.getMinutes());
}

function jsSetPeriodTime() {
	$("#displayStartHour").val("00");
	$("#displayStartMin").val("00");
	$("#displayEndHour").val("23");
	$("#displayEndMin").val("59");
}

function jsInitData() {
	$("#displayStartDate").val(addDash($("#startDateMonitoring").val().substring(0,8)));
	$("#displayEndDate").val(addDash($("#endDateMonitoring").val().substring(0,8)));
	$("#displayStartHour").val($("#startDateMonitoring").val().substring(8,10));
	$("#displayStartMin").val($("#startDateMonitoring").val().substring(10));
	$("#displayEndHour").val($("#endDateMonitoring").val().substring(8,10));
	$("#displayEndMin").val($("#endDateMonitoring").val().substring(10));
}

function jsGetMonitoringList() {
	$("#tbodyMonitoringList tr").remove();
	
	jsTrimInputValue("edocProcListForm");
	jsAjaxPost("${_context}/edoc/proc/monitoring/list", $("#edocProcListForm").serialize(), function(response) {
		if (response.result == "success") {
			if (response.list && response.list != null && response.list.length > 0) {
				for (var idx in response.list) {
					var item = response.list[idx];
					var htmlStr = '';
					htmlStr += '<tr>';
					htmlStr += '	<td>'+item.stepName+'</td>';
					htmlStr += '	<td>'+item.countReady+'</td>';
					htmlStr += '	<td style="text-decoration: underline;"><a href="javascript:jsProcErrorList(\''+item.stepCode+'\');">'+item.countFail+'</a></td>';
					htmlStr += '	<td>'+item.countSuccess+'</td>';
					htmlStr += '	<td>'+item.countTotal+'</td>';
					htmlStr += '</tr>';
					$("#tbodyMonitoringList").append(htmlStr);
				}
			} else {
				// 데이터 없음
				$("#tbodyMonitoringList").append('<tr><td colspan="5">조회 결과가 없습니다.</td></tr>');
			}
		} else {
			// 요청실패
		}
	});
}

function jsSearch() {
	// check validation
	/*
	var betweenDayFormToday = getBetweenDaysFromToday($("#displayStartDate").val());
	if (betweenDayFormToday < -5) {
		alert("설정 기간은 현재일 기준 5일 전 까지 가능합니다.");
		return false;
	} 
	
	var betweenDays = getBetweenDays($("#displayStartDate").val(), $("#displayEndDate").val());
	if (betweenDays < -5) {
		alert("최대 거래일 설정 기간은 5일까지 가능합니다.");
		return false;
	} 
	*/
	
	$("#startDateMonitoring").val(removeDash($("#displayStartDate").val())+$("#displayStartHour").val()+$("#displayStartMin").val());
	$("#endDateMonitoring").val(removeDash($("#displayEndDate").val())+$("#displayEndHour").val()+$("#displayEndMin").val());
	
	//document.location.href = "${_context}/edoc/proc/monitoring?"+$("#edocProcListForm").serialize();
	jsTrimInputValue("edocProcListForm");
	$("#edocProcListForm").attr("method", "post");
	$("#edocProcListForm").attr("action", "${_context}/edoc/proc/monitoring");
	$("#edocProcListForm").submit();
}

function jsProcErrorList(procsStepCd) {
	var _procsStepCd = procsStepCd == "99" ? "" : procsStepCd;
	$("#procsStepCd").val(procsStepCd);
	$("#startDate").val(jsGetTimeFormat(removeDash($("#displayStartDate").val()), $("#displayStartHour").val(), $("#displayStartMin").val()));
	$("#endDate").val(jsGetTimeFormat(removeDash($("#displayEndDate").val()), $("#displayEndHour").val(), $("#displayEndMin").val()));
	
	jsTrimInputValue("edocProcListForm");
	$("#edocProcListForm").attr("method", "post");
	$("#edocProcListForm").attr("action", "${_context}/edoc/proc/error");
	$("#edocProcListForm").submit();
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
			<h2>창구 전자문서 처리결과 모니터링</h2>
			
			<div class="table1">
				<table class="data" style="min-width: 100%">
					<tr class="tr1">
						<td class="title">거래일</td>
						<td class="input">
							<input type="text" id="displayStartDate" class="inputdate"><span>~</span>
							<input type="text" id="displayEndDate" class="inputdate">
						</td>
						<td class="title">거래 시간</td>
						<td class="input">
							<div>
								<input id="displayStartHour" type="text" style="width:40px"><p>시</p>
								<input id="displayStartMin" type="text" style="width:40px"><p>분</p>
								<p style="margin: 0 8px 0 14px">-</p>
								<input id="displayEndHour" type="text" style="width:40px"><p>시</p>
								<input id="displayEndMin" type="text" style="width:40px"><p>분</p>
							</div>
						</td>
						<td class="title title2" rowspan="3"><span onclick="javascript:jsSearch()" class="btn1" style="height:36px;">조회</span></td>
					</tr>
				</table>
			</div>
			<table class="output non_btn" width="1220px">
				<thead>
					<tr>
						<th rowspan="2" class="tb_rowspan" style="border-right: 1px solid #a3c2f3; width:40%">단계</th>
						<th colspan="4" style="width:20%">전자문서개수</th>
					</tr>
					<tr>
						<th style="width:15%">진행중 미처리</th>
						<th style="width:15%">에러 미처리</th>
						<th style="width:15%">완료</th>
						<th style="width:15%">합계</th>
					</tr>			
				</thead>
				<tbody id="tbodyMonitoringList"></tbody>
			</table>
			
			<%@ include file="/WEB-INF/jsp/include/frame/bottom.jsp"%>
		</div>
		
		<input type="hidden" id="procsStepCd" name="procsStepCd" value="<c:out value="${edocProcListForm.procsStepCd }" />" />
		<input type="hidden" id="startDate" name="startDate" value="<c:out value="${edocProcListForm.startDate }" />" />
		<input type="hidden" id="endDate" name="endDate" value="<c:out value="${edocProcListForm.endDate }" />" />
		<input type="hidden" id="startDateMonitoring" name="startDateMonitoring" value="<c:out value="${edocProcListForm.startDateMonitoring }" />" />
		<input type="hidden" id="endDateMonitoring" name="endDateMonitoring" value="<c:out value="${edocProcListForm.endDateMonitoring }" />" />
		
	</form:form>
	<!-- contents end -->
	
	<%@ include file="/WEB-INF/jsp/include/frame/footer.jsp"%>
</body>
</html>