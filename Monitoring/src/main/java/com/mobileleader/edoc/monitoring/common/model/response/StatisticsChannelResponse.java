package com.mobileleader.edoc.monitoring.common.model.response;

import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticsChannelResponse {

    private List<HashMap<String, Object>> monthlyProcessingStatusCounts;
    
    private List<HashMap<String, Object>> dailyProcessingStatusCounts;

}
