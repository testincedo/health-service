package com.health.repo;

import com.health.domain.Diagnosis;
import com.health.domain.Visit;
import com.health.domain.VisitDiagnosis;
import com.health.domain.VisitDiagnosisId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitDiagnosisRepository extends JpaRepository<VisitDiagnosis, VisitDiagnosisId> {

    List<VisitDiagnosis> findByVisit(Visit visit);

    List<VisitDiagnosis> findByDiagnosis(Diagnosis diagnosis);

    List<VisitDiagnosis> findByVisitId(Long visitId);  // if you expose VisitDiagnosisId
}
