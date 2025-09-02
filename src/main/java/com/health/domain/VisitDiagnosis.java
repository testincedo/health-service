
package com.health.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "visit_diagnosis",
       indexes = {@Index(name = "ix_vdx_diagnosis", columnList = "diagnosis_id")})
@IdClass(VisitDiagnosisId.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VisitDiagnosis {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", nullable = false)
    private Visit visit;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id", nullable = false)
    private Diagnosis diagnosis;

    @Column(name = "primary_flag")
    private boolean primaryFlag;
}
