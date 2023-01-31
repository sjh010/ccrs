<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<c:set var="_context" value="${(pageContext.request.contextPath eq '/')? '': pageContext.request.contextPath}" />

<script type="text/javascript">
$(document).ready(function() {
	$(".btnPopupClose").click(function(e) {
		jsHideBlockLayer("#divErrorDetailPopup");
	});
	$("#btnReprocessing").click(function() {
		jsDetailReprocessing();
	});
});

function jsDetailReprocessing() {
	if (confirm("선택한 항목을 재시도 하시겠습니까?")) {
		var elecDocGroupInexNo = "<c:out value='${detail.elecDocGroupInexNo }' />";	
		var csrfName = "<c:out value='${_csrf.parameterName}' />";
		var crsfToken = "<c:out value='${_csrf.token}' />";
		
		// cleanXss
		elecDocGroupInexNo = cleanXss(elecDocGroupInexNo);
		csrfName = cleanXss(csrfName);
		crsfToken = cleanXss(crsfToken);

		var $form = $('<form></form>');
	    $form.appendTo('body');
	    $form.append('<input type="hidden" value="'+elecDocGroupInexNo+'" name="edocIdxNo">');
	    $form.append('<input type="hidden" name="' + csrfName +'" value="' + crsfToken + "'/>");

		jsAjaxPost("${_context}/edoc/proc/retry", $form.serialize(), function(response) {
			if (response.result == "success") {
				alert("파일 처리를 재시도하도록 설정 되었습니다.");
			} else {
				// 요청 실패
			}
		}); 
	}
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

	<div style="background-color: #ffffff; width:760px; padding-left:4px; padding-bottom:10px">
		<div class="popup_top" style="margin-top:-14px; padding-top:0">
			<h2>상세 조회</h2>	
			<a href="javascript:;" class="btn_popup_close btnPopupClose" style="margin-top: 24px;margin-right:14px"></a>
		</div>
		<div class="table1" style="width: 752px;">
			<table class="output" style="width: 752px;">
				<thead>
					<tr>
						<th>서식순서</th>
						<th>서식코드</th>
						<th>총페이지수</th>
						<th>전자문서 처리단계</th>
						<!-- <th>등록직원번호</th> -->
						<th>등록일시</th>
					</tr>			
				</thead>
				<tbody id="tbodyProcFileList">	
					<c:forEach var="file" items="${detail.procFiles }" varStatus="status">
					<tr>							
						<td><c:out value="${file.fileSeqNo}" /></td>
						<!-- <td><script>cleanXss("${file.fileSeqNo}")</script></td> -->
						<td><c:out value="${file.lefrmCd }" /></td>
						<td><c:out value="${file.pageCnt }" /></td>
						<td>
						<c:forEach var="procsStepCode" items="${procsStepCodeList }" varStatus="status">
						<c:if test="${file.procsStepCd eq procsStepCode.cdVal}"><c:out value="${procsStepCode.cdNm }" /></c:if>
                        </c:forEach>
						</td>
						<td><fmt:formatDate value="${file.crtnTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					</tr>
					</c:forEach>	
				</tbody>
			</table>
		</div>
	</div>

	<div id="divFileDetail" class="content_wrap" style="margin-top: 4px;">
		<div class="popup_top" style="float:left; margin-bottom: 8px; margin-left:4px">
			<h2><span><c:out value="${detail.elecDocGroupInexNo }" /></span>&nbsp;|&nbsp;파일 에러 내역 조회</h2>	
		</div>
		<div style="clear:both"></div>
		
		<div id="divProcErrorList" class="tabcontent receive_list_userlist"> 	
			<div style="clear:both"></div>
			<div style="float:left; width: 760px; background-color: #ffffff; padding-left:4px; padding-bottom:10px">
				<table class="output" style="margin:8px 0 0 0; width: 752px;">
					<thead>
						<tr>
							<th>처리순번</th>
							<th>처리단계</th>
							<th>서버IP</th>
							<th>등록일시</th>
							<th>에러코드</th>
							<th>에러내용</th>
						</tr>		
					</thead>
					<tbody>		
						<c:forEach var="history" items="${detail.errorHistory }" varStatus="status">	
						<tr>							
							<td><c:out value="${history.seqno }" /></td>
							<td>
							<c:forEach var="procsStepCode" items="${procsStepCodeList }" varStatus="status">
								<c:if test="${history.procsStepCd eq procsStepCode.cdVal}"><c:out value="${procsStepCode.cdNm }" /></c:if>
                            </c:forEach>
							</td>
							<td><c:out value="${history.svrIp }" /></td>
							<td><fmt:formatDate value="${history.crtnTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td><c:out value="${history.procsStepMsgCd }" /></td>
							<td><c:out value="${history.errMsg }" /></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		
		<sec:authorize access="hasRole('A04_EDIT')">
		<div class="popupbutton" style="float:left !important; padding-top: 10px !important; margin-left:8px; margin-bottom:8px">
			<span id="btnReprocessing" style="width:110px">재시도</span>
		</div>
		</sec:authorize>
		<div class="popupbutton" style="float:right !important; padding-top: 10px !important; margin-right:8px; margin-bottom:8px">
			<span class="close btnPopupClose">닫기</span>
		</div>
	</div>
	
<script>
$(document).ready(function() {
	jsShowBlockLayer("#divErrorDetailPopup");
});
</script>