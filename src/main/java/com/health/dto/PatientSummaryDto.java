
package com.health.dto;

import com.health.domain.Patient;
import com.health.domain.PatientAgg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class PatientSummaryDto {
    private Long id;
    private String mrn;
    private String fullName;
    private String gender;
    private LocalDate dob;
    private InsightsDto insights;

    public static PatientSummaryDto from(Patient p, PatientAgg a) {
        InsightsDto ins = null;
        if (a != null) {
            ins = InsightsDto.from(a);
        }
        return PatientSummaryDto.builder()
                .id(p.getId())
                .mrn(p.getMrn())
                .fullName(p.getFirstName() + " " + p.getLastName())
                .gender(p.getGender())
                .dob(p.getDateOfBirth())
                .insights(ins)
                .build();
    }
}
