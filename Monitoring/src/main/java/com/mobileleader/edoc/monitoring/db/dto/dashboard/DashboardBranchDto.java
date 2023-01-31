package com.mobileleader.edoc.monitoring.db.dto.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardBranchDto extends DashboardBaseDto {

    private String branchName;
    
    private int rank;
    
}
