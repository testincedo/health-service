package com.health.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.health.domain.PatientAgg;
import com.health.repo.PatientAggRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PatientAggService {

    private final PatientAggRepository patientAggRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void refreshPatientAgg() throws JsonProcessingException {
        OffsetDateTime cutoff = OffsetDateTime.now().minusDays(365);

        // Step 1: Aggregate total visits, first and last visit per patient
        List<Object[]> results = em.createQuery("""
        SELECT v.patient.id,
               COUNT(v.id),
               MIN(v.visitTime),
               MAX(v.visitTime)
        FROM Visit v
        GROUP BY v.patient.id
    """, Object[].class)
                .getResultList();

        for (Object[] row : results) {
            Long patientId = (Long) row[0];
            Long visitsTotal = (Long) row[1];
            OffsetDateTime firstVisit = (OffsetDateTime) row[2];
            OffsetDateTime lastVisit = (OffsetDateTime) row[3];

            // Step 2: Count visits in the last 365 days
            Long visitsLast365d = em.createQuery("""
            SELECT COUNT(v.id)
            FROM Visit v
            WHERE v.patient.id = :pid AND v.visitTime >= :cutoff
        """, Long.class)
                    .setParameter("pid", patientId)
                    .setParameter("cutoff", cutoff)
                    .getSingleResult();

            // Step 3: Get top 3 diagnoses for this patient
            List<String> diagTop3 = em.createQuery("""
            SELECT vd.diagnosis.code
            FROM VisitDiagnosis vd
            WHERE vd.visit.patient.id = :pid
            GROUP BY vd.diagnosis.code
            ORDER BY COUNT(vd.diagnosis) DESC
        """, String.class)
                    .setParameter("pid", patientId)
                    .setMaxResults(3)
                    .getResultList();

            // Step 4: Get top 3 recent doctors for this patient
            List<String> doctorTop3 = em.createQuery("""
            SELECT v.doctor.npi
            FROM Visit v
            WHERE v.patient.id = :pid
            ORDER BY v.visitTime DESC
        """, String.class)
                    .setParameter("pid", patientId)
                    .setMaxResults(3)
                    .getResultList();

            // Step 5: Convert to JSON
            String diagJson = objectMapper.writeValueAsString(diagTop3);
            String doctorJson = objectMapper.writeValueAsString(doctorTop3);

            // Step 6: Save aggregation
            PatientAgg agg = PatientAgg.builder()
                    .patientId(patientId)
                    .visitsTotal(visitsTotal.intValue())
                    .visitsLast365d(visitsLast365d.intValue())
                    .firstVisitAt(firstVisit)
                    .lastVisitAt(lastVisit)
                    .primaryDiagnosisTop3(diagJson)
                    .doctorsRecentTop3(doctorJson)
                    .lastAggRefreshedAt(OffsetDateTime.now())
                    .build();

            patientAggRepository.save(agg);
        }
    }

}
