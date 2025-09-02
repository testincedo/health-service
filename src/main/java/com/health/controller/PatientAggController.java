package com.health.controller;

import com.health.service.PatientAggService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/patient-agg")
@RequiredArgsConstructor
public class PatientAggController {

    @Autowired
    private PatientAggService aggService;

    @PostMapping("/refresh")
    public void refreshAggs() throws Exception {
        aggService.refreshPatientAgg();
    }
}
