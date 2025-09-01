
package com.health.service;

import com.health.request.CohortRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CohortService {

    private final Map<String, CohortStrategy> strategies;

    public List<Long> createCohort(CohortRequest request) {
        CohortStrategy s = strategies.get(request.getStrategyKey());
        if (s == null) throw new IllegalArgumentException("Unsupported strategy: " + request.getStrategyKey());
        List<Long> ids = s.findPatientIds(request);
        return ids;
    }
}
