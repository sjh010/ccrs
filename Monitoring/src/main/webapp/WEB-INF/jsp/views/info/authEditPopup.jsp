<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/frame/taglib.jsp"%>
<c:set var="_context" value="${(pageContext.request.contextPath eq '/')? '': pageContext.request.contextPath}" />

<script type="text/javascript">			
$(document).ready(function() {					
	$("#divPermEditPopup .btnPermEditPopupClose").click(function(e) {
		location.replace("${_context}/info/authAdmin?permId="+$("#selectedPermId").val()); 
		jsHideBlockLayer("#divPermEditPopup");
	});
	
	$("#divPermAddPopup .btnPermAddPopupClose").click(function(e) {
		jsHideBlockLayer("#divPermAddPopup");
	});
	
	$("#permEditList tr").click(function(e) {
 		jsSelectPerm($(this).find("td").attr("permId"));
	});
	
	<sec:authorize access="!hasRole('ROLE_C03_EDIT')">
		$("#editMenuPermTable input").prop("disabled", true);
	</sec:authorize>

	jsSelectPerm($("#permId").val());
	
	
	//삭제 버튼 클릭시 이벤트 처리
	$("#btnDelCode").click(function() {
		var selected = $("#permEditList .selected");
		if(selected) {
			if (confirm("선택한 항목을 삭제하시겠습니까?")) {
				jsDeletePerm();
			}
			//callConfirm("선택한 항목을 삭제하시겠습니까?", jsDeletePerm);
		}else{
			alert("삭제할 항목을 선택하십시오");
		}
	});
	
	// 권한 추가 팝업 input event
	$("#divPermAddPopup .inputAddPerm").keypress(function(e) {
		var keyCode = e.which?e.which:e.keyCode;
		if (keyCode == 13) {
			e.preventDefault();
			jsAddPerm();
		}
	});
	
});		
	
function jsSelectPerm(permId) {
	$("#permEditList td").removeClass("selected");
	$("#selectedPermId").val(permId);
	$("#selectedPermId_menu").val(permId);		
	$("#permEditList tr td[permId='" + permId + "']").addClass("selected");
	jsEditMenuPermData();
}

function jsEditMenuPermData() {
	$("#editMenuPermTable tr td input[type='checkbox']").prop("checked", false);
	
	jsTrimInputValue("editPermForm");
	jsAjaxPost("${_context}/info/authAdmin/list", $("#editPermForm").serialize(), function(response) {
		if (response.result == "success") {
			if (response.list && response.list != null && response.list.length > 0) {
				
				for (var idx in response.list) {
					var item = response.list[idx];
					var menuCd = item.menuCd;
					
					if(item.accsYn == "Y") {
						$("input#" + menuCd + "_accsYn").prop("checked", true);
					}
					if(item.listYn == "Y") {
						$("input#" + menuCd + "_listYn").prop("checked", true);
					}
					if(item.dtalYn == "Y") {
						$("input#" + menuCd + "_dtalYn").prop("checked", true);
					}
					if(item.regYn == "Y") {
						$("input#" + menuCd + "_regYn").prop("checked", true);
					}
					if(item.editYn == "Y") {
						$("input#" + menuCd + "_editYn").prop("checked", true);
					}
					if(item.delYn == "Y") {
						$("input#" + menuCd + "_delYn").prop("checked", true);
					}												
				}
			}
		} else {
			// 요청 실패
		}
	});
};

function jsShowAddPopup() {
	jsShowBlockLayer("#divPermAddPopup");
}

function jsAddPerm() {
	var permNm = $("#newPermNm").val().trim();		
	
	/* if(jsCheckPermNmDuplicate(permNm)) {
		alert("중복된 권한명 입니다.");
		return;
	} */
	
	jsTrimInputValue("addPermForm");
	jsAjaxPost("${_context}/info/authAdmin/add", $("#addPermForm").serialize(), function(response) {
		if (response.result == "success") {
			jsHideBlockLayer("#divPermAddPopup");
			
			var permId = response.permId;
			var permNm = response.permNm;
			
			var htmlStr = '';				
			htmlStr += '<tr permId="' + permId + '">';	
			htmlStr += '	<td style="width: 240px; text-align: left;" permid="' + permId + '">' + permNm + '</td>';
			htmlStr += '</tr>';
							
			$("#permEditList").append(htmlStr);
			
			$("#permEditList tr[permId='"+permId+"']").click(function(e) {
		 		jsSelectPerm($(this).find("td").attr("permId"));
			});
			
			jsSelectPerm(permId);
			
		} else if(response.result == "duplicate") {
			//alert("중복된 권한명 입니다.");
			$("#existId").show();	
			$("#existId2").show();			
		} else {
			alert("권한 추가를 실패하였습니다.");
		}
	});
}

