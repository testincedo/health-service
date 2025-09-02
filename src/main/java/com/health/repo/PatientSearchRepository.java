
package com.health.repo;

import com.health.dto.PatientSummaryDto;
import com.health.dto.PatientSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientSearchRepository {
    Page<PatientSummaryDto> searchPatients(PatientSearchCriteria criteria, Pageable pageable);
}
