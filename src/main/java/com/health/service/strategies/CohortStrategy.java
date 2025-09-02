
package com.health.service.strategies;

import com.health.request.CohortRequest;

import java.util.List;

public interface CohortStrategy {
    List<Long> findPatientIds(CohortRequest request);
    String key();
}