function jsShowRenameInput() {
	var $selected = $("#permEditList .selected");
	
	if($selected.length > 0) {
		if ($(".renameInput").length > 0) {
			$(".renameInput").parent().find("td").not(".renameInput").show();
			$(".renameInput").remove();
		}
		
		$selected.css("display", "none");  
		
		var htmlStr = 	'<td class="renameInput">';
		htmlStr += 			'<input type="text" id="renameNm" name="permNm" value="' + $selected.text() + '"/>';
		htmlStr += 		'</td>';
		
		$selected.parent().append(htmlStr);
		$("#renameNm").focus();
		
		$("#renameNm").change(function() {
			jsEditPermName();
		});
		$("#renameNm").blur(function() {
			$(".renameInput").parent().find("td").not(".renameInput").show();
			$(".renameInput").remove();
		});
		$("#renameNm").keypress(function(e) {
			var keyCode = e.which?e.which:e.keyCode;
			if (keyCode == 13) {
				e.preventDefault();
			}
		});
	} else {
		alert("선택된 권한이 없습니다.");
	}
}

function jsEditPermName() {
	var permNm = $("#renameNm").val();
		
	/* if(jsCheckPermNmDuplicate(permNm)) {
		alert("'"+permNm+"'가 이미 있습니다.");
		return;
	} */
	
	jsTrimInputValue("editPermForm");
	jsAjaxPost("${_context}/info/authAdmin/rename", $("#editPermForm").serialize(), function(response) {
		if (response.result == "success") {				
			$(".renameInput").remove();
			$("#permEditList .selected").css("display", "block");
			$("#permEditList .selected").text(permNm);
			
		} else if(response.result == "duplicate") {
			alert("'"+permNm+"'가 이미 있습니다.");
			$("#renameNm").focus();
		} else {
			alert("권한 변경을 실패하였습니다.");
			$("#renameNm").focus();
		}
	});
}

function jsCheckPermNmDuplicate(permNm) {
	var isDuplicate = false;
	$("#permEditList tr td").each(function(index, item){
		if($(item).text().trim == permNm) {
			isDuplicate = true;
		}
	});
		
	return isDuplicate;
}

function jsDeletePerm() {
	var $selected = $("#permEditList .selected");
	
	if($selected.length > 0) {
		jsTrimInputValue("editPermForm");
		jsAjaxPost("${_context}/info/authAdmin/delete", $("#editPermForm").serialize(), function(response) {
			if (response.result == "success") {	
				$selected.remove();
				jsSelectPerm(1);
			} else {
				alert("삭제할 수 없는 권한입니다.");
			}
		});
	} else {
		alert("선택된 권한이 없습니다.");
	}
}

