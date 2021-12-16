package com.polygor.email.processing.service;


import com.polygor.email.processing.dto.EmailTO;
import com.polygor.email.processing.dto.EmailsResourcesRequestTO;
import com.polygor.email.processing.exception.ConstraintsViolationException;
import com.polygor.email.processing.exception.InvalidEmailException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmailService {

    void feedBulkEmails(EmailsResourcesRequestTO emailsResourcesRequestTO);

    EmailTO createEmail(EmailTO emailTO) throws InvalidEmailException, ConstraintsViolationException;

    EmailTO modifyEmail(Long emailId, Long encounteredCount);

    Page<EmailTO> findAll(Pageable pageable);

    Page<EmailTO> findByEmail(String email, Pageable pageable);

    Page<EmailTO> findByCount(Long count, Pageable pageable);

    void deleteBy(Long id);
}
