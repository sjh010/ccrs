package com.mobileleader.edoc.monitoring.common.model.response;

import com.mobileleader.edoc.monitoring.db.dto.dashboard.DashboardBaseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DashboardBaseResponse {

    private DashboardBaseDto monthlyProcessingStatusCounts;
    
    private DashboardBaseDto dailyProcessingStatusCounts;

    @Builder
    public DashboardBaseResponse(DashboardBaseDto monthlyProcessingStatusCounts,
            DashboardBaseDto dailyProcessingStatusCounts) {
        this.monthlyProcessingStatusCounts = monthlyProcessingStatusCounts;
        this.dailyProcessingStatusCounts = dailyProcessingStatusCounts;
    }
}
