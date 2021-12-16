package com.polygor.email.processing.config;

import com.polygor.email.processing.rest.EmailProcessingRestTemplate;
import com.polygor.email.processing.rest.EmailProcessingRestTemplateBuilder;
import org.springframework.context.annotation.Bean;

import java.time.Duration;


public class EmailProcessingRestTemplateConfig {

    private static final int CONNECT_TIMEOUT = 5;
    private static final int READ_TIMEOUT = 5;

    @Bean
    public EmailProcessingRestTemplate restTemplate(EmailProcessingRestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT))
                .setReadTimeout(Duration.ofSeconds(READ_TIMEOUT))
                .build(EmailProcessingRestTemplate.class); }
}
