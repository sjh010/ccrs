package com.mobileleader.edoc.monitoring.service.dashboard;

import java.util.List;
import com.mobileleader.edoc.monitoring.common.form.dashboard.DashboardForm;
import com.mobileleader.edoc.monitoring.common.model.response.DashboardBaseResponse;
import com.mobileleader.edoc.monitoring.common.model.response.DashboardBranchResponse;
import com.mobileleader.edoc.monitoring.db.dto.dashboard.DashboardBaseDto;
import com.mobileleader.edoc.monitoring.db.dto.dashboard.DashboardBranchDto;

public interface DashboardService {

    public DashboardBaseResponse getProcessingStatusCounts(DashboardForm request);
    
    public DashboardBaseDto getMonthlyProcessingStatusCounts(String searchYearMonth);
    
    public DashboardBaseDto getDailyProcessingStatusCounts(String searchDate);
    
    public DashboardBranchResponse getUsageTop5Branches(DashboardForm request);

    public List<DashboardBranchDto> getMonthlyUsageTop5Branches(String searchYearMonth);
    
    public List<DashboardBranchDto> getDailyUsageTop5Branches(String searchDate);
}
