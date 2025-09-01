
package com.health.controller;

import com.health.service.CohortRequest;
import com.health.service.CohortService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cohorts")
@RequiredArgsConstructor
public class CohortController {

    private final CohortService cohortService;

    @PostMapping("/execute")
    @Operation(summary = "Execute cohort request and return patient ids")
    public ResponseEntity<List<Long>> execute(@RequestBody CohortRequest req) {
        return ResponseEntity.ok(cohortService.createCohort(req));
    }
}
