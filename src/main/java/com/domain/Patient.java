
package com.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "patient",
       indexes = {
         @Index(name = "ix_patient_name", columnList = "last_name,first_name"),
         @Index(name = "ix_patient_dob", columnList = "date_of_birth")
       },
       uniqueConstraints = {@UniqueConstraint(columnNames = {"mrn"})}
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String mrn;

    @Column(nullable = false, length = 64)
    private String firstName;

    @Column(nullable = false, length = 64)
    private String lastName;

    @Column(nullable = false, length = 1)
    private String gender; // M/F/O/U

    private LocalDate dateOfBirth;

    private String phone;

    private String email;

    private String addressCity;
    private String addressState;
    private String addressCountry;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
