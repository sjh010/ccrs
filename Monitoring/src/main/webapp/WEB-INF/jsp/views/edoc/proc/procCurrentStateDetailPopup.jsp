<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="_context" value="${(pageContext.request.contextPath eq '/')? '': pageContext.request.contextPath}" />

<script type="text/javascript">
$(document).ready(function() {
	$(".btnPopupClose").click(function(e) {
		jsHideBlockLayer("#divCurrentStateDetailPopup");
	});
});
</script>

	<div class="popup_top">
		<h2><span><c:out value="${detail.elecDocGroupInexNo }" /></span>&nbsp;|&nbsp;상세 조회</h2>	
	</div>
	<a href="javascript:;" class="btn_popup_close btnPopupClose"></a>
	<div class="table1" style="width: 960px;">
		<table class="output" style="width: 960px;">
			<thead>
				<tr>
					<th>서식순서</th>
					<th>서식코드</th>
					<th>총페이지수</th>
					<th>전자문서 처리단계</th>
					<th>처리일시</th>
				</tr>			
			</thead>
			<tbody id="tbodyProcFileList">	
				<c:forEach var="file" items="${detail.procFiles }" varStatus="status">
				<tr>							
					<td><c:out value="${file.fileSeqNo }" /></td>
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
	<div class="popup_top">
		<h2><span><c:out value="${detail.elecDocGroupInexNo }" /></span>&nbsp;|&nbsp;연동 정보</h2>	
	</div>
	<div class="table1" style="width: 960px;">
		<table class="output" style="width: 960px;">
			<thead>
				<tr>
					<th>문서양식CODE</th>
					<th>GUID</th>
					<th>이미지인덱스번호</th>
					<th>등록일시</th>
					<!-- <th>전자문서구분</th> -->
					<!-- <th>최종변경일시</th> -->
				</tr>
			</thead>
			<tbody  id="tbodyEcmFileList">	
				<c:forEach var="ecm" items="${detail.ecmFiles }" varStatus="status">	
				<tr>							
					<td><c:out value="${ecm.docTycd }" /></td>
					<td><c:out value="${ecm.guid }" /></td>
					<td><c:out value="${ecm.fileId }" /></td>
					<td><fmt:formatDate value="${ecm.regTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="popup_top">
		<h2><span><c:out value="${detail.elecDocGroupInexNo }" /></span>&nbsp;|&nbsp;에러 내역</h2>	
	</div>
	<div class="table1" style="width: 960px;">
		<table class="output" style="width: 960px;">
			<thead>
				<tr>
					<th>처리순번</th>
					<!-- <th>일련번호</th> -->
					<th>처리단계</th>
					<th>처리상태</th>
					<th>에러코드</th>
					<th>에러내용</th>
					<th>등록일시</th>
					<th>서버 IP</th>
				</tr>			
			</thead>
			<tbody id="tbodyErrorHistory">		
				<c:forEach var="history" items="${detail.errorHistory }" varStatus="status">	
				<tr>							
					<!-- <td>처리순번</td> -->
					<td><c:out value="${history.seqno }" /></td>
					<td>
					<c:forEach var="procsStepCode" items="${procsStepCodeList }" varStatus="status">
						<c:if test="${history.procsStepCd eq procsStepCode.cdVal}"><c:out value="${procsStepCode.cdNm }" /></c:if>
                    </c:forEach>
					</td>
					<td>
					<c:forEach var="procsStepStatusCode" items="${procsStepStatusCodeList }" varStatus="status">
						<c:if test="${history.procsStepStcd eq procsStepStatusCode.cdVal}"><c:out value="${procsStepStatusCode.cdNm }" /></c:if>
                    </c:forEach>
					</td>
					<td><c:out value="${history.procsStepMsgCd }" /></td>
					<td><c:out value="${history.errMsg }" /></td>
					<td><fmt:formatDate value="${history.crtnTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="popupbutton" style="float:right !important;padding-top: 20px !important">
		<span class="close btnPopupClose">닫기</span>
	</div>
	
<script>
$(document).ready(function() {
	jsShowBlockLayer("#divCurrentStateDetailPopup")
});
</script>