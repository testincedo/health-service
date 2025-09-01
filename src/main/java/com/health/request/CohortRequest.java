
package com.health.request;

import lombok.Data;

@Data
public class CohortRequest {
    private String name;
    private String strategyKey;
    private String diseaseCode;
    private Integer minAge;
    private Integer maxAge;
}
