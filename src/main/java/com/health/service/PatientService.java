package com.health.service;

import com.health.dto.PatientSearchCriteria;
import com.health.dto.PatientSummaryDto;
import com.health.repo.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientService {


    @Autowired
    private PatientRepository patientRepository;

    public Page<PatientSummaryDto> searchPatients(PatientSearchCriteria criteria, Pageable pageable) {
        return patientRepository.searchPatients(criteria, pageable);
    }
}
