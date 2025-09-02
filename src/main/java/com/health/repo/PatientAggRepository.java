
package com.health.repo;

import com.health.domain.PatientAgg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientAggRepository extends JpaRepository<PatientAgg, Long> {
}
