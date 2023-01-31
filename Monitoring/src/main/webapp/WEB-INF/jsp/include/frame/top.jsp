<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 상단바 start-->
<%@ include file="/WEB-INF/jsp/include/frame/taglib.jsp"%>
<c:set var="_context" value="${(pageContext.request.contextPath eq '/')? '': pageContext.request.contextPath}" />

<script>
function logout() {
	if (confirm("로그아웃하시겠습니까?")) {
		document.location.href = "${_context}/logout";
	}
}

function modifyPassword() {
	$("#divModifyPasswordPopup").empty();
	$("#divModifyPasswordPopup").load("${_context}/password/modify/");
}
</script>

<div class="container">
  <div class="header">
  	<h1><a href="#">전자문서 모니터링 시스템 </a></h1>
  </div>
  <div id="alarm">
  	<ul>
      <sec:authorize access="isAuthenticated()">
      	<li><sec:authentication property="principal.username"/></li>
      	<li>|</li>
      	<li><sec:authentication property="principal.userMgmtDto.empNm"/></li>
      	<li>|</li>
      	<li><a href="javascript:modifyPassword();">[비밀번호 변경]</a></li>
      	<li>|</li>
      	<li><a href="javascript:logout();">[로그아웃]</a></li>
      </sec:authorize>
      <sec:authorize access="!isAuthenticated()">
      	<li><a href="<c:url value="/login" />">[로그인]</a></li>
      </sec:authorize>
  	</ul>
  </div>
	<ul id="topnav"> 
      <sec:authorize access="hasAnyRole('ROLE_A00_ACCS')">
        <li>
          <sec:authorize access="hasRole('ROLE_A00_ACCS')">
            <a href="<c:url value="/dashboard" />">대시보드</a>
          </sec:authorize>
        </li>
      </sec:authorize>
       <sec:authorize access="hasAnyRole('ROLE_D01_ACCS')">
      <li>
        <sec:authorize access="hasRole('ROLE_D01_ACCS')">
      	  <a href="<c:url value="/edoc/view" />">통합전자문서 조회</a>
        </sec:authorize>
      </li>
      </sec:authorize>
  	  <sec:authorize access="hasAnyRole('ROLE_A01_ACCS', 'ROLE_A02_ACCS', 'ROLE_A03_ACCS', 'ROLE_A04_ACCS')">
  		<li>
  			<a href="javascript:;">창구 전자문서처리 관리</a>
  			<span class="submenu subOne">
  				<span class="submenu-link">
  					<sec:authorize access="hasRole('ROLE_A01_ACCS')">
  						<a href="<c:url value="/edoc/proc/monitoring" />" style="width:185px">창구 전자문서 처리결과 모니터링</a>
  					</sec:authorize>
  					<sec:authorize access="hasRole('ROLE_A02_ACCS')">
  						<a href="<c:url value="/edoc/proc/current" />">창구 전자문서 처리현황</a>
  					</sec:authorize>
  					<sec:authorize access="hasRole('ROLE_A03_ACCS')">
  						<a href="<c:url value="/edoc/proc/statistics" />">창구 전자문서 처리통계</a>
  					</sec:authorize>
  					<sec:authorize access="hasRole('ROLE_A04_ACCS')">
  						<a href="<c:url value="/edoc/proc/error" />">미처리 현황</a>
  					</sec:authorize>
  				</span>
  			</span>
  		</li>
  	</sec:authorize>
<%--   	<sec:authorize access="hasRole('ROLE_B01_ACCS')">
  		<li>
  			<sec:authorize access="hasRole('ROLE_B01_ACCS')">
  				<a href="<c:url value="/system/systemMgmt" />">시스템 관리</a>
  			</sec:authorize>
  		</li>
  	</sec:authorize> --%>
  	<sec:authorize access="hasAnyRole('ROLE_C01_ACCS', 'ROLE_C02_ACCS', 'ROLE_C03_ACCS')">
      <li>
      	<a href="javascript:;">기본 정보 관리</a>
      	<span class="submenu subThr">
      		<span class="submenu-link">
      			<sec:authorize access="hasRole('ROLE_C01_ACCS')">
      				<a href="<c:url value="/info/codeAdmin" />">코드 관리</a>
      			</sec:authorize>
      			<sec:authorize access="hasRole('ROLE_C02_ACCS')">
      				<a href="<c:url value="/info/userAdmin" />">사용자 관리</a>
      			</sec:authorize>
      			<sec:authorize access="hasRole('ROLE_C03_ACCS')">
      				<a href="<c:url value="/info/authAdmin" />">권한 관리</a>
      			</sec:authorize>
      		</span>
      	</span>
      </li>
  	 </sec:authorize>
  	
  	</ul>
  </div>
<!-- 상단바 end -->