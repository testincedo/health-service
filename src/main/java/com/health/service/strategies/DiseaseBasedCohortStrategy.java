
package com.health.service.strategies;

import com.health.domain.Patient;
import com.health.request.CohortRequest;
import com.health.repo.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DiseaseBasedCohortStrategy implements CohortStrategy {

    private final PatientRepository patientRepository;

    @Override
    public List<Long> findPatientIds(CohortRequest request) {

        return patientRepository.findAll().stream()
                .filter(p -> request.getDiseaseCode() != null && p.getMrn() != null && p.getMrn().contains(request.getDiseaseCode()))
                .map(Patient::getId)
                .collect(Collectors.toList());
    }

    @Override
    public String key() {
        return "diseaseBased";
    }
}
