<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
 	<link href="<c:url value="/resources/bootstrap/4.3.1-dist/css/bootstrap.min.css"/>" rel="stylesheet">  
 	<link href="<c:url value="/resources/css/testPage.css"/>" rel="stylesheet">     

	<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-3.2.1.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-ui.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.fileupload.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.form.min.js" />"></script>
	
	<script type="text/javascript" src="<c:url value="/resources/js/common/com.ajax.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/common/common.js" />"></script>

	<title>이미지 서버 테스트</title>
	<script>
		$(document).ready(function(){
			var ipAddress = '<%=request.getRemoteAddr()%>';
			$('#previousDeviceInfo').val(ipAddress);

			$('#getMainKeyBtn').click(function(){
 				ajaxCall("GET", "generate/mainkey", null, function(response){
					if(response.resultCode == 200){
						$('#mainKey').val(response.mainKey);
					} else {
						var message =  "[ 메인키 생성 실패 ]";
						message += "\n" + "errorCode : " + response.resultCode;
						message += "\n" + "errorMessage : " + response.resultMessage;
						alert(message);
					}
				});
			})
			
			$('#uploadBtn').click(function(){
				var totalData = new FormData();
				
				var imageFileFormList = [];
				var rowLength = document.getElementById('dynamicBody').rows.length;
				for(var i=0; i<rowLength; i++){
					
					var file = $('input[name="files"]')[i].files[0];
					totalData.append(file.name, file);
					
					var imageFileForm = {
						'pageCnt' : $("input[name='pageCnt']:eq("+i+")").val(),
						'docId' : $("input[name='docId']:eq("+i+")").val(),
						'docTitle' : $("input[name='docTitle']:eq("+i+")").val(),
						'docType' : $("select[name='docType'] option:selected").eq(i).attr("value"),
						'funnels' : $("select[name='funnels'] option:selected").eq(i).attr("value"),
						'fileName' : file.name
					};
					
					imageFileFormList.push(imageFileForm);
				}
				
				var metaInfo = {
					'imageStoreDto' : $('#imageStoreForm').serializeObject(),
					'imageFileDtos' : imageFileFormList
				};
				
				totalData.append("scanInfo", JSON.stringify(metaInfo));
				
				ajaxCall("POST", "upload", totalData, function(response){
					if(response.resultCode == 200){
						alert("업로드 성공");
					} else {
						var message =  "[ 업로드 실패 ]";
						message += "\n" + "errorCode : " + response.resultCode;
						message += "\n" + "errorMessage : " + response.resultMessage;
						alert(message);
					}
				});
			})
		});
	</script>
</head>
<body>

<div class="container header">
	<h1>ImageServer Test</h1>
</div>	
	
<div class="container">	
	<form id="imageStoreForm">
		<div class="table-top">
			<h5 style="display:inline-block;">업무 정보</h5>
			<button type="button" class="btn btn-info resetBtn" onclick="reset($('#imageStoreForm'))">RESET</button>
		</div>
		<div class="table-responsive">
			<table class="table table-condensed">
				<thead>
					<tr>
						<th class="title">통합키<br />(메인키)</th>
						<td colspan="5">
							<div class="input-group">
								<input type="text" class="form-control" id="mainKey" name="mainKey" />
								<div class="input-group-btn">
									<button type="button" class="btn btn-info btn-s" id="getMainKeyBtn">키 생성</button>
								</div>
							</div>
						</td>
					</tr>				
				</thead>
				<tbody>
					<tr>
						<th class="title">지점 코드</th>
						<td><input type="text" class="form-control" id="branchCode" name="branchCode" value="00001" size="10" /></td>
						<th class="title">담당자 ID</th>
						<td><input type="text" class="form-control" id="employeeId" name="employeeId" value="00001" size="10"  /></td>
						<th class="title">업무 코드</th>
						<td><input type="text" class="form-control" id="insourceId" name="insourceId" value="0001" size="10" /></td>
					</tr>
					<tr>
						<th class="title">지점 명</th>
						<td><input type="text" class="form-control" id="branchTitle" name="branchTitle" value="서울지부" size="10" /></td>
						<th class="title">담당자 명</th>
						<td><input type="text" class="form-control" id="employeeName" name="employeeName" value="이순신" size="10" /></td>
						<th class="title">업무 명</th>
						<td><input type="text" class="form-control" id="insourceTitle" name="insourceTitle" value="신용회복신청" size="10" /></td>
					</tr>
					<tr>
						<th class="title">안내원
						<td><input type="text" class="form-control" id="guideName" name="guideName" value="박안내" size="10" /></td>
						<th class="title">접수 번호</th>
						<td colspan="3"><input type="text" class="form-control" id="taskKey" name="taskKey" value="task001" /></td>
					</tr>
					<tr>
						<th class="title">고객 명</th>
						<td><input type="text" class="form-control" id="customerName" name="customerName" value="강감찬" size="10" /></td>
						<th class="title">주민등록번호</th>
						<td colspan="3"><input type="text" class="form-control" id="memo" name="memo" placeholder=" '-' 제외" /></td>
					</tr>
				</tbody>
			</table>
		</div>
		<input type="hidden" id="previousDeviceInfo" name="previousDeviceInfo" />
	</form>
