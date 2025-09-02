
package com.health.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "patient_agg",
       indexes = {
         @Index(name = "ix_pagg_visits_total", columnList = "visits_total"),
         @Index(name = "ix_pagg_last_visit", columnList = "last_visit_at")
       }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PatientAgg {
    @Id
    private Long patientId;

    private Integer visitsTotal;
    private Integer visitsLast365d;
    private OffsetDateTime firstVisitAt;
    private OffsetDateTime lastVisitAt;

    @Column(columnDefinition = "json")
    private String primaryDiagnosisTop3;

    @Column(columnDefinition = "json")
    private String doctorsRecentTop3;

    private OffsetDateTime lastAggRefreshedAt;
}
