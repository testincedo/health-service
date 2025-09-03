
package com.health.repo;

import com.health.domain.*;
import com.health.dto.PatientSummaryDto;
import com.health.dto.PatientSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PatientSearchRepositoryImpl implements PatientSearchRepository {

    private final EntityManager em;

    @Override
    public Page<PatientSummaryDto> searchPatients(PatientSearchCriteria criteria, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Step 1: Query patient IDs
        CriteriaQuery<Long> idQuery = cb.createQuery(Long.class);
        Root<Visit> visitRoot = idQuery.from(Visit.class);
        Join<Visit, Patient> patientJoin = visitRoot.join("patient", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();
        if (criteria.getGenders() != null && !criteria.getGenders().isEmpty()) {
            predicates.add(patientJoin.get("gender").in(criteria.getGenders()));
        }
        if (criteria.getWithinDays() != null && criteria.getWithinDays() > 0) {
            OffsetDateTime cutoff = OffsetDateTime.now().minusDays(criteria.getWithinDays());
            predicates.add(cb.greaterThanOrEqualTo(visitRoot.get("visitTime"), cutoff));
        }

        idQuery.select(patientJoin.get("id")).distinct(true)
                .where(predicates.toArray(new Predicate[0]))
                .orderBy(cb.desc(patientJoin.get("id")));

        TypedQuery<Long> typedIdQuery = em.createQuery(idQuery);
        typedIdQuery.setFirstResult((int) pageable.getOffset());
        typedIdQuery.setMaxResults(pageable.getPageSize());
        List<Long> patientIds = typedIdQuery.getResultList();

        if (patientIds.isEmpty()) {
            return new PageImpl<>(List.of(), pageable, 0);
        }

        // Step 2: Fetch full Patient entities
        CriteriaQuery<Patient> patientQuery = cb.createQuery(Patient.class);
        Root<Patient> patientRoot = patientQuery.from(Patient.class);
        patientQuery.select(patientRoot)
                .where(patientRoot.get("id").in(patientIds));

        List<Patient> patients = em.createQuery(patientQuery).getResultList();

        // Step 3: Fetch PatientAgg
        CriteriaQuery<PatientAgg> aggQuery = cb.createQuery(PatientAgg.class);
        Root<PatientAgg> aggRoot = aggQuery.from(PatientAgg.class);
        aggQuery.select(aggRoot)
                .where(aggRoot.get("patientId").in(patientIds));
        List<PatientAgg> aggs = em.createQuery(aggQuery).getResultList();
        Map<Long, PatientAgg> aggMap = aggs.stream()
                .collect(Collectors.toMap(PatientAgg::getPatientId, a -> a));

        // Step 4: Map to DTOs
        List<PatientSummaryDto> items = patients.stream()
                .map(p -> PatientSummaryDto.from(p, aggMap.get(p.getId())))
                .collect(Collectors.toList());

        return new PageImpl<>(items, pageable, patientIds.size());
    }


}