</div>		
		
<div class="container" style="text-align : center;">
	<button type="button" class="btn btn-default btn-lg btn-lg" id="addFileBtn" onclick="addFileInfo()">+파일 추가</button>
</div>

<div class="container">
	<form id="imageFileForm">
		<div class="table-top">
			<h5 style="display:inline-block;">파일 정보</h5>
			<button type="button" class="btn btn-info resetBtn" onclick="imageFileFormReset()">RESET</button>
		</div>
		<div class="table-responsive">
			<table class="table table-condensed">
				<thead>
					<tr>
						<th class="title">문서 아이디</th>
						<th class="title">문서 명</th>
						<th class="title" style="width : 15%;">문서 타입</th>
						<th class="title" style="width : 15%;">채널 타입</th>
						<th class="title">페이지 수</th>
						<th class="title">파일 선택</th>
						<th class="title" style="width : 2%;"> </th>
					</tr>
				</thead>
				<tbody id="dynamicBody">
					
						<tr>
							<td><input type="text" class="form-control" name="docId" value="DOC-001" size="10" /></td>
							<td><input type="text" class="form-control" name="docTitle" value="테스트문서1" size="15" /></td>
							<td>
								<select class="form-control" name="docType" >
									<option value="PDF">PDF</option>
									<option value="TIF">TIF</option>
									<option value="JPG">JPG</option>
								</select>
							</td>
							<td>
								<select class="form-control" name="funnels">
									<option value="cyber">CYBER</option>
									<option value="scan">SCAN</option>
									<option value="edoc" selected>EDOC</option>
								</select>
							</td>
							<td>
								<input type="text" class="form-control" name="pageCnt" size="2" value="0" />
							</td>
							<td>
								<div class="input-group">
									<input type="text" class="form-control" name="fileName" size="10" readonly/>
									<input type="file" class="invisible files" name="files" onchange="uploadFile(this)" />
									<div class="input-group-btn">
										<button type="button" class="btn btn-info btn-s selectFileBtn" onclick="selectFile(this)">찾아보기</button>
									</div>
								</div>
							</td>
							<td>
								<button type="button" class="btn btn-default btn-xs" onclick="removeFileInfo(this)">x</button>
							</td>
						</tr>
				</tbody>
			</table>
		</div>
	</form>
</div>
<div class="container footer">	
	<button type="button" class="btn btn-info btn-lg" id="uploadBtn">업로드</button>
</div>

</body>
	
<script type="text/javascript">
	var fileAddCnt = 1;
	
	function addFileInfo(){
		
		fileAddCnt += 1;
		
		var docId = "DOC-" + pad(fileAddCnt, 3);
		var docTitle = "테스트문서" + fileAddCnt;
		
 		var str = "<tr>";
 		str += "<td><input type='text' class='form-control' name='docId' size='10' value='" + docId + "'/></td>";
 		str += "<td><input type='text' class='form-control' name='docTitle' size='15' value='" + docTitle + "'/></td>";
		str += "<td><select class='form-control' name='docType'><option value='PDF'>PDF</option><option value='TIF'>TIF</option><option value='JPG'>JPG</option></select></td>";
		str += "<td><select class='form-control' name='funnels'><option value='cyber'>CYBER</option><option value='scan'>SCAN</option><option value='edoc'>EDOC</option></select></td>";
		str += "<td><input type='text' class='form-control' name='pageCnt' size='2' value='0' /></td>";
		str += "<td><div class='input-group'><input type='text' class='form-control' name='fileName' size='10' readonly/>";
		str += "<input type='file' class='invisible files' name='files' onchange='uploadFile(this)'/>";
		str += "<div class='input-group-btn'><button type='button' class='btn btn-info btn-s selectFileBtn' onclick='selectFile(this)'>찾아보기</button></div></div></td>";
		str += "<td><button type='button' class='btn btn-default btn-xs' onclick='removeFileInfo(this)'>x</button></td>";
		str += "</tr>";
		
		$("#dynamicBody").append(str);
	}
	
	function selectFile(btnObj){
		$(btnObj).parent().siblings(".files").click();
	}
	
	function uploadFile(fileObj){
		var fileName = $(fileObj)[0].files[0].name;
		$(fileObj).prev().val(fileName);
	}
	
	function removeFileInfo(btnObj){
		var obj = btnObj;
		while(obj.tagName.toUpperCase() != "TR"){
			obj = obj.parentNode;
		}
		
		var trId = obj.id;
		
		console.log($(obj));
		$(obj).remove();
	}
	
	function reset(form){
		$(form)[0].reset();
	}
	
	function imageFileFormReset(){
		fileAddCnt = 0;
		$("#dynamicBody").empty();
		
		addFileInfo();
	}
</script>

</html>




