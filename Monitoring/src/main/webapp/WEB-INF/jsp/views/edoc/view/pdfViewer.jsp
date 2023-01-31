<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/frame/header.jsp"%>
</head>
<body style="width: 100%; height: 100%; background-color: #ffffff; min-width: 680px; overflow-x: auto;">
	<!-- Adobe PDF Viewer -->
	<embed id="pdfAria" src="file:${file}" type="application/pdf" width="100%" height="100%">
</body>
</html>
