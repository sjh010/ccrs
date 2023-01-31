package com.mobileleader.edoc.monitoring.common.model.response;

import java.util.List;
import com.mobileleader.edoc.monitoring.db.dto.dashboard.DashboardBranchDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DashboardBranchResponse {

    private List<DashboardBranchDto> monthlyUsageTop5Branches;

    private List<DashboardBranchDto> dailyUsageTop5Branches;

    @Builder
    public DashboardBranchResponse(List<DashboardBranchDto> monthlyUsageTop5Branches,
            List<DashboardBranchDto> dailyUsageTop5Branches) {
        this.monthlyUsageTop5Branches = monthlyUsageTop5Branches;
        this.dailyUsageTop5Branches = dailyUsageTop5Branches;
    }
}
