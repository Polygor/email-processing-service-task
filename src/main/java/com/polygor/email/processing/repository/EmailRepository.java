package com.polygor.email.processing.repository;

import com.polygor.email.processing.dto.EmailTO;
import com.polygor.email.processing.entity.EmailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface EmailRepository extends PagingAndSortingRepository<EmailEntity, Long> {

    Optional<EmailEntity> findByEmail(String email);

    Page<EmailEntity> findByEmail(String email, Pageable pageable);

    Page<EmailEntity> findByEncounteredCount(Long encounteredCount, Pageable pageable);

}