function jsSaveMenuAccsPerm() {
	jsTrimInputValue("editMenuAccsForm");
	jsAjaxPost("${_context}/info/authAdmin/update", $("#editMenuAccsForm").serialize(), function(response) {
		if (response.result == "success") {				
			var permId = $("#selectedPermId").val();
			location.replace("${_context}/info/authAdmin?permId=" + permId ); 
		} else {
			alert("권한 수정을 실패하였습니다.");
		}
	});
}	
</script>


	<div class="popup_top">
		<h2>권한 설정 수정</h2>	
	</div>
	<a href="#" class="btn_popup_close btnPermEditPopupClose"></a>	
	<div class="popup_search">
		<div style="clear:both; width:1024px">
			<div style="float:left; width:240px">
				<div class="refinebutton" style="float:left; clear:both; width:240px; height: 44px;">
					<sec:authorize access="hasRole('ROLE_C03_REG')">
						<span class="btn4_popup" style="width: 74px; margin-bottom: 3px;" onclick="jsShowAddPopup()">추가</span>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_C03_EDIT')">
						<span class="btn4_popup" style="width: 74px; margin-bottom: 3px;" onclick="jsShowRenameInput()">수정</span>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_C03_DEL')">
						<span id="btnDelCode" class="btn4_popup" style="width: 74px; margin-bottom: 3px;">삭제</span>
					</sec:authorize>
				</div>
				<form:form id="editPermForm">
				<input type="hidden" id="selectedPermId" name="permId" />
				<table class="output" style="width: 100%">
					<thead>
						<tr><th>권한</th><tr>			
					</thead>					
					<tbody id="permEditList" class="scroll_tbody" style="height:450px;">		
						<c:forEach items="${permList}" var="perm">
							<tr>
								<td style="width: 240px; text-align: left;" permId="${perm.permId}">${perm.permNm}</td>
							</tr>
						</c:forEach>
					</tbody>					
				</table>
				</form:form>
			</div>
			<div style="float:left">
				<h2 style="margin-left:24px; margin-bottom: 4px;">시스템 관리자</h2>
				<form:form id="editMenuAccsForm">
					<input type="hidden" id="selectedPermId_menu" name="permId" />
				
				<table id="editMenuPermTable" class="output" style="clear:both;margin-left:24px;width:755px;">
					<thead>
						<tr>
							<th colspan="2" width="46%">메뉴/화면 이름</th>
							<th rowspan="2" width="9%">메뉴사용</th>
							<th rowspan="2" width="9%">목록조회</th>
							<th rowspan="2" width="9%">상세조회</th>
							<th rowspan="2" width="9%">등록</th>
							<th rowspan="2" width="9%">수정</th>
							<th rowspan="2" width="9%">삭제</th>
						</tr>
						<tr>
							<th>1depth</th>
							<th style="border-right: 1px solid #a3c2f3">2depth</th>
						</tr>		
					</thead>					
					<tbody style="height:44px">		
						<!-- tr1 -->	
                        <tr>                            
                            <td style="text-align: left; padding-left: 5px;">대시보드</td>
                            <td style="text-align: left; padding-left: 5px;"></td>
                            <td><input id="A00_accsYn" type="checkbox" name="A00_accsYn" class="table_checkbox2  poptable_box"></td>
                            <td><input id="A00_listYn" type="checkbox" name="A00_listYn" class="table_checkbox2  poptable_box"></td>
                            <td><input id="A00_dtalYn" type="checkbox" name="A00_dtalYn" class="table_checkbox2  poptable_box"></td>
                            <td><input id="A00_regYn" type="checkbox" name="A00_regYn" class="table_checkbox2  poptable_box"></td>
                            <td><input id="A00_editYn" type="checkbox" name="A00_editYn" class="table_checkbox2  poptable_box"></td>
                            <td><input id="A00_delYn" type="checkbox" name="A00_delYn" class="table_checkbox2  poptable_box"></td>
                        </tr>
						<tr>							
							<td rowspan="4" style="text-align: left; padding-left: 5px;">전자문서 처리 관리</td>
							<td style="text-align: left; padding-left: 5px;">전자문서 처리결과 모니터링</td>
							<td><input id="A01_accsYn" type="checkbox" name="A01_accsYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A01_listYn" type="checkbox" name="A01_listYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A01_dtalYn" type="checkbox" name="A01_dtalYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A01_regYn" type="checkbox" name="A01_regYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A01_editYn" type="checkbox" name="A01_editYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A01_delYn" type="checkbox" name="A01_delYn" class="table_checkbox2  poptable_box"></td>
						</tr>
						<tr>							
							<td style="text-align: left; padding-left: 5px;">전자문서 처리현황</td>
							<td><input id="A02_accsYn" type="checkbox" name="A02_accsYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A02_listYn" type="checkbox" name="A02_listYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A02_dtalYn" type="checkbox" name="A02_dtalYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A02_regYn" type="checkbox" name="A02_regYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A02_editYn" type="checkbox" name="A02_editYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A02_delYn" type="checkbox" name="A02_delYn" class="table_checkbox2  poptable_box"></td>
						</tr>
						<tr>							
							<td style="text-align: left; padding-left: 5px;">전자문서 처리통계</td>
							<td><input id="A03_accsYn" type="checkbox" name="A03_accsYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A03_listYn" type="checkbox" name="A03_listYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A03_dtalYn" type="checkbox" name="A03_dtalYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A03_regYn" type="checkbox" name="A03_regYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A03_editYn" type="checkbox" name="A03_editYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A03_delYn" type="checkbox" name="A03_delYn" class="table_checkbox2  poptable_box"></td>
						</tr>
						<tr>							
							<td style="text-align: left; padding-left: 5px;">미처리 현황</td>
							<td><input id="A04_accsYn" type="checkbox" name="A04_accsYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A04_listYn" type="checkbox" name="A04_listYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A04_dtalYn" type="checkbox" name="A04_dtalYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A04_regYn" type="checkbox" name="A04_regYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A04_editYn" type="checkbox" name="A04_editYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="A04_delYn" type="checkbox" name="A04_delYn" class="table_checkbox2  poptable_box"></td>
						</tr>
