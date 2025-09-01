
package com.health.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "visit",
       indexes = {
         @Index(name = "ix_visit_patient_time", columnList = "patient_id, visit_time"),
         @Index(name = "ix_visit_doctor_time", columnList = "doctor_id, visit_time")
       }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Visit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "visit_time", nullable = false)
    private OffsetDateTime visitTime;

    @Column(name = "visit_type", length = 16)
    private String visitType;

    @Lob
    private String notes;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
