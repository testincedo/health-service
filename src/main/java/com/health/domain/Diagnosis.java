
package com.health.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "diagnosis", indexes = {@Index(name = "ux_diag_code", columnList = "code")})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Diagnosis {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 16, unique = true)
  private String code;

  private String description;
}
