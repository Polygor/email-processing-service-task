package com.polygor.email.processing.dto.mapper;

import com.polygor.email.processing.dto.EmailTO;
import com.polygor.email.processing.entity.EmailEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailMapper {

    public Page<EmailTO> mapToDto(Page<EmailEntity> emailEntities) {
        return emailEntities.map(this::mapToDto);
    }

    public Page<EmailEntity> mapToEntity(Page<EmailTO> emailDtos) {
        return emailDtos.map(this::mapToEntity);
    }

    public EmailTO mapToDto(EmailEntity emailEntity) {
        EmailTO to = new EmailTO();
        if (emailEntity != null) {
            to.setId(emailEntity.getId());
            to.setEmail(emailEntity.getEmail());
            to.setEncounteredCount(emailEntity.getEncounteredCount());
        }
        return to;
    }

    public EmailEntity mapToEntity(EmailTO emailTO) {
        EmailEntity entity = new EmailEntity();
        if (emailTO != null) {
            entity.setId(emailTO.getId());
            entity.setEmail(emailTO.getEmail());
            entity.setEncounteredCount(emailTO.getEncounteredCount());
        }
        return entity;
    }
}