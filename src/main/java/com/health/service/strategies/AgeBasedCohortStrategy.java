
package com.health.service.strategies;

import com.health.domain.Patient;
import com.health.request.CohortRequest;
import com.health.repo.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AgeBasedCohortStrategy implements CohortStrategy {

    private final PatientRepository patientRepository;

    @Override
    public List<Long> findPatientIds(CohortRequest request) {
        if (request.getMinAge() == null && request.getMaxAge() == null) {
            return patientRepository.findAll().stream().map(Patient::getId).collect(Collectors.toList());
        }

        LocalDate today = LocalDate.now();
        return patientRepository.findAll().stream()
                .filter(p -> p.getDateOfBirth() != null)
                .filter(p -> {
                    int age = Period.between(p.getDateOfBirth(), today).getYears();
                    if (request.getMinAge() != null && age < request.getMinAge()) return false;
                    return request.getMaxAge() == null || age <= request.getMaxAge();
                })
                .map(Patient::getId)
                .collect(Collectors.toList());
    }

    @Override
    public String key() {
        return "ageBasedCohortStrategy";
    }
}
