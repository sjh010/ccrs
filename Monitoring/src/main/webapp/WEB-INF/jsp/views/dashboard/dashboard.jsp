<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/frame/header.jsp"%>
<%@ include file="/WEB-INF/jsp/include/frame/title.jsp"%>
</head>

<body>
  <%@ include file="/WEB-INF/jsp/include/frame/top.jsp"%>
  <!-- content -->
  <div class="dash-layout-wrap" style="height:400px">
    <div class="dash-layout-sub-wrap">
      <h2>업무별 등록 건수</h2>
      <form id="processingStatusForm">
        <div class="dash-layout-sub-content-wrap">
          <div class="dash-layout-sub-content">
            <h2>월별 현황</h2>
            <div class="date-wrapper">
              <span id="monthlyProcessingStatusChartPrev" class="fa fa-angle-left"></span>
              <input type="text" id="monthlyProcessingStatusDate" class="inputdate dashboard-date" name="searchYearMonth"/>
              <span id="monthlyProcessingStatusChartNext" class="fa fa-angle-right"></span>
            </div>
            <!-- <div id="monthlyProcessingStatusChart" class="chart-contents" style="width:500px"> -->
            <div id="monthlyProcessingStatusChart" class="chart-contents" style="height: 300px; overflow-y: auto;">
            	<table class="output" style="margin-top: 20px; width: 90%; margin-left:5%; margin-right:5%;">
			      	<thead id="theadProcList">
			          <tr>
			          	
			            <th style="width: 40%; padding:5px 0 5px 0; min-width: 200px !important;">업무</th>
			            <th style="width: 10%; padding:5px 0 5px 0;">건수</th>
			         
			            <th style="width: 40%; padding:5px 0 5px 0; min-width: 200px !important;">업무</th>
			            <th style="width: 10%; padding:5px 0 5px 0;">건수</th>
			          </tr>
			      	</thead>
			      	<tbody id="tbodyMonthly"></tbody>
			      </table>
          	</div>
          </div>
          <div class="dash-layout-sub-content">
            <h2>일별 현황</h2>
            <div class="date-wrapper">
              <span id="dailyProcessingStatusChartPrev" class="fa fa-angle-left dt_btn_prev_1"></span>
              <input type="text" id="dailyProcessingStatusDate" class="inputdate dashboard-date" name="searchDate"/>
              <span id="dailyProcessingStatusChartNext" class="fa fa-angle-right dt_btn_next_1"></span>
            </div>
            <!-- <div id="dailyProcessingStatusChart" class="chart-contents" style="width:500px"></div> -->
            <div id="dailyProcessingStatusChart" class="chart-contents" style="height: 300px; overflow-y: auto;">
            	<table class="output" style="margin-top: 20px; width: 90%; margin-left:5%; margin-right:5%;">
			      	<thead id="theadProcList">
			          <tr>
			          	
			            <th style="width: 40%; padding:5px 0 5px 0; min-width: 200px !important;">업무</th>
			            <th style="width: 10%; padding:5px 0 5px 0;">건수</th>
			         
			            <th style="width: 40%; padding:5px 0 5px 0; min-width: 200px !important;">업무</th>
			            <th style="width: 10%; padding:5px 0 5px 0;">건수</th>
			          </tr>
			      	</thead>
			      	<tbody id="tbodyDaily"></tbody>
			      </table>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
  
  <div class="dash-layout-wrap" style="height:400px;">
    <div class="dash-layout-sub-wrap">
      <!-- <h2>전자문서 사용 지점 Top5</h2> -->
      <h2>통합전자문서 등록 지부 Top10</h2>
      <form id="usageTop5BranchesForm">
        <div class="dash-layout-sub-content-wrap">
          <div class="dash-layout-sub-content">
            <h2>월별 현황</h2>
            <div class="date-wrapper">
              <span id="monthlyUsageTop5BranchesChartPrev" class="fa fa-angle-left mt_btn_prev_2"></span>
              <input type="text" id="monthlyUsageTop5BranchesDate" class="inputdate dashboard-date" name="searchYearMonth"/>
              <span id="monthlyUsageTop5BranchesChartNext" class="fa fa-angle-right mt_btn_next_2"></span>
            </div>
            <div id="monthlyUsageTop5BranchesChart" class="chart-contents" style="min-width: 100px; min-height: 100px; height: 320px;"></div>
   <!--          <div class="table-wrapper">
              <table id="monthlyUsageTop5BranchesTable" class="output" style="margin:8px 4px 0 4px; width:100%;">
              </table>
            </div> -->
          </div>
  
          <div class="dash-layout-sub-content">
            <h2>일별 현황</h2>
            <div class="date-wrapper">
              <span id="dailyUsageTop5BranchesChartPrev" class="fa fa-angle-left dt_btn_prev_2"></span>
              <input type="text" id="dailyUsageTop5BranchesDate" class="inputdate dashboard-date" name="searchDate"/>
              <span id="dailyUsageTop5BranchesChartNext" class="fa fa-angle-right dt_btn_next_2"></span>
            </div>
            <div id="dailyUsageTop5BranchesChart" class="chart-contents" style="min-width: 100px; min-height: 100px; height: 320px;"></div>
            <div class="table-wrapper">
              <table id="dailyUsageTop5BranchesTable" class="output" style="margin:8px 4px 0 4px; width:100%;">
              </table>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
  <!-- contents end -->
  <%@ include file="/WEB-INF/jsp/include/frame/bottom.jsp"%>	
  <%@ include file="/WEB-INF/jsp/include/frame/footer.jsp"%>
  <script src="${_context}/js/echart/echarts.js"></script>
  <script src="${_context}/js/echart/constant.js"></script>
  <script>
  $(document).ready(function(){
	  initailizeDate();
	  initializeDashboard();
	  initializeDashboardEvent();
	  
	  jsSetCode();
  });
  
  var insourceTitleList = {};
  
  function jsSetCode() {
	<c:forEach var="insourceTitle" items="${insourceTitleList }" varStatus="status">
	insourceTitleList["<c:out value="${insourceTitle.cdVal}" />"] = "<c:out value="${insourceTitle.cdNm}" />";
	</c:forEach>
  }
  
  function initailizeDate() {
	  var currentDate = new Date();
	  $("#monthlyProcessingStatusDate,#monthlyUsageTop5BranchesDate").monthpicker({
		  pattern: 'yyyy.mm',
		  monthNames : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
      });
      $("#monthlyProcessingStatusDate,#monthlyUsageTop5BranchesDate").val(currentDate.format("yyyy.MM"));
	  
	  $("#dailyProcessingStatusDate,#dailyUsageTop5BranchesDate").datepicker({
		  dateFormat: "yy.mm.dd",
		  maxDate: "today"
	  });
	  $("#dailyProcessingStatusDate,#dailyUsageTop5BranchesDate").val(currentDate.format("yyyy.MM.dd"));
  }
  
  function initializeDashboard() {
	  jsAjaxPost("${_context}/dashboard/biz/count", $("#processingStatusForm").serialize(), function(response) {
		  createProcessingStatusChart(response.monthlyProcessingStatusCounts, "tbodyMonthly");
		  createProcessingStatusChart(response.dailyProcessingStatusCounts, "tbodyDaily");
	  });
	  jsAjaxPost("${_context}/dashboard/branches", $("#usageTop5BranchesForm").serialize(), function(response) {
		  createUsageTop5BranchesChart(response.monthlyUsageTop5Branches, "monthlyUsageTop5BranchesChart", "monthlyUsageTop5BranchesTable");
		  createUsageTop5BranchesChart(response.dailyUsageTop5Branches, "dailyUsageTop5BranchesChart", "dailyUsageTop5BranchesTable");
      });
  }
  
  function initializeDashboardEvent() {
	  // 전자문서 처리현황 월별 이벤트 handler
	  $("#monthlyProcessingStatusDate").on("change", function() {
		  jsAjaxPost("${_context}/dashboard/biz/count/monthly", $("#processingStatusForm").serialize(), function(response) {
			  createProcessingStatusChart(response, "tbodyMonthly");  
		  });
	  });
	  
	  $("#monthlyProcessingStatusChartPrev").on("click", function(){
		  var date = new Date($("#monthlyProcessingStatusDate").val());
		  date.setMonth(date.getMonth() - 1);
		  $("#monthlyProcessingStatusDate").val(date.format("yyyy.MM"));
		  jsAjaxPost("${_context}/dashboard/biz/count/monthly", $("#processingStatusForm").serialize(), function(response) {
              createProcessingStatusChart(response, "tbodyMonthly");  
          });
	  });
	  
	  $("#monthlyProcessingStatusChartNext").on("click", function(){
		  var date = new Date($("#monthlyProcessingStatusDate").val());
          date.setMonth(date.getMonth() + 1);
          $("#monthlyProcessingStatusDate").val(date.format("yyyy.MM"));
		  jsAjaxPost("${_context}/dashboard/biz/count/monthly", $("#monthlyProcessingStatusDate").serialize(), function(response) {
              createProcessingStatusChart(response, "tbodyMonthly");  
          });
      });
	  
	  // 전자문서 처리현황 일별 이벤트 handler
      $("#dailyProcessingStatusDate").on("change", function() {
          jsAjaxPost("${_context}/dashboard/biz/count/daily", $("#processingStatusForm").serialize(), function(response) {
              createProcessingStatusChart(response, "tbodyDaily");  
          });
      });
        
      $("#dailyProcessingStatusChartPrev").on("click", function(){
          var date = new Date($("#dailyProcessingStatusDate").val());
          date.setDate(date.getDate() - 1);
          $("#dailyProcessingStatusDate").val(date.format("yyyy.MM.dd"));
          jsAjaxPost("${_context}/dashboard/biz/count/daily", $("#processingStatusForm").serialize(), function(response) {
              createProcessingStatusChart(response, "tbodyDaily");
          });
      });
        
      $("#dailyProcessingStatusChartNext").on("click", function(){
          var date = new Date($("#dailyProcessingStatusDate").val());
          date.setDate(date.getDate() + 1);
          $("#dailyProcessingStatusDate").val(date.format("yyyy.MM.dd"));
          jsAjaxPost("${_context}/dashboard/biz/count/daily", $("#dailyProcessingStatusDate").serialize(), function(response) {
              createProcessingStatusChart(response, "tbodyDaily");  
          });
      });
	  
	  // 사용지점 Top5 월별 이벤트 handler
	  $("#monthlyUsageTop5BranchesDate").on("change", function() {
          jsAjaxPost("${_context}/dashboard/branches/monthly", $("#usageTop5BranchesForm").serialize(), function(response) {
        	  createUsageTop5BranchesChart(response, "monthlyUsageTop5BranchesChart", "monthlyUsageTop5BranchesTable");  
          });
      });
      
      $("#monthlyUsageTop5BranchesChartPrev").on("click", function(){
          var date = new Date($("#monthlyUsageTop5BranchesDate").val());
          date.setMonth(date.getMonth() - 1);
          $("#monthlyUsageTop5BranchesDate").val(date.format("yyyy.MM"));
          jsAjaxPost("${_context}/dashboard/branches/monthly", $("#usageTop5BranchesForm").serialize(), function(response) {
        	  createUsageTop5BranchesChart(response, "monthlyUsageTop5BranchesChart", "monthlyUsageTop5BranchesTable");  
          });
      });
      
      $("#monthlyUsageTop5BranchesChartNext").on("click", function(){
          var date = new Date($("#monthlyUsageTop5BranchesDate").val());
          date.setMonth(date.getMonth() + 1);
          $("#monthlyUsageTop5BranchesDate").val(date.format("yyyy.MM"));
          jsAjaxPost("${_context}/dashboard/branches/monthly", $("#usageTop5BranchesForm").serialize(), function(response) {
        	  createUsageTop5BranchesChart(response, "monthlyUsageTop5BranchesChart", "monthlyUsageTop5BranchesTable");  
          });
      });
      
	  // 사용지점 Top5 일별 이벤트 handler
      $("#dailyUsageTop5BranchesDate").on("change", function() {
          jsAjaxPost("${_context}/dashboard/branches/daily", $("#usageTop5BranchesForm").serialize(), function(response) {
              createUsageTop5BranchesChart(response, "dailyUsageTop5BranchesChart", "dailyUsageTop5BranchesTable"); 
          });
      });
      
      $("#dailyUsageTop5BranchesChartPrev").on("click", function(){
          var date = new Date($("#dailyUsageTop5BranchesDate").val());
          date.setDate(date.getDate() - 1);
          $("#dailyUsageTop5BranchesDate").val(date.format("yyyy.MM.dd"));
          jsAjaxPost("${_context}/dashboard/branches/daily", $("#usageTop5BranchesForm").serialize(), function(response) {
              createUsageTop5BranchesChart(response, "dailyUsageTop5BranchesChart", "dailyUsageTop5BranchesTable");  
          });
      });
      
      $("#dailyUsageTop5BranchesChartNext").on("click", function(){
          var date = new Date($("#dailyUsageTop5BranchesDate").val());
          date.setDate(date.getDate() + 1);
          $("#dailyUsageTop5BranchesDate").val(date.format("yyyy.MM.dd"));
          jsAjaxPost("${_context}/dashboard/branches/daily", $("#usageTop5BranchesForm").serialize(), function(response) {
              createUsageTop5BranchesChart(response, "dailyUsageTop5BranchesChart", "dailyUsageTop5BranchesTable");  
          });
      });
  }
  
  function createProcessingStatusChart(data, elementId) {
	  $("#" + elementId + " tr").remove();
	  
	  var htmlStr = '';

	  if (data.length == 0) {
		  $("#" + elementId).append('<tr><td colspan="4">조회 결과가 없습니다.</td></tr>');
	  } else {
		  $.each(data, function(index, item){			  
			  if (index != 0 && index % 2 == 1) {
					htmlStr += '    <td style="padding:3px 0 3px 0;">' + checkTitle(insourceTitleList[item.INSOURCE_ID]) +'</td>';
					htmlStr += '    <td style="padding:3px 0 3px 0; font-weight: bold">' + item.COUNT +'</td>';
					htmlStr += '</tr>';
					$("#" + elementId).append(htmlStr);
					
					htmlStr = '';
	
			  } else if (index == 0 || index % 2 == 0) {
				    htmlStr += '<tr>';
					htmlStr += '    <td style="padding:3px 0 3px 0;">' + checkTitle(insourceTitleList[item.INSOURCE_ID]) +'</td>';
					htmlStr += '    <td style="padding:3px 0 3px 0; font-weight: bold">' + item.COUNT +'</td>';
			  } 
			  
			  if ((data.length - 1) == index) {
				  if (index % 2 == 0) {
					  htmlStr += '    <td style="padding:3px 0 3px 0;"></td>';
					  htmlStr += '    <td style="padding:3px 0 3px 0; font-weight: bold"></td>';
				  }
				  htmlStr += '</tr>';
				  $("#" + elementId).append(htmlStr);
			  }

	      });
	  }
  }
  
  function checkTitle(insourceTitle) {
	  if (insourceTitle) {
		  return insourceTitle;
	  } else {
		  return "테스트";
	  }
  }

  // 통합전자문서 등록 지점 top10(하단 2개) chart
  function createUsageTop5BranchesChart(data, chartElementId, tableElementId) {

      var chart = echarts.init(document.getElementById(chartElementId));
  
      var chartData = {
        branches : [],
        totalDatas : []
      }
      
      $.each(data, function(index, item){
    	  chartData.branches.push(item.BRANCH_TITLE);
    	  chartData.totalDatas.push(item.COUNT);
      });
      
      var option = {
          legend: {
             // x : "center",
              y : "bottom"
          },
          tooltip: {},
          xAxis: {
        	  type: 'category',
              data: chartData.branches,
              axisLabel: {
            	  show: true,
            	  rotate: 0,
            	  margin: 8,
            	  interval: 0
              },
              splitLine : {
            	  
              }
          },
          yAxis: {
        	//  data : chartData.totalDatas
          },
          series: [	  
              {
                  //name: CHART_NAME.TOTAL,
                  type: 'bar',
                  color : CHART_COLOR.COMPLETE,
                  data: chartData.totalDatas,
               	  barWidth: 40,
               	  maxBarWidth: 70
              }
          ]
      };
      chart.setOption(option);
      
  }
  
  </script>
</body>
</html>