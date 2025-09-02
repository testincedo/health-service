
package com.health.repo;

import com.health.dto.PatientSummaryDto;
import com.health.dto.PatientSearchCriteria;
import com.health.domain.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PatientSearchRepositoryImpl implements PatientSearchRepository {

    private final EntityManager em;

    @Override
    public Page<PatientSummaryDto> searchPatients(PatientSearchCriteria criteria, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Count query
        CriteriaQuery<Long> countQ = cb.createQuery(Long.class);
        Root<Patient> countRoot = countQ.from(Patient.class);
        countRoot.join("id", JoinType.LEFT);
        countQ.select(cb.count(countRoot));
        Long total = em.createQuery(countQ).getSingleResult();

        // Data query select patients paged
        CriteriaQuery<Patient> q = cb.createQuery(Patient.class);
        Root<Patient> root = q.from(Patient.class);
        q.select(root).orderBy(cb.desc(root.get("id")));
        TypedQuery<Patient> typed = em.createQuery(q);
        typed.setFirstResult((int) pageable.getOffset());
        typed.setMaxResults(pageable.getPageSize());
        List<Patient> patients = typed.getResultList();

        List<PatientSummaryDto> items = patients.stream()
                .map(p -> PatientSummaryDto.from(p, null))
                .collect(Collectors.toList());

        return new PageImpl<>(items, pageable, total);
    }
}
