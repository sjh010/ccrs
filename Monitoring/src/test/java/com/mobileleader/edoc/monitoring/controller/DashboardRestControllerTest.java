package com.mobileleader.edoc.monitoring.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.mobileleader.edoc.monitoring.common.annotation.TestDescription;
import com.mobileleader.edoc.monitoring.common.form.dashboard.DashboardForm;
import com.mobileleader.edoc.monitoring.config.RootConfiguration;
import com.mobileleader.edoc.monitoring.config.web.WebApplicationConfiguration;
import com.mobileleader.edoc.monitoring.config.web.WebMvcConfiguration;
import com.mobileleader.edoc.monitoring.controller.dashboard.DashboardRestController;
import com.mobileleader.edoc.monitoring.support.exceptionhandler.GlobalRestContorllerAdvice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebApplicationConfiguration.class, WebMvcConfiguration.class, RootConfiguration.class})
@WebAppConfiguration
public class DashboardRestControllerTest {

    @Autowired
    DashboardRestController dashboardRestController;

    private MockMvc mockMvc;
    
    @Before
    public void setUp() {
        setUpMockMvc();
    }

    private void setUpMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(dashboardRestController)
                                 .setControllerAdvice(new GlobalRestContorllerAdvice())
                                 .build();
    }
    
    @Test
    @TestDescription("???????????? ????????????(??????, ??????) ????????? ?????????")
    public void testProcessingStatus() throws Exception {
        // given
        DashboardForm request = new DashboardForm();
        request.setSearchYearMonth("2019.05");
        request.setSearchDate("2019.05.22");
        
        // when
        ResultActions result = mockMvc.perform(post("/dashboard/processing/status")
                                               .accept(MediaType.APPLICATION_JSON_VALUE)
                                               .contentType(MediaType.APPLICATION_JSON_VALUE)
                                               .param("searchYearMonth", request.getSearchYearMonth())
                                               .param("searchDate", request.getSearchDate())
                               );
        
        // then
        result.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("monthlyProcessingStatusCounts", Matchers.notNullValue()))
                    .andExpect(jsonPath("dailyProcessingStatusCounts", Matchers.notNullValue()))
                ;
    }
    
    @Test
    @TestDescription("???????????? ????????????(??????) ????????? ?????????")
    public void testMonthlyProcessingStatus() throws Exception {
        // given
        String request = "2019.05";
        
        // when
        ResultActions result = mockMvc.perform(post("/dashboard/processing/status/monthly")
                                               .accept(MediaType.APPLICATION_JSON_VALUE)
                                               .param("searchYearMonth", request)
                               );
        
        // then
        result.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("totalCount", Matchers.notNullValue()))
                    .andExpect(jsonPath("processingCount", Matchers.notNullValue()))
                    .andExpect(jsonPath("successCount", Matchers.notNullValue()))
                    .andExpect(jsonPath("failureCount", Matchers.notNullValue()))
                ;
    }
    
    @Test
    @TestDescription("???????????? ????????????(??????) ????????? ?????????")
    public void testDailyProcessingStatus() throws Exception {
        // given
        String request = "2019.05.22";
        
        // when
        ResultActions result = mockMvc.perform(post("/dashboard/processing/status/daily")
                                               .accept(MediaType.APPLICATION_JSON_VALUE)
                                               .param("searchDate", request)
                               );
        
        // then
        result.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("totalCount", Matchers.notNullValue()))
                    .andExpect(jsonPath("processingCount", Matchers.notNullValue()))
                    .andExpect(jsonPath("successCount", Matchers.notNullValue()))
                    .andExpect(jsonPath("failureCount", Matchers.notNullValue()))
                ;
    }
    
    @Test
    @TestDescription("???????????? ????????????(??????) validation ????????? ?????????")
    public void testValidataionMonthlyProcessingStatus() throws Exception {
        // given
        String request = "201905";
        
        // when
        ResultActions result = mockMvc.perform(post("/dashboard/processing/status/monthly")
                                               .accept(MediaType.APPLICATION_JSON_VALUE)
                                               .param("searchYearMonth", request)
                               );
        
        // then
        result.andDo(print())
                    .andExpect(status().isBadRequest())
                ;
    }
    
    @Test
    @TestDescription("???????????? ????????????(??????) validation ????????? ?????????")
    public void testValidationDailyProcessingStatus() throws Exception {
        // given
        String request = "20195022";
        
        // when
        ResultActions result = mockMvc.perform(post("/dashboard/processing/status/daily")
                                               .accept(MediaType.APPLICATION_JSON_VALUE)
                                               .param("searchDate", request)
                               );
        
        // then
        result.andDo(print())
                    .andExpect(status().isBadRequest())
                ;
    }
    
    @Test
    @TestDescription("???????????? ????????? TOP5 ??????(??????, ??????) ?????????")
    public void testUsageTop5Branches() throws Exception {
        // given
        DashboardForm request = new DashboardForm();
        request.setSearchYearMonth("2019.05");
        request.setSearchDate("2019.05.22");
        
        // when
        ResultActions result = mockMvc.perform(post("/dashboard/branches")
                                               .accept(MediaType.APPLICATION_JSON_VALUE)
                                               .contentType(MediaType.APPLICATION_JSON_VALUE)
                                               .param("searchYearMonth", request.getSearchYearMonth())
                                               .param("searchDate", request.getSearchDate())
                               );
        
        // then
        result.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("monthlyUsageTop5Branches", Matchers.notNullValue()))
                    .andExpect(jsonPath("dailyUsageTop5Branches", Matchers.notNullValue()))
                ;
    }
    
    @Test
    @TestDescription("???????????? ????????? TOP5 ??????(??????) validation ?????????")
    public void testValidationMonthlyUsageTop5Branches() throws Exception {
        // given
        String request = "201905";
        
        // when
        ResultActions result = mockMvc.perform(post("/dashboard/branches/monthly")
                                               .accept(MediaType.APPLICATION_JSON_VALUE)
                                               .param("searchYearMonth", request)
                               );
        
        // then
        result.andDo(print())
                    .andExpect(status().isBadRequest())
                ;
    }
    
    @Test
    @TestDescription("???????????? ????????? TOP5 ??????(??????) validation ?????????")
    public void testValidationDailyUsageTop5Branches() throws Exception {
        // given
        String request = "20190522";
        
        // when
        ResultActions result = mockMvc.perform(post("/dashboard/branches/daily")
                                               .accept(MediaType.APPLICATION_JSON_VALUE)
                                               .param("searchDate", request)
                               );
        
        // then
        result.andDo(print())
                    .andExpect(status().isBadRequest()  )
                ;
    }
    
    @Test
    @TestDescription("dashboard validation ?????????")
    public void testRequestDateValidation() throws Exception {
        // given
        DashboardForm request = new DashboardForm();
        request.setSearchYearMonth("20190522");
        request.setSearchDate("201905");
        
        // when
        ResultActions result = mockMvc.perform(post("/dashboard/processing/status")
                                               .accept(MediaType.APPLICATION_JSON_VALUE)
                                               .contentType(MediaType.APPLICATION_JSON_VALUE)
                                               .param("searchYearMonth", request.getSearchYearMonth())
                                               .param("searchDate", request.getSearchDate())
                               );
        
        // then
        result.andDo(print())
                    .andExpect(status().isBadRequest())
                    ;
        
        // when
        ResultActions result2 = mockMvc.perform(post("/dashboard/branches")
                                               .accept(MediaType.APPLICATION_JSON_VALUE)
                                               .contentType(MediaType.APPLICATION_JSON_VALUE)
                                               .param("searchYearMonth", request.getSearchYearMonth())
                                               .param("searchDate", request.getSearchDate())
                               );
        
        // then
        result2.andDo(print())
                    .andExpect(status().isBadRequest())
                    ;
    }
}
