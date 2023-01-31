package com.mobileleader.edoc.monitoring.common.form.dashboard;

import org.hibernate.validator.constraints.NotEmpty;
import com.mobileleader.edoc.monitoring.support.validation.annotation.DateFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardForm {

    @NotEmpty
    @DateFormat(pattern = "yyyy.MM")
    private String searchYearMonth;
    
    @NotEmpty
    @DateFormat(pattern = "yyyy.MM.dd")
    private String searchDate;
    
}