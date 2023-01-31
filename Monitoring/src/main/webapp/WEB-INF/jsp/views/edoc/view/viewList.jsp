<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobileleader.edoc.monitoring.utils.CryptoSHA2"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/frame/header.jsp"%>
<%@ include file="/WEB-INF/jsp/include/frame/title.jsp"%>

<script type="text/javascript">
$(document).ready(function() {	
	$("#pageSize").change(function() {
		
		var pageSize = $(this).val();
		
		$("#pageSize").val(pageSize);
		$("#pageNo").val(1);
		
		//jsGetViewList();
		
		jsTrimInputValue("searchRequest");
		$("#searchRequest").attr("method", "post");
		$("#searchRequest").attr("action", "${_context}/edoc/view");
		$("#searchRequest").submit();
	});
	
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
		},
	});
	
	$("#btnInitFilter").click(function() {
		document.location.href = "${_context}/edoc/view";
	});
	
	$("#tbodyProcList").on("dblclick", "tr", function(){
		jsEdocViewSform($(this).data("key"));
	});
	
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
        <sec:authorize access="hasRole('ROLE_D01_LIST')">
        jsGetViewList();
    	</sec:authorize>
    }).css("cursor", "pointer");
	
	jsSetCode();
	jsInitFilter();
	<sec:authorize access="hasRole('ROLE_D01_LIST')">
	jsGetViewList();
	</sec:authorize>
	<sec:authorize access="!hasRole('ROLE_D01_LIST')">
	$("#tbodyProcList").append('<tr><td colspan="11">조회 권한이 없습니다.</td></tr>');
	</sec:authorize>
});

var procsTypeCodeList = {};
var workTypeCodeList = {};
var workCodeList = {};
function jsSetCode() {
	<c:forEach var="procsTypeCode" items="${procsTypeCodeList }" varStatus="status">
	procsTypeCodeList["<c:out value="${procsTypeCode.cdVal}" />"] = "<c:out value="${procsTypeCode.cdNm}" />";
	</c:forEach>
	<c:forEach var="workTypeCode" items="${workTypeCodeList }" varStatus="status">
	workTypeCodeList["<c:out value="${workTypeCode.cdVal}" />"] = "<c:out value="${workTypeCode.cdNm}" />";
	</c:forEach>
	<c:forEach var="workCode" items="${workCodeList }" varStatus="status">
	workCodeList["<c:out value="${workCode.cdVal}" />"] = "<c:out value="${workCode.cdNm}" />";
	</c:forEach>
}

function jsInitFilter() {
	$("#displayStartDate").val(addDash($("#startDate").val().substring(0,8)));
	$("#displayEndDate").val(addDash($("#endDate").val().substring(0,8)));
	
	// 지부
	$("#branchCode").val("<c:out value="${searchRequest.branchCode}" />");
	// 고객명
	$("#customerName").val("<c:out value="${searchRequest.customerName}" />");
	// 통합전자문서키
	$("#mainKey").val("<c:out value="${searchRequest.mainKey}" />");
	// 업무
	$("#insourceId").val("<c:out value="${searchRequest.insourceId}" />");
	// 심사담당자
	$("#employeeName").val("<c:out value="${searchRequest.employeeName}" />");
	// 주민번호
	$("#memo").val("<c:out value="${searchRequest.memo}" />");

	// 페이지 사이즈
	$("#pageSize").val("<c:out value="${searchRequest.pageSize}" />");
}

var createResult = true;
function jsGetViewList() {	
	$("#tbodyProcList").empty();
	
	jsTrimInputValue("searchRequest");
	jsAjaxPost("${_context}/edoc/view/list", $("#searchRequest").serialize(), function(response) {
		
		if (response.result == "success") {
			if (response.list && response.list != null && response.list.length > 0) {
				for (var idx in response.list) {
					var item = response.list[idx];

					var htmlStr = '';
					htmlStr += '<tr data-key="' + item.mainKey + '" style="cursor: pointer;">';
					htmlStr += '	<td>' + ($("#pageSize").val()*($("#pageNo").val() - 1) + (parseInt(idx) + 1))+'</td>';
					htmlStr += '    <td>' + nvl(item.mainKey) +'</td>';
					htmlStr += '    <td>' + nvl(item.branchCode) +'</td>';
					htmlStr += '	<td>' + nvl(item.branchTitle) + '</td>';
					htmlStr += '	<td>' + nvl(item.insourceId) + '</td>';
					htmlStr += '	<td>' + nvl(workTypeCodeList[item.insourceId]) + '</td>';

					htmlStr += '	<td>' + nvl(item.employeeId) + '</td>';
					htmlStr += '	<td>' + nvl(item.employeeName) + '</td>';
					
					htmlStr += '    <td>' + nvl(item.customerName) + '</td>';
					htmlStr += '    <td>' + nvl(item.guideName) + '</td>';
					htmlStr += '    <td>' + nvl(setDateTimeFormatFromDate(parseDate(item.createTime))) + '</td>';
					htmlStr += '	<td></td>';
					htmlStr += '</tr>';
					
					$("#tbodyProcList").append(htmlStr);
				}
				
				jsPageNumbering($("#pageNo").val(), response.totalCount, $("#pageSize").val());
			} else {
				// 데이터 없음
				$("#tbodyProcList").append('<tr><td colspan="11">조회 결과가 없습니다.</td></tr>');
				$("#divPaging").children().remove();
			}
			
			$("#thTotalCount").text(response.totalCount);
		} else {
			// 요청 실패
		}
	});
}

