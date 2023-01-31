package com.mobileleader.edoc.monitoring.service.dashboard;

import java.util.List;
import org.springframework.stereotype.Service;
import com.mobileleader.edoc.monitoring.common.form.dashboard.DashboardForm;
import com.mobileleader.edoc.monitoring.common.model.response.DashboardBaseResponse;
import com.mobileleader.edoc.monitoring.common.model.response.DashboardBranchResponse;
import com.mobileleader.edoc.monitoring.db.dto.dashboard.DashboardBaseDto;
import com.mobileleader.edoc.monitoring.db.dto.dashboard.DashboardBranchDto;
import com.mobileleader.edoc.monitoring.db.mapper.EdocGroupBzwkInfoMapper;
import com.mobileleader.edoc.monitoring.db.mapper.EdocGroupProcsMgmtMapper;
import com.mobileleader.edoc.monitoring.exceptions.MonitoringServiceException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final EdocGroupBzwkInfoMapper bzwkInfoMapper;

    private final EdocGroupProcsMgmtMapper procsMgmtMapper;

    public DashboardBaseResponse getProcessingStatusCounts(DashboardForm request) {

        DashboardBaseResponse response = null;

        try {
            response = DashboardBaseResponse.builder()
                            .monthlyProcessingStatusCounts(procsMgmtMapper.selectDashboardProcessingStatusCountForMonthly(request.getSearchYearMonth()))
                            .dailyProcessingStatusCounts(procsMgmtMapper.selectDashboardProcessingStatusCountForDaily(request.getSearchDate()))
                            .build();
        } catch (RuntimeException e) {
            throw new MonitoringServiceException("전자문서 처리 현황 데이터를 가져오는데 실패했습니다.", e);
        }

        return response;
    }
    
    @Override
    public DashboardBaseDto getMonthlyProcessingStatusCounts(String searchYearMonth) {
        
        DashboardBaseDto response = null;

        try {
            response = procsMgmtMapper.selectDashboardProcessingStatusCountForMonthly(searchYearMonth);
        } catch (RuntimeException e) {
            throw new MonitoringServiceException("월별 전자문서 처리 현황 데이터를 가져오는데 실패했습니다.", e);
        }

        return response;
    }
    
    @Override
    public DashboardBaseDto getDailyProcessingStatusCounts(String searchDate) {
        
        DashboardBaseDto response = null;

        try {
            response = procsMgmtMapper.selectDashboardProcessingStatusCountForDaily(searchDate);
        } catch (RuntimeException e) {
            throw new MonitoringServiceException("일별 전자문서 처리 현황 데이터를 가져오는데 실패했습니다.", e);
        }
        
        return response;
    }

    public DashboardBranchResponse getUsageTop5Branches(DashboardForm request) {

        DashboardBranchResponse response = null;
        
        try {
            response = DashboardBranchResponse.builder()
                                    .monthlyUsageTop5Branches(bzwkInfoMapper.selectDashboardUsageTop5BranchesForMonthly(request.getSearchYearMonth()))
                                    .dailyUsageTop5Branches(bzwkInfoMapper.selectDashboardUsageTop5BranchesForDaily(request.getSearchDate()))
                                    .build();
        } catch (RuntimeException e) {
            throw new MonitoringServiceException("전자문서 사용지점 Top5 데이터를 가져오는데 실패했습니다.", e);
        }

        return response;
    }
    
    @Override
    public List<DashboardBranchDto> getMonthlyUsageTop5Branches(String searchYearMonth) {
        
        List<DashboardBranchDto> response = null;
        
        try {
            response = bzwkInfoMapper.selectDashboardUsageTop5BranchesForMonthly(searchYearMonth);
        } catch (RuntimeException e) {
            throw new MonitoringServiceException("월별 전자문서 사용지점 Top5 데이터를 가져오는데 실패했습니다.", e);
        }

        return response;
    }
    
    @Override
    public List<DashboardBranchDto> getDailyUsageTop5Branches(String searchDate) {
        List<DashboardBranchDto> response = null;
        
        try {
            response = bzwkInfoMapper.selectDashboardUsageTop5BranchesForDaily(searchDate);
        } catch (RuntimeException e) {
            throw new MonitoringServiceException("일별 전자문서 사용지점 Top5 데이터를 가져오는데 실패했습니다.", e);
        }

        return response;
    }
}
