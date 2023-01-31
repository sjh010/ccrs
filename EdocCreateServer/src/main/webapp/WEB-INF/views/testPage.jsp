<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html>

<head>
	<link href="<c:url value="/resources/bootstrap/4.3.1-dist/css/bootstrap.min.css"/>" rel="stylesheet">  
   	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common/testPage.css" />" />
   	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/common/test.ajax.js" />"></script>
	
	<title>EdocCreateServer Test Page</title>
	
	<script type="text/javascript">
	
		var protocol = location.protocol;
		var host = location.host;
		
 		var imageUrl;
		if(protocol.startsWith("https")){
			imageUrl = "https://pptr.ccrs.or.kr";
		} else {
			imageUrl = "http://pptr-dr.ccrs.or.kr:7104"
		}

		var sampleFileResponse;
		
		$(document).ready(function(){			
			
			getSampleFile("<c:url value="/sample.zip" />", function(response){sampleFileResponse = response;});
			
			function getSampleFile(url, callback){
				var xhr = new XMLHttpRequest();
				xhr.open("GET", url, true);
				xhr.responseType="blob";
				xhr.onload=function(){
					callback(xhr.response);
				}
				xhr.send(null);
			}
			
			$('#getEdocGrpKeyBtn').click(function(){
				var formData = $('#uploadForm').serializeObject();

				ajaxPostCall("edoc/getKey", JSON.stringify(formData), false, function(response){
						if(response.statusCode == 200){
							$('#corEdocGrpIdxNo').val(response.corEdocGrpIdxNo);
						} else{
							alert("전자문서 그룹키 생성 실패!\nerrorCode : " + response.corCode + "\nerrorMessage :" + response.corMsg);
						}
				});
			})
			
			$('#getMainKeyBtn').click(function(){
			
				ajaxGetCall(imageUrl+"/generate/mainkey", null, function(response){
					if(response.resultCode == 200){
						$('#MainKey').val(response.mainKey);
					}else{
						 var errorMsg = "메인키 생성 실패!" 
						 				+ "\n" + "errorCode : " + response.resultCode 
						 				+ "\n" + "errorMessage : " + response.resultMessage;
						alert(errorMsg);
					}
				});
			})
			
			$('#tempSaveBtn').click(function(){
				$('#bizTmpDataStrgType').val("T");
				
				tempSave(getSendData(), function(){alert("임시저장을 완료하였습니다.");});
			})

			$('#uploadEdocBtn').click(function(){
				$('#bizTmpDataStrgType').val("A");
				
				var sendData = getSendData();
				
				tempSave(sendData, function(){
										console.log("tempSave Success");
										uploadEdoc(sendData, function(){alert("전자문서 생성요청을 완료하였습니다.");});
				});
					
				
			})
						
			function getSendData(){
				var totalData = new FormData();
				
				 if($('#file')[0].files[0]){
 					totalData.append("file", $('#file')[0].files[0]);
				} else{
					totalData.append("file", sampleFileResponse, "sample.zip");
				}
				
				var formData = $('#uploadForm').serializeObject();
				totalData.append("data", JSON.stringify(formData));
				
				return totalData;
			}

			function tempSave(sendData, callbackFucn){
				
  				ajaxPostCall("edoc/upload/tempData", sendData, true, function(response){
					if(response.statusCode == 200){
						callbackFucn();
					} else{
						alert("임시저장 실패!\nerrorCode : " + response.corCode + "\nerrorMessage :" + response.corMsg);
					}
				});
			}
			
			function uploadEdoc(sendData, callbackFucn){
				
				ajaxPostCall("edoc/upload", sendData, true, function(response){
					if(response.statusCode == 200){
						callbackFucn();
					} else{
						alert("전자문서 생성 실패!\nerrorCode : " + response.corCode + "\nerrorMessage :" + response.corMsg);
					}
				});
			}
			
			$('#resetBtn').click(function(){
				$('form').each(function(){
					this.reset();
				});
			})
			
			$("#file").change(function(){
				$("#fileName").val($('#file')[0].files[0].name);
			})
		});
	</script>
</head>
<body>

<div class="container title align-center">
	<h1>EdocCreateServer Test</h1>
</div>

