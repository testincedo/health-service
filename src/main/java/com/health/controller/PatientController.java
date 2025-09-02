
package com.health.controller;

import com.health.dto.PatientSearchCriteria;
import com.health.dto.PatientSummaryDto;
import com.health.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/search")
    @Operation(summary = "Search patients")
    public ResponseEntity<Page<PatientSummaryDto>> search(@RequestBody PatientSearchCriteria criteria,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(patientService.searchPatients(criteria, pageable));
    }
}
