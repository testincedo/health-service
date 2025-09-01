
package com.health.service;

import com.health.request.CohortRequest;

public interface CohortStrategy {
    java.util.List<Long> findPatientIds(CohortRequest request);
    String key();
}