function parseDate(strDate) {
	var _strDate = strDate;
	var _dateObj = new Date(_strDate);
	if (_dateObj.toString() === 'Invalid Date') {
		_strDate = _strDate.split('.').join('-');
		_dateObj = new Date(_strDate);
	}
	if (_dateObj.toString() === 'Invalid Date') {
		var _parts = _strDate.split(' ');
		var _dateParts = _parts[0];
		
		_dateObj = new Date(_dateParts);
		
		if (_parts.length > 1) {
			var _timeParts = _parts[1].split(':');

			_dateObj.setHours(_timeParts[0]);
			_dateObj.setMinutes(_timeParts[1]);
			
			if (_timeParts.length > 2) {
				_dateObj.setSeconds(_timeParts[2].split('-')[0]);
			}
		}
	}

	return _dateObj;
}

function jsPage(pageNo) {
	$("#pageNo").val(pageNo);
	
	jsTrimInputValue("searchRequest");
	$("#searchRequest").attr("method", "post");
	$("#searchRequest").attr("action", "${_context}/edoc/view");
	$("#searchRequest").submit();
	//jsGetViewList();
}

function jsSearch() {
	// TODO : validation 체크
	$("#startDate").val(removeDash($("#displayStartDate").val()) + "000000");
	$("#endDate").val(removeDash($("#displayEndDate").val()) + "235959");

	$("#pageNo").val(1);
	
	jsTrimInputValue("searchRequest");
	$("#searchRequest").attr("method", "post");
	$("#searchRequest").attr("action", "${_context}/edoc/view");
	$("#searchRequest").submit();

	//jsGetViewList();
}

