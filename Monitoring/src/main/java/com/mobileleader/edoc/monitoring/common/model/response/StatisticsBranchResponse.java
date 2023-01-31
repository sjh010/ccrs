package com.mobileleader.edoc.monitoring.common.model.response;

import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticsBranchResponse {

    private List<HashMap<String, Object>> monthlyUsageTop5Branches;

    private List<HashMap<String, Object>> dailyUsageTop5Branches;

}