<form id="uploadForm">
	<div class="container">
		<div class="table-header">
			<h5 style="display:inline-block;">기본 정보</h5>
			<button type="button" class="btn btn-default btn-right" onclick="resetAll()">RESET</button>
		</div>
		
		<div class="table-content">
			<table class="table">
				<tr>
					<th>취급점코드</th>
					<td><input class="form-control" type="text" id="bizRgstBrCd" name="bizRgstBrCd" value="00001" /></td>
					<th>취급점명</th>
					<td><input class="form-control" type="text" id="bizRgstBrNm" name="bizRgstBrNm" value="서울지부"  /></td>
					<th>화면번호</th>
					<td><input class="form-control" type="text" id="bizScrnNo" name="bizScrnNo" value="0001" /></td>
					<th>단말번호</th>
					<td><input class="form-control" type="text" id="bizTerminalNo" name="bizTerminalNo" value="PC001" /></td>
				</tr>
				<tr>
					<th>취급자코드</th>
					<td><input class="form-control" type="text" id="bizRgstEmpNo" name="bizRgstEmpNo" value="00001" /></td>
					<th>취급자명</th>
					<td><input class="form-control" type="text" id="bizRgstEmpNm" name="bizRgstEmpNm" value="이순신" /></td>
					<th>부화면 번호</th>
					<td><input class="form-control" type="text" id="bizSubScrnNo" name="bizSubScrnNo" value="TEST"/></td>
					<th>프로세스<br/>유형코드</th>
					<td><input class="form-control" type="text" id="bizProcessType" name="bizProcessType" value="PPR" /></td>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="container" style="margin-top : 2rem;">
		<div class="table-header">
			<h5 style="display:inline-block;">업무 정보</h5>
		</div>
		<div class="table-content">
			<table class="table">
				<tr>
					<th>통합키<br/>(메인키)</th>
					<td colspan="5">
						<div class="input-group">
							<input class="form-control" type="text" id="MainKey" name="MainKey" />
							<div class="input-group-btn">
								<button type="button" class="btn btn-info" id="getMainKeyBtn">메인키 생성</button>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<th>전자문서<br/>그룹 키 </th>
					<td colspan="5">
						<div class="input-group">
							<input class="form-control" type="text" id="corEdocGrpIdxNo" name="corEdocGrpIdxNo" />
							<div class="input-group-btn">
								<button type="button" class="btn btn-info" id="getEdocGrpKeyBtn">전자문서그룹키 생성</button>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<th>접수 번호<br/>(업무키)</th>
					<td><input class="form-control" type="text" id="TaskKey" name="TaskKey" value="task001" /></td>
					<th>업무 코드</th>
					<td><input class="form-control" type="text" id="InsourceId" name="InsourceId" value="0001" /></td>
					<th>업무명</th>
					<td><input class="form-control" type="text" id="InsourceTitle" name="InsourceTitle" value="신용회복신청" /></td>
				</tr>
				<tr>
					<th>고객명</th>
					<td><input class="form-control" type="text" id="CustomerName" name="CustomerName" value="강감찬"/></td>
					<th>주민등록번호</th>
					<td colspan="3"><input class="form-control" type="text" id="Memo" name="Memo" placeholder=" '-' 제외" /></td>
				</tr>
				<tr>
					<th>ZIP 파일</th>
					<td colspan="5">
						<div class="input-group">
							<input class="form-control" type="text" id="fileName" value="선택된 파일이 없습니다." readonly style="border:0;"/>
							<div class="input-group-btn">
								<button type="button" class="btn btn-default" onclick="$('#file').click();">첨부파일</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<input type="hidden" id="bizTmpDataStrgType" name="bizTmpDataStrgType" />
</form>

<form>
	<input type="file" class="invisible" id="file" />
</form>

<div class="container bottom align-center">
	<button type="button" class="btn btn-info btn-large" id="tempSaveBtn">임시 저장</button>
	<button type="button" class="btn btn-info btn-large" id="uploadEdocBtn">전자문서 생성</button>
</div>
	
<%-- 	<div class="container">
		<form id="searchForm">
			<table>
				<tr>
					<th>조회 날짜</th>
					<td><input class="form-control" type="text" /></td>
					<th>고객명</th>
					<td><input type="text" size="10"/></td>
					<td rowspan="2"><input class="form-control btn btn-info btn-large" type="button" id="searchTempBtn" value="임시저장조회" /></td>
				</tr>
				<tr>
					<th>전자문서 그룹키</th>
					<td><input class="form-control" type="text" /></td>
					<th>저장 유형</th>
					<td>
						<select class="form-control" name="">
							<option value="">전체</option>
							<option value="A">A</option>
							<option value="T">T</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div class="container">
		<table>
			<thead>
				<tr>
					<th></th>
				</tr>
			</thead>
		</table>
	</div> --%>
</body>
<script>
		function resetAll(){			
			$('form').each(function(){
				this.reset();
			});
		}
</script>


</html>