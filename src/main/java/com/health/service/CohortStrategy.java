
package com.health.service;

public interface CohortStrategy {
    java.util.List<Long> findPatientIds(CohortRequest request);
    String key();
}
