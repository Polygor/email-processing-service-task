package com.polygor.email.processing.service;

import com.polygor.email.processing.dto.EmailTO;
import com.polygor.email.processing.dto.EmailsResourcesRequestTO;
import com.polygor.email.processing.dto.mapper.EmailMapper;
import com.polygor.email.processing.entity.EmailEntity;
import com.polygor.email.processing.exception.ConstraintsViolationException;
import com.polygor.email.processing.exception.InvalidEmailException;
import com.polygor.email.processing.repository.EmailRepository;
import com.polygor.email.processing.rest.EmailProcessingRestTemplate;
import com.polygor.email.processing.validation.EmailValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);
    private static final long ENCOUNTER = 1L;
    private static final long ZERO = 0L;
    private static final String EMPTY_STRING = "";
    private final EmailRepository emailRepository;
    private final EmailValidator emailValidator;
    private final EmailMapper emailMapper;
    private final Map<String, Long> encounteredEmailsCache = new ConcurrentHashMap<>();
    private final EmailProcessingRestTemplate restTemplate;

    @Override
    public void feedBulkEmails(EmailsResourcesRequestTO uploadDto) {
        feedCache(Collections.singletonList(uploadDto));
    }

    private void feedCache(List<EmailsResourcesRequestTO> uploadDto) {
        uploadDto.forEach(dto -> {
                    dto.getEmails().stream()
                    .filter(emailValidator::validate)
                    .forEach(email -> {
                        encounteredEmailsCache.putIfAbsent(email, ZERO);
                        encounteredEmailsCache.computeIfPresent(email, (key, lastCount) -> lastCount + ENCOUNTER);
                    });
            this.feedCache(getEmailsByResources(dto.getResources()));
        });
    }

    private List<EmailsResourcesRequestTO> getEmailsByResources(final List<String> urls) {
        List<EmailsResourcesRequestTO> fetchedByUrlDTOs = new ArrayList<>();
        AtomicReference<String> referenceForLogging = new AtomicReference<>(EMPTY_STRING);
        try {
            fetchedByUrlDTOs = urls.stream().map(url -> {
                referenceForLogging.set(url);
                return restTemplate.getForEntity(referenceForLogging.get(), EmailsResourcesRequestTO.class).getBody();
            }).collect(Collectors.toList());
        } catch (HttpClientErrorException ex) {
            LOG.warn("Error has been occurred while fetching new data via external URL: " + referenceForLogging, ex);
        }
        return fetchedByUrlDTOs;
    }

    @Scheduled(fixedDelayString = "50000")
    public void persistCache() {
        this.encounteredEmailsCache.forEach((email, newCount) -> {
            persistEmail(email, newCount);
            this.encounteredEmailsCache.computeIfPresent(email, (key, lastCount) -> lastCount - newCount);
        });
        this.encounteredEmailsCache.entrySet().removeIf(entry -> entry.getValue() == ZERO);
    }

    @Override
    @Transactional
    public EmailTO createEmail(EmailTO emailTO) throws InvalidEmailException, ConstraintsViolationException {
        EmailEntity emailToPersist = emailMapper.mapToEntity(emailTO);
        try {
            if (emailValidator.validate(emailToPersist.getEmail())) {
                throw new InvalidEmailException("Invalid email, only comenon.com and cherry.se domains are allowed");
            }
            emailToPersist = emailRepository.save(emailToPersist);
        } catch (DataIntegrityViolationException e) {
            LOG.warn("Error while creating an email: {}", emailToPersist, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return emailMapper.mapToDto(emailToPersist);
    }

    @Override
    @Transactional
    public EmailTO modifyEmail(Long emailId, Long encounteredCount) {
      EmailEntity emailEntity = this.emailRepository.findById(emailId).orElseThrow(() -> new EntityNotFoundException("Email entity was not found by id: " + emailId));
      emailEntity.setEncounteredCount(encounteredCount);
      emailRepository.save(emailEntity);
      return emailMapper.mapToDto(emailEntity);
    }

    @Transactional
    public void persistEmail(final String email, final Long count) {
        EmailEntity emailToPersist = emailRepository.findByEmail(email).orElse(new EmailEntity(email));
        emailToPersist.increaseEncounteredCount(count);
        this.emailRepository.save(emailToPersist);
    }


    public Page<EmailTO> findAll(Pageable pageable) {
        Page<EmailEntity> peopleEntities = emailRepository.findAll(pageable);
        return this.emailMapper.mapToDto(peopleEntities);
    }

    public Page<EmailTO> findByEmail(String email, Pageable pageable) {
        Page<EmailEntity> peopleEntities = emailRepository.findByEmail(email, pageable);
        return this.emailMapper.mapToDto(peopleEntities);
    }

    @Override
    public Page<EmailTO> findByCount(Long count, Pageable pageable) {
        Page<EmailEntity> peopleEntities = emailRepository.findByEncounteredCount(count, pageable);
        return this.emailMapper.mapToDto(peopleEntities);
    }

    public void deleteBy(Long id) {
        this.emailRepository.deleteById(id);
    }
}
