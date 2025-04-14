package com.accenture.accreditation_service.repositories;

import com.accenture.accreditation_service.models.AccreditationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccreditationRepository extends JpaRepository<AccreditationEntity, Long> {
}
