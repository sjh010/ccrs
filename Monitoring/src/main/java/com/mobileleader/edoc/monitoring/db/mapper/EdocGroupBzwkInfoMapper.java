package com.mobileleader.edoc.monitoring.db.mapper;

import java.util.List;
import com.mobileleader.edoc.monitoring.db.dto.dashboard.DashboardBranchDto;

public interface EdocGroupBzwkInfoMapper {

    /* 
        added query for dashboard by asyu (2019-05-30) 
        Top5 usage by branch 
    */
    public List<DashboardBranchDto> selectDashboardUsageTop5BranchesForMonthly(String searchYearMonth);
    
    public List<DashboardBranchDto> selectDashboardUsageTop5BranchesForDaily(String searchDate);}
