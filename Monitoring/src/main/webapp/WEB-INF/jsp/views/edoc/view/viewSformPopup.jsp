<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/frame/header.jsp"%>
<link rel="stylesheet" href="<c:url value="/webform/css/sform/sform.viewer.1.1.3.min.css?v=201910160000" />" type="text/css" />
<style>
.active {
	background-color: lightgray;
}
</style>
<script type="text/javascript" src="<c:url value="/webform/js/sform/sform.viewer.1.1.3.min.js?v=201910160000" />"></script>
<script type="text/javascript">
$(document).ready(function() {

	// '전체보기' 버튼 이벤트
	// 모든 버전의 이미지 + 삭제된 이미지 조회
	$("#btn_viewAll").click(function() {
		var mainKey = "${imageStore.mainKey}";

		// cleanXss
		mainKey = cleanXss(mainKey);

		jsAjaxPost("${_context}/edoc/view/sform/all", { mainKey : mainKey }, function(response) {
			crateImageListTable(response);
		})
	});
	
	$("#btn_print").click(function() {
		var fileId = $(this).data("fileid");
		sFormController.print(fileId);
	});

	// '닫기' 버튼 이벤트
	$(".btnPopupClose").click(function(e) {
		self.close();
	});

	// PDF 파일 다운로드
	$("#btn_pdfView").click(function(e) {
		callAdobe(fileId);
	});

	
	initSformViewer();
});

var sFormController;
var viewerController;

// webform viewer init
function initSformViewer() {
	$("#ulFileList li:first").addClass("active");
	var fileId = $("#ulFileList li:first").data("fileid");
	var config = {
		context : "<c:out value="${_context}" />",
		container : "#sform-wrapper",
		item : fileId,
		toolbarLayoutType : "simple",
		serviceStreamingType : "url",
		viewerConfig : {
			globalTransform : false,
			renderType : "clip",
			renderNodeType : "image",
			zoom : "fitauto",
			visible : true,
			draggable : true,
			annotationEnable : false,
			layerOptions : {
				toolbarControl : true,
				pageControl : true,
				thumbnailControl : {
					enable : true
				}
			}
		},
		printConfig : {
			commonOption : {
				margins : 1 // 여백 설정
			}
		}
	};

	sFormController = InziSForm.viewerLoad(config);
}

// '문서목록' 클릭 이벤트
function jsSelectDocument(obj) {

	sFormController.clear().addItem($(obj).data("fileid")).render();	

	$("#ulFileList li").each(function(index, value) {
		
		if ($(obj).data("seq") == $(value).data("seq")) {
			$(this).addClass("active");
		} else {
			$(this).removeClass("active");
		}	
	});

	createFileInfoTable($(obj).data());
}


// '문서목록' 생성
function crateImageListTable(data) {

	var imageListTable = $("#ulFileList");

	imageListTable.empty();

	var html = "";

	$.each(data, function(index, item) {
		var createTime = item.createTime.split('.')[0];
		
		console.log(createTime.split('.')[0]);
		
		html += "<li data-doctitle='" + item.docTitle + "' data-fileid='" + item.fileId + "' data-pagecnt='" + item.pageCnt;
		html += "' data-funnels='" + item.funnels + "' data-filename='" + item.fileName + "' data-doctype='" + item.docType;
		html += "' data-versioninfo='" + item.versionInfo + "' data-deleteyn='" + item.deleteYn + "' data-seq='" + index;
		html += "' data-createtime='" + createTime;
		html += "' class='popup_scan_doc_tables' onclick='jsSelectDocument(this)'>";

		if (item.docTitle) {
			if (item.deleteYn == "Y") {
				html += item.docTitle + " (삭제)" + "</li>";
			} else {
				html += item.docTitle + "</li>";	
			}
		} else {
			if (item.deleteYn == "N") {
				html += item.docId + " (삭제)" + "</li>";	
			} else {
				html += item.docId + "</li>";	
			}		
		}
	});

	imageListTable.append(html);

	jsSelectDocument($("#ulFileList li").eq(0));
}

