
package com.health.domain;

import lombok.*;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VisitDiagnosisId implements Serializable {
  private Long visit;
  private Long diagnosis;
}
