
package com.health.service;

import com.health.exception.UnsupportedStrategyException;
import com.health.request.CohortRequest;
import com.health.service.strategies.CohortStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CohortService {

    private final Map<String, CohortStrategy> strategies;

    public List<Long> createCohort(CohortRequest request) {
        CohortStrategy cohortStrategy = strategies.get(request.getStrategyKey());
        if (cohortStrategy == null) {
            throw new UnsupportedStrategyException(request.getStrategyKey());
        }

        List<Long> ids = cohortStrategy.findPatientIds(request);
        return ids;
    }
}