// '파일 정보' 생성
function createFileInfoTable(data) {
	$("#docTitle").text(data.doctitle);
	$("#pageCnt").text(data.pagecnt);
	$("#docType").text(data.doctype);
	$("#versionInfo").text(data.versioninfo);
	$("#deleteYn").text(data.deleteyn);
	$("#fileCreateTime").text(data.createtime);
	$("#fileId").text(data.fileId);
	
	
	var funnels = data.funnels.toUpperCase();
	
	if (funnels == "SCAN") {
		$("#funnels").text("스캔");
	} else if (funnels == "EDOC") {
		$("#funnels").text("페이퍼리스");
	} else if (funnels == "CYBER") {
		$("#funnels").text("사이버");
	}

	// 인쇄버튼 fileId 변경
	$("#btn_print").data("fileid", data.fileid);
	
	
	// 문서 종류가 PDF인 경우에만 '다운로드' 버튼 보이기
	/* if (data.doctype == 'PDF') {
		// 선택한 문서 fileid 설정
		$("#btn_pdfView").data("fileid", data.fileid);
		div.show();
	} else {
		div.hide();
	} */
}

function callAdobe(fileId) {
	var options = "width=" + screen.width + ",height=" + screen.height + ",resizable=yes"; 
	var self = window.open("${_context}/edoc/view/pdfview/" + fileId, "", options);
	self.moveTo(0,0);
	self.resizeTo(screen.availWidth, screen.availHeight);
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
		value = replaceAll(value,
				"[\\\\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = replaceAll(value, "script", "");
	}

	return value;
}
</script>
</head>
<body style="width: 100%; height: 100%; background-color: #ffffff; min-width: 680px; overflow-x: auto;">
	<div class="popup-wrapper_viewer">
		<div class="popup_top">
			<%-- <h2><c:out value="${imageStore.mainKey }" /></h2><h2>&nbsp;|&nbsp;</h2><h2>상세조회</h2> --%>
		</div>

		<!-- sform 뷰어 start -->
		<div id="sform-wrapper" class="pop_wrapper__toolbar"></div>
		<!-- sform 뷰어 end -->

		<div class="doculist">
			<h2>문서목록</h2>
			<ul id="ulFileList">
				<c:forEach var="file" items="${files }" varStatus="status">
					<li data-doctitle="<c:out value="${file.docTitle }" />"
						data-fileid="<c:out value="${file.fileId }" />"
						data-pagecnt="<c:out value="${file.pageCnt }" />"
						data-funnels="<c:out value="${file.funnels }" />"
						data-filename="<c:out value="${file.fileName }" />"
						data-doctype="<c:out value="${file.docType }" />"
						data-versioninfo="<c:out value="${file.versionInfo }" />" 
						data-deleteyn="<c:out value="${file.deleteYn }" />"
						<fmt:parseDate value="${file.createTime }" var="createTime" pattern="yyyy-MM-dd HH:mm:ss" />
						data-createtime="<fmt:formatDate value="${createTime }" pattern="yyyy-MM-dd HH:mm:ss" />" 
						data-seq="<c:out value="${status.index }" />" 
						class="popup_scan_doc_tables" onclick="jsSelectDocument(this)">
						<c:choose>
							<c:when test="${empty file.docTitle}">
								<c:out value="${file.docId }" />
							</c:when>
							<c:otherwise>
								<c:out value="${file.docTitle }" />
							</c:otherwise>
						</c:choose>
					</li>
				</c:forEach>
			</ul>
			<div class="blank"></div>

			<div style="width: 100%;">
				<a href="javascript:;" class="popupbutton" style="padding-top: 5px;">
					<span id="btn_viewAll" class="close" style="width: 100px;">전체조회</span>
				</a>
			</div>

			<h2>기본정보</h2>
			<table class="data">
				<tr class="tr1">
					<td class="pop_title pop_table1">통합전자문서키</td>
					<td class="pop_input pop_table1">
						<p><c:out value="${imageStore.mainKey }" /></p>
					</td>
				</tr>
				<tr class="tr1">
					<td class="pop_title">지부코드</td>
					<td class="pop_input">
						<p>
							<c:out value="${imageStore.branchCode }" />
						</p>
					</td>
				</tr>
				<tr class="tr1">
					<td class="pop_title">지부명</td>
					<td class="pop_input">
						<p>
							<c:choose>
								<c:when test="${imageStore.branchTitle ne null }">
									<c:out value="${imageStore.branchTitle }" />
								</c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose>
						</p>
					</td>
				</tr>
				<tr class="tr1">
					<td class="pop_title">담당자명</td>
					<td class="pop_input">
						<p><c:out value="${imageStore.employeeName }" /></p>
					</td>
				</tr>
				<tr class="tr1">
					<td class="pop_title">고객명</td>
					<td class="pop_input">
						<p><c:out value="${imageStore.customerName }" /></p>
					</td>
				</tr>

				<tr class="tr1">
					<td class="pop_title">거래일시</td>
					<td class="pop_input"><fmt:parseDate value="${imageStore.createTime }" var="createTime" pattern="yyyy-MM-dd HH:mm:ss" />
						<p><fmt:formatDate value="${createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></p>
					</td>
				</tr>
			</table>

			<div id="divDownload" style="width: 100%;">
				<%-- <c:choose>
					<c:when test="${fn:toUpperCase(files[0].docType) == 'PDF' }">
						<a href="javascript:;" class="popupbutton" style="padding-top: 5px;">
							<span id="btn_pdfView" data-doctitle="${files[0].docTitle }" data-fileid="${files[0].fileId }" 
								class="close" style="width: 100px;">PDF보기</span>
						</a>
					</c:when>
				</c:choose> --%>
				
				<a href="javascript:;" class="popupbutton" style="padding-top: 5px;">
					<span id="btn_print" data-fileid="${files[0].fileId }" class="close" style="width: 50px;">인쇄</span>
				</a>
			</div>

			<h2>파일 정보</h2>
			<table class="data">
				<tr class="tr1">
					<td class="pop_title pop_table1">문서명</td>
					<td class="pop_input pop_table1">
						<p id="docTitle"><c:out value="${files[0].docTitle }" /></p>
					</td>
				</tr>
				<tr class="tr1">
					<td class="pop_title">페이지수</td>
					<td class="pop_input">
						<p id="pageCnt"><c:out value="${files[0].pageCnt }" /></p>
					</td>
				</tr>
				<tr class="tr1">
					<td class="pop_title">문서종류</td>
					<td class="pop_input">
						<p id="docType"><c:out value="${files[0].docType }" /></p>
					</td>
				</tr>
				<tr class="tr1">
					<td class="pop_title">유입경로</td>
					<td class="pop_input">
						<p id="funnels">
							<c:choose>
								<c:when test="${fn:toUpperCase(files[0].funnels) == 'SCAN' }">
									<c:out value="스캔" />
								</c:when>
								<c:when test="${fn:toUpperCase(files[0].funnels) == 'EDOC' }">
									<c:out value="페이퍼리스" />
								</c:when>
								<c:when test="${fn:toUpperCase(files[0].funnels) == 'CYBER' }">
									<c:out value="사이버" />
								</c:when>
							</c:choose>
						</p>
					</td>
				<tr class="tr1">
					<td class="pop_title">버전정보</td>
					<td class="pop_input">
						<p id="versionInfo"><c:out value="${files[0].versionInfo }" /></p>
					</td>
				</tr>
				<tr class="tr1">
					<td class="pop_title">삭제여부</td>
					<td class="pop_input">
						<p id="deleteYn"><c:out value="${files[0].deleteYn }" /></p>
					</td>
				</tr>
				<tr class="tr1">
					<td class="pop_title">거래일시</td>
					<td class="pop_input"><fmt:parseDate value="${files[0].createTime }" var="createTime" pattern="yyyy-MM-dd HH:mm:ss" />
						<p id="fileCreateTime"><fmt:formatDate value="${createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></p>
					</td>
				</tr>
				<tr class="tr1">
					<td class="pop_title">파일 아이디</td>
					<td class="pop_input">
						<p id="fileId"><c:out value="${files[0].fileId }" /></p>
					</td>
				</tr>
				
			</table>

			<a href="javascript:;" class="popupbutton"> <span
				class="close btnPopupClose">닫기</span>
			</a>
		</div>
	</div>
</body>
</html>
