package com.polygor.email.processing.rest;

import com.polygor.email.processing.dto.EmailTO;
import com.polygor.email.processing.dto.EmailsResourcesRequestTO;
import com.polygor.email.processing.exception.ConstraintsViolationException;
import com.polygor.email.processing.exception.InvalidEmailException;
import com.polygor.email.processing.service.EmailService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/xml/emails")
@RequiredArgsConstructor
@Api
public final class EmailXmlRestController {

    private final EmailService emailService;

    @PostMapping(value = "/feed", consumes = MediaType.APPLICATION_XML_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void feedBulkDataEmails(@RequestBody EmailsResourcesRequestTO uploadDto) {
        this.emailService.feedBulkEmails(uploadDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmailTO createEmail(@RequestBody EmailTO emailTO) throws InvalidEmailException, ConstraintsViolationException {
        return emailService.createEmail(emailTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmailTO modifyEmail(@PathVariable Long id, @RequestParam Long encounteredCount)  {
        return emailService.modifyEmail(id, encounteredCount);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EmailTO>> getAllEmailsWithPagination(Pageable pageable) {
        return new ResponseEntity<>(emailService.findAll(pageable).getContent(), HttpStatus.OK);
    }

    @GetMapping("/all/new")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EmailTO>> getAllNewEmailsWithPagination(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of
                (
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by(Sort.Direction.DESC, "creationDate")
                );
        return new ResponseEntity<>(emailService.findAll(pageRequest).getContent(), HttpStatus.OK);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EmailTO>> getEmailByValue(@RequestParam("email") String email, Pageable pageable) {
        return new ResponseEntity<>(emailService.findByEmail(email, pageable).getContent(), HttpStatus.OK);
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EmailTO>> getEmailByValue(@RequestParam("encounteredCount") Long count, Pageable pageable) {
        return new ResponseEntity<>(emailService.findByCount(count, pageable).getContent(), HttpStatus.OK);
    }

    @GetMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmailBy(@RequestParam() Long id) {
        this.emailService.deleteBy(id);
    }
}
