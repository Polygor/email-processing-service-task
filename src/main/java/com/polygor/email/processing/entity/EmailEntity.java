package com.polygor.email.processing.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "email")
public class EmailEntity extends AbstractEntity {

    @Getter
    @Setter
    @Column(name = "email")
    private String email;

    @Getter
    @Setter
    @Column(name = "encountered")
    private Long encounteredCount;

    @Column(name = "creationDate", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Getter
    @Setter
    private ZonedDateTime creationDate = ZonedDateTime.now();

    public void increaseEncounteredCount(Long encounteredCount) {
        this.encounteredCount = this.encounteredCount + encounteredCount;
    }

    public EmailEntity(String email) {
        this.email = email;
    }

    public EmailEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailEntity that = (EmailEntity) o;

        if (!Objects.equals(email, that.email)) return false;
        if (!Objects.equals(encounteredCount, that.encounteredCount))
            return false;
        return Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (encounteredCount != null ? encounteredCount.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }
}