function jsEdocViewSform(mainKey) {
	var agent = navigator.userAgent.toLowerCase();
	if ((agent.indexOf("msie 8.0") != -1)) {				// IE8 체크
		alert("인터넷 익스플로러8 브라우저에서는 뷰어 기능을 지원하지 않습니다.");
	} else {
		var options = "width=" + screen.width + ",height=" + screen.height; 
		var self = window.open("${_context}/edoc/view/sform/" + mainKey, "_blank", options);
		self.moveTo(0,0);
		self.resizeTo(screen.availWidth, screen.availHeight);
	}
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
</script>
</head>
<body>
  <%@ include file="/WEB-INF/jsp/include/frame/top.jsp"%>
  
  <!-- contents start -->
  <form:form id="searchRequest" name="searchRequest">
  	<div class="search">
      <h2>통합전자문서 조회</h2>
      <div class="table1"  style="margin-bottom: 10px;">
      	<table class="data" style="min-width: 100%">
          <tr class="tr1">
          	<td class="title">지부</td>
          	<td class="input">
          		<select id="branchCode" name="branchCode">
	                <option value="">전체</option>
	                <c:forEach var="dapartment" items="${departmentList }" varStatus="status">
	                  <option value="<c:out value="${dapartment.BRANCH_CODE }" />"><c:out value="${dapartment.BRANCH_TITLE }" /></option>
	                </c:forEach>
	              </select>
	        </td>
	        
	        <td class="title">업무</td>
          	<td class="input">
              <select id="insourceId" name="insourceId">
                <option value="">전체</option>
                <c:forEach var="workCode" items="${workTypeCodeList }" varStatus="status">
                   <option value="<c:out value="${workCode.cdVal }" />"><c:out value="${workCode.cdNm }" /></option>
                </c:forEach>
              </select>
          	</td>
          	
          	<td class="title title2" rowspan="3">
          		<span id="btn_search" <sec:authorize access="hasRole('ROLE_D01_LIST')">onclick="jsSearch()"</sec:authorize> class="btn1" style="height:105px; line-height: 95px;">조회</span>
          	</td>
          </tr> 
          <tr class="tr1">   
          	<td class="title">거래일시</td>
          	<td class="input">
              <input type="text" id="displayStartDate" class="inputdate"> 
              <span style="padding: 0 3px"> ~ </span>
              <input type="text" id="displayEndDate" class="inputdate">
          	</td>
          	
          	<td class="title">고객명</td>
          	<td class="input">
          		<input type="text" id="customerName" name="customerName">
          	</td>
          </tr>
          <tr class="tr1">
          	<td class="title">통합전자문서키</td>
          	<td class="input">
          		<input type="text" id="mainKey" name="mainKey">
          	</td>

          	<td class="title">심사담당자</td>
          	<td class="input"><input type="text" id="employeeName" name="employeeName"></td>
          	
<%--           	<td class="title title2">
          	  <span id="btnInitFilter" class="btn1 refresh">
          		<img src="<c:url value="/images/btn_refresh.png" />" 
          			style="float:left; margin-top:4px; margin-left:18px; margin-right:-20px;">초기화
          	  </span>
          	</td> --%>
          </tr>
          
          <tr class="tr1">
          	<td class="title">주민번호</td>
          	<td class="input">
          		<input type="text" id="memo" name="memo">
          	</td>

          	<td class="title"></td>
          	<td class="input">
          	</td>
          	
          	<td class="title title2">
          	  <span id="btnInitFilter" class="btn1 refresh">
          		<img src="<c:url value="/images/btn_refresh.png" />" 
          			style="float:left; margin-top:4px; margin-left:18px; margin-right:-20px;">초기화
          	  </span>
          	</td>
          </tr>
      	</table>
      </div>
      
      <div style="display:flex; float:right; width: 130px !important; margin-right: 60px;">
      	<select id="pageSize" name="pageSize">
	  		<option value="10">10건보기</option>
	  		<option value="20">20건보기</option>
	  		<option value="30">30건보기</option>
	  	</select>
      </div>
      
      
      <table id="count" summary="전자문서열람건수" style="margin-top:30px;">
      	<colgroup>
      	  <col span="3" style="border:none;">
      	</colgroup>
      	<tr>
          <th>조회결과&nbsp;</th>
          <th id="thTotalCount">0</th>
          <th>&nbsp;건</th>
      	</tr>
      </table>
      
      <table class="output" style="width:1220px" <sec:authorize access="hasRole('ROLE_D01_DTAL')">style="clear:both;"</sec:authorize>>
      	<thead id="theadProcList">
          <tr>
          	<th>No</th>
            <th>통합전자문서키<span data-sort-key="mainKey"></span></th>
            <th>지부코드<span data-sort-key="branchCode" class="btn-sorting sorting-down-disabled"></span></th>
            <th>지부명<span data-sort-key="branchTitle" class="btn-sorting sorting-down-disabled"></span></th>
            <th>업무코드<span data-sort-key="insourceId" class="btn-sorting sorting-down-disabled"></span></th>
            <th>업무명<span data-sort-key="insourceTitle" class="btn-sorting sorting-down-disabled"></span></th>
            <th>심사담당자사번<span data-sort-key="employeeId" class="btn-sorting sorting-down-disabled"></span></th>
            <th>심사담당자명<span data-sort-key="employeeName" class="btn-sorting sorting-down-disabled"></span></th>
            <th>고객명</th>            
          	<th>안내원명</th>
          	<th>거래일시<span data-sort-key="createTime" class="btn-sorting sorting-down"></span></th>
          </tr>
      	</thead>
      	<tbody id="tbodyProcList"></tbody>
      </table>
  		
      <!-- paging -->
      <div id="divPaging" class="prev"></div>
  		
      <%@ include file="/WEB-INF/jsp/include/frame/bottom.jsp"%>
  	</div>
  	
  	<input type="hidden" id="startDate" name="startDate" value="<c:out value="${searchRequest.startDate }" />" />
  	<input type="hidden" id="endDate" name="endDate" value="<c:out value="${searchRequest.endDate }" />" />
  	<input type="hidden" id="pageNo" name="pageNo" value="<c:out value="${searchRequest.pageNo }" />" />
  	<%-- <input type="hidden" id="pageSize" name="pageSize" value="<c:out value="${searchRequest.pageSize }" />" /> --%>
  	<input type="hidden" id="pageBlock" name="pageBlock" value="<c:out value="${searchRequest.pageBlock }" />" />
  	<input type="hidden" id="sortKey" name="sortKey" value="<c:out value="${ssearchRequest.earchCondition.sortKey }" />">
    <input type="hidden" id="sortOrder" name="sortOrder" value="<c:out value="${searchRequest.sortOrder }" />">
  </form:form>
  <!-- contents end -->
  
  <%@ include file="/WEB-INF/jsp/include/frame/footer.jsp"%>
</body>
</html>