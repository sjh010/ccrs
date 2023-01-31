<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="_context" value="${(pageContext.request.contextPath eq '/')? '': pageContext.request.contextPath}" />
<%
	response.setStatus(200);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/jsp/include/frame/title.jsp"%>
</head>
<style>
body {
    text-align: center;
}

img {
    max-width: 100%;
    height: auto;
}
</style>
<body>
    <div class="wrapper">
        <img src="${_context }/images/error_500.png">
    </div>
</body>
</html>