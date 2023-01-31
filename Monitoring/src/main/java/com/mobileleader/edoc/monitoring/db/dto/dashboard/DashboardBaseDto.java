package com.mobileleader.edoc.monitoring.db.dto.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardBaseDto {

    private int totalCount;
    
    private int completeCount;
    
    private int processingCount;
    
    private int errorCount;
}
