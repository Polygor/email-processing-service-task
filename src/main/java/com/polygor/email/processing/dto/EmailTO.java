package com.polygor.email.processing.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

public class EmailTO implements Serializable {

    private static final long serialVersionUID = 27867584921111L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private Long encounteredCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailTO to = (EmailTO) o;

        if (!Objects.equals(id, to.id)) return false;
        if (!Objects.equals(email, to.email)) return false;
        return Objects.equals(encounteredCount, to.encounteredCount);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (encounteredCount != null ? encounteredCount.hashCode() : 0);
        return result;
    }
}
