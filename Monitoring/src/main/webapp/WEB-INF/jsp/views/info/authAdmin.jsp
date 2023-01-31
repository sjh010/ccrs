<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/frame/header.jsp"%>
<%@ include file="/WEB-INF/jsp/include/frame/title.jsp"%>
<script type="text/javascript">
$(document).ready(function() {					
	$("#permList tr").click(function(e) {
		$("#permList tr td").removeClass("selected");
 		$(this).find("td").addClass("selected");
 		$("#permId").val($(this).attr("permId"));
 		jsMenuPermData();			
	});
	
	$("#permList tr[permId='${permId}']").find("td").addClass("selected");
	$("#permId").val('${permId}');		
	
	<sec:authorize access="hasRole('ROLE_C03_LIST')">
	jsMenuPermData();	
	</sec:authorize>
	<sec:authorize access="!hasRole('ROLE_C03_LIST')">
	$("#permList").append('<tr><td style="width: 307px; text-align: center;">조회 권한이 없습니다.</td></td>');
	</sec:authorize>
});		
	
function jsMenuPermData() {
	$("#menuPermTable tr .check_on").removeClass("check_on").addClass("check_off");
	
	jsTrimInputValue("permForm");
	jsAjaxPost("${_context}/info/authAdmin/list", $("#permForm").serialize(), function(response) {
		if (response.result == "success") {
			if (response.list && response.list != null && response.list.length > 0) {
				
				for (var idx in response.list) {
					var item = response.list[idx];
					var menuCd = item.menuCd;
					
					if(item.accsYn == "Y") {
						$("#" + menuCd + "_accsYn").removeClass("check_off").addClass("check_on");
					}
					if(item.listYn == "Y") {
						$("#" + menuCd + "_listYn").removeClass("check_off").addClass("check_on");
					}
					if(item.dtalYn == "Y") {
						$("#" + menuCd + "_dtalYn").removeClass("check_off").addClass("check_on");
					}
					if(item.regYn == "Y") {
						$("#" + menuCd + "_regYn").removeClass("check_off").addClass("check_on");
					}
					if(item.editYn == "Y") {
						$("#" + menuCd + "_editYn").removeClass("check_off").addClass("check_on");
					}
					if(item.delYn == "Y") {
						$("#" + menuCd + "_delYn").removeClass("check_off").addClass("check_on");
					}												
				}
			}
		} else { 
			// 요청 실패
		}
	});
};

