
package com.health.dto;

import com.health.domain.PatientAgg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class InsightsDto {
    private Integer visitsTotal;
    private Integer visitsLast365d;
    private OffsetDateTime lastVisitAt;
    private List<String> primaryDiagnosisTop3;

    public static InsightsDto from(PatientAgg a) {
        return InsightsDto.builder()
                .visitsTotal(a.getVisitsTotal())
                .visitsLast365d(a.getVisitsLast365d())
                .lastVisitAt(a.getLastVisitAt())
                .primaryDiagnosisTop3(null)
                .build();
    }
}
