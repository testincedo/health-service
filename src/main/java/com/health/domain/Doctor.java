
package com.health.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "doctor")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Doctor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // National Provider Identifier
    private String npi;
    private String fullName;
    private Long departmentId;
}