function jsPermEdit() {
	jsShowBlockLayerWithUrl("#divPermEditPopup", "${_context}/info/authAdmin/edit?permId=" + $("#permId").val());
}
</script>
</head>
<body>

	<%@ include file="/WEB-INF/jsp/include/frame/top.jsp"%>

	<!-- content -->
	<div class="search" style="padding-bottom: 0px;">
		<div style="display: block">
			<h2>권한 관리</h2>
			<div class="refinebutton" style="margin-right: 0;">
				<sec:authorize access="hasAnyRole('ROLE_C03_REG', 'ROLE_C03_EDIT', 'ROLE_C03_DEL')">
					<span class="btn4" style="width:120px" onclick="jsPermEdit()">권한관리 수정</span>
				</sec:authorize>				
			</div>
			<form:form id="permForm" name="permForm">
				<input type="hidden" id="permId" name="permId"/>
			</form:form>
		</div>
		<div style="clear:both">
			<table class="output" style="width: 24%;">
				<thead>
					<tr>
						<th>권한</th>
					</tr>			
				</thead>
				<tbody id="permList" class="scroll_tbody" style="height:590px;">
					<sec:authorize access="hasRole('ROLE_C03_LIST')">		
					<c:forEach items="${permList}" var="perm">
						<tr permId="${perm.permId}">
							<td style="width:307px; text-align: left; padding-left: 10px;">${perm.permNm}</td>
						</tr>
					</c:forEach>	
					</sec:authorize>		
				</tbody>
			</table>
		
			<table id="menuPermTable" class="output" style="margin-left: 24px; width: 74%;">
				<thead>
					<tr>
						<th colspan="2" width="40%">메뉴/화면 이름</th>
						<th rowspan="2" width="10%">메뉴사용</th>
						<th rowspan="2" width="10%">목록조회</th>
						<th rowspan="2" width="10%">상세조회</th>
						<th rowspan="2" width="10%">등록</th>
						<th rowspan="2" width="10%">수정</th>
						<th rowspan="2" width="10%">삭제</th>
					</tr>
					<tr>
						<th>1depth</th>
						<th style="border-right: 1px solid #a3c2f3">2depth</th>
					</tr>		
				</thead>
				<tbody style="height:55px">
                    <tr id="A00">                           
                        <td style="text-align: left; padding-left: 5px;">대시보드</td>
                        <td></td>
                        <td><div id="A00_accsYn" class="check_off"></div></td>
                        <td><div id="A00_listYn" class="check_off"></div></td>
                        <td><div id="A00_dtalYn" class="check_off"></div></td>
                        <td><div id="A00_regYn"  class="check_off"></div></td>
                        <td><div id="A00_editYn" class="check_off"></div></td>
                        <td><div id="A00_delYn"  class="check_off"></div></td>
                    </tr>
					<!-- tr1 -->	
					<tr id="A01">							
						<td rowspan="4" style="text-align: left; padding-left: 5px;">전자문서 처리 관리</td>
						<td style="text-align: left; padding-left: 5px;">전자문서 처리결과 모니터링</td>
						<td><div id="A01_accsYn" class="check_off"></div></td>
						<td><div id="A01_listYn" class="check_off"></div></td>
						<td><div id="A01_dtalYn" class="check_off"></div></td>
						<td><div id="A01_regYn"  class="check_off"></div></td>
						<td><div id="A01_editYn" class="check_off"></div></td>
						<td><div id="A01_delYn"  class="check_off"></div></td>
					</tr>
					<tr id="A02">							
						<td style="text-align: left; padding-left: 5px;">전자문서 처리현황</td>
						<td><div id="A02_accsYn" class="check_off"></div></td>
						<td><div id="A02_listYn" class="check_off"></div></td>
						<td><div id="A02_dtalYn" class="check_off"></div></td>
						<td><div id="A02_regYn"  class="check_off"></div></td>
						<td><div id="A02_editYn" class="check_off"></div></td>
						<td><div id="A02_delYn"  class="check_off"></div></td>
					</tr>
					<tr id="A03">							
						<td style="text-align: left; padding-left: 5px;">전자문서 처리통계</td>
						<td><div id="A03_accsYn" class="check_off"></div></td>
						<td><div id="A03_listYn" class="check_off"></div></td>
						<td><div id="A03_dtalYn" class="check_off"></div></td>
						<td><div id="A03_regYn"  class="check_off"></div></td>
						<td><div id="A03_editYn" class="check_off"></div></td>
						<td><div id="A03_delYn"  class="check_off"></div></td>
					</tr>
					<tr id="A04">							
						<td style="text-align: left; padding-left: 5px;">미처리 현황</td>
						<td><div id="A04_accsYn" class="check_off"></div></td>
						<td><div id="A04_listYn" class="check_off"></div></td>
						<td><div id="A04_dtalYn" class="check_off"></div></td>
						<td><div id="A04_regYn"  class="check_off"></div></td>
						<td><div id="A04_editYn" class="check_off"></div></td>
						<td><div id="A04_delYn"  class="check_off"></div></td>
					</tr>
			<!-- 		<tr id="B01">							
						<td style="text-align: left; padding-left: 5px;">시스템 관리</td>
						<td></td>
						<td><div id="B01_accsYn" class="check_off"></div></td>
						<td><div id="B01_listYn" class="check_off"></div></td>
						<td><div id="B01_dtalYn" class="check_off"></div></td>
						<td><div id="B01_regYn"  class="check_off"></div></td>
						<td><div id="B01_editYn" class="check_off"></div></td>
						<td><div id="B01_delYn"  class="check_off"></div></td>
					</tr> -->
					<tr id="C01">							
						<td rowspan="3" style="text-align: left; padding-left: 5px;">기본 정보 관리</td>
						<td style="text-align: left; padding-left: 5px;">코드 관리</td>
						<td><div id="C01_accsYn" class="check_off"></div></td>
						<td><div id="C01_listYn" class="check_off"></div></td>
						<td><div id="C01_dtalYn" class="check_off"></div></td>
						<td><div id="C01_regYn"  class="check_off"></div></td>
						<td><div id="C01_editYn" class="check_off"></div></td>
						<td><div id="C01_delYn"  class="check_off"></div></td>
					</tr>
					<tr id="C02">							
						<td style="text-align: left; padding-left: 5px;">사용자 관리</td>
						<td><div id="C02_accsYn" class="check_off"></div></td>
						<td><div id="C02_listYn" class="check_off"></div></td>
						<td><div id="C02_dtalYn" class="check_off"></div></td>
						<td><div id="C02_regYn"  class="check_off"></div></td>
						<td><div id="C02_editYn"  class="check_off"></div></td>
						<td><div id="C02_delYn"  class="check_off"></div></td>
					</tr>
					<tr id="C03">							
						<td style="text-align: left; padding-left: 5px;">권한 관리</td>
						<td><div id="C03_accsYn" class="check_off"></div></td>
						<td><div id="C03_listYn" class="check_off"></div></td>
						<td><div id="C03_dtalYn" class="check_off"></div></td>
						<td><div id="C03_regYn"  class="check_off"></div></td>
						<td><div id="C03_editYn" class="check_off"></div></td>
						<td><div id="C03_delYn"  class="check_off"></div></td>
					</tr>
					<tr id="D01">							
						<td style="text-align: left; padding-left: 5px;">이미지조회</td>
                        <td></td>
						<td><div id="D01_accsYn" class="check_off"></div></td>
						<td><div id="D01_listYn" class="check_off"></div></td>
						<td><div id="D01_dtalYn" class="check_off"></div></td>
						<td><div id="D01_regYn"  class="check_off"></div></td>
						<td><div id="D01_editYn" class="check_off"></div></td>
						<td><div id="D01_delYn"  class="check_off"></div></td>
					</tr>
				</tbody>
			</table>
		</div>				
	
		<!-- 권한 편집 팝업 -->
		<div id="divPermEditPopup" class="popup-wrapper" style="width: 1024px; display: none;"></div>	
		
		<%@ include file="/WEB-INF/jsp/include/frame/bottom.jsp"%>
		
	</div>	
		
	<%@ include file="/WEB-INF/jsp/include/frame/footer.jsp"%>

</body>
</html>