<!-- 						<tr>							
							<td style="text-align: left; padding-left: 5px;">시스템 관리</td>
							<td></td>
							<td><input id="B01_accsYn" type="checkbox" name="B01_accsYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="B01_listYn" type="checkbox" name="B01_listYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="B01_dtalYn" type="checkbox" name="B01_dtalYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="B01_regYn" type="checkbox" name="B01_regYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="B01_editYn" type="checkbox" name="B01_editYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="B01_delYn" type="checkbox" name="B01_delYn" class="table_checkbox2  poptable_box"></td>
						</tr> -->
						<tr>							
							<td rowspan="3" style="text-align: left; padding-left: 5px;">기본 정보 관리</td>
							<td style="text-align: left; padding-left: 5px;">코드 관리</td>
							<td><input id="C01_accsYn" type="checkbox" name="C01_accsYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="C01_listYn" type="checkbox" name="C01_listYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="C01_dtalYn" type="checkbox" name="C01_dtalYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="C01_regYn" type="checkbox" name="C01_regYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="C01_editYn" type="checkbox" name="C01_editYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="C01_delYn" type="checkbox" name="C01_delYn" class="table_checkbox2  poptable_box"></td>
						</tr>
						<tr>							
							<td style="text-align: left; padding-left: 5px;">사용자 관리</td>
							<td><input id="C02_accsYn" type="checkbox" name="C02_accsYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="C02_listYn" type="checkbox" name="C02_listYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="C02_dtalYn" type="checkbox" name="C02_dtalYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="C02_regYn" type="checkbox" name="C02_regYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="C02_editYn" type="checkbox" name="C02_editYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="C02_delYn" type="checkbox" name="C02_delYn" class="table_checkbox2  poptable_box"></td>
						</tr>
						<tr>							
							<td style="text-align: left; padding-left: 5px;">권한 관리</td>
							<td><input id="C03_accsYn" type="checkbox" name="C03_accsYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="C03_listYn" type="checkbox" name="C03_listYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="C03_dtalYn" type="checkbox" name="C03_dtalYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="C03_regYn" type="checkbox" name="C03_regYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="C03_editYn" type="checkbox" name="C03_editYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="C03_delYn" type="checkbox" name="C03_delYn" class="table_checkbox2  poptable_box"></td>
						</tr>
						<tr>	
							<td style="text-align: left; padding-left: 5px;">이미지 조회</td>
							<td style="text-align: left; padding-left: 5px;"></td>
							<td><input id="D01_accsYn" type="checkbox" name="D01_accsYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="D01_listYn" type="checkbox" name="D01_listYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="D01_dtalYn" type="checkbox" name="D01_dtalYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="D01_regYn" type="checkbox" name="D01_regYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="D01_editYn" type="checkbox" name="D01_editYn" class="table_checkbox2  poptable_box"></td>
							<td><input id="D01_delYn" type="checkbox" name="D01_delYn" class="table_checkbox2  poptable_box"></td>
						</tr>
					</tbody>					
				</table>
				</form:form>
			</div>
		</div>
	</div>
	<div class="popupbutton" style="float:right; padding-top:10px">
		<sec:authorize access="hasRole('ROLE_C03_EDIT')">
			<span class="download" style="width:90px !important" onclick="jsSaveMenuAccsPerm()">저장</span>
		</sec:authorize>
		<span class="close btnPermEditPopupClose">취소</span>
	</div>
	
	<!-- 권한 추가 팝업 -->
	<div id="divPermAddPopup" class="popup-wrapper" style="width: 380px; display: none;">
		<div class="popup_top">
			<h2>권한 추가</h2>	
		</div>
		<a href="javascript:;" class="btn_add_close btnPermAddPopupClose"></a>
		<form:form id="addPermForm" name="addPermForm">
		<div class="table1">
			<table class="data" style="width:260px">
				<tr class="tr1">					
		   <!-- <td rowspan="2" class="title">권한명</td> -->
					<td class="title">권한명</td>
					<td class="input" style="border-right:none; float:left; width:238px"><input type="text" id="newPermNm" name="permNm" style="width:220px;"></td>					
<!--			</tr>
 				<tr>
					<td style="border-right:none; float:left; height:28px">&nbsp
						<span id="existId" style="border-right:none; float:left; width:238px; color:red; display: none">이(가) 이미 있습니다.</span>
					</td>
				</tr> -->
			</table>
		</div>
		<div style="display: none" id="existId2">		
			<img alt="O" src="${_context}/images/bull_success.png">	<!-- TODO: 이미지 변경해야함 -->	
			<span style="color:blue"><strong>이미 등록된 권한명은 사용할 수 없습니다.</strong></span>
		</div>
		<div class="popupbutton" style="float:right; margin-top:10px">		
			<span class="download" style="width:90px !important" onclick="jsAddPerm()">저장</span>			
			<span id="btnAddClose" class="close btnPermAddPopupClose">취소</span>
		</div>
		</form:form>
	</div>
