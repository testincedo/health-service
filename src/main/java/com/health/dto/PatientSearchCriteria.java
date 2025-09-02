
package com.health.dto;

import lombok.Data;

import java.util.List;

@Data
public class PatientSearchCriteria {
    private List<String> genders;
    private Integer minVisitsTotal;
    private Integer minVisitsLast365d;
    private List<String> diagnosisCodes;
    private Integer withinDays;
}
