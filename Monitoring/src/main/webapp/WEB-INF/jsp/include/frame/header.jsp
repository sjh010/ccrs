<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>

<%@ include file="/WEB-INF/jsp/include/frame/taglib.jsp"%>
<c:set var="_context" value="${(pageContext.request.contextPath eq '/')? '': pageContext.request.contextPath}" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<link rel="stylesheet" type="text/css" href="<c:url value="/css/reset.css?v=201803120000" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css?v=201803130002" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/font-awesome.min.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/dev.css?v=201803120000" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery/jquery-ui.min.css" />" />

<!--[if lt IE 9]>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery-1.11.1.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/util/html5shiv.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/util/IE9.js" />"></script>
<![endif]-->
<!--[if gte IE 9]>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery-3.2.1.min.js" />"></script>
<![endif]-->
<!--[if !IE]> -->
<script type="text/javascript" src="<c:url value="/js/jquery/jquery-3.2.1.min.js" />"></script>
<!-- <![endif]-->
<script type="text/javascript" src="<c:url value="/js/jquery/jquery-ui.min.js" />"></script>
<script type="text/javascript">
/**
 * Jquery Ui Datepicker 한글 설정
 */
jQuery(function($){
	$.datepicker.regional['ko'] = {
		closeText: '닫기',
		prevText: '이전',
		nextText: '다음',
		currentText: '오늘',
		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
		dayNames: ['일','월','화','수','목','금','토'],
		dayNamesShort: ['일','월','화','수','목','금','토'],
		dayNamesMin: ['일','월','화','수','목','금','토'],
		weekHeader: 'Wk',
		dateFormat: 'yy-mm-dd',
		firstDay: 0,
		isRTL: false,
		showMonthAfterYear: true,
		yearSuffix: '',
		changeMonth: true, 
		changeYear: true,
		maxdate: 0
	};
	$.datepicker.setDefaults($.datepicker.regional['ko']);
});
</script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.blockUI.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.form.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.mtz.monthpicker.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.bpopup.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/jquery/jquery.fileupload.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/common/com.ajax.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/common/com.page.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/common/com.util.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/common/com.date.js" />"></script>