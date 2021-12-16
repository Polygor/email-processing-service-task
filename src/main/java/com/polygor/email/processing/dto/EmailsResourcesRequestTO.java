package com.polygor.email.processing.dto;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class EmailsResourcesRequestTO {

    @Getter
    @Setter
    private List<String> emails = new ArrayList<>();

    @Getter
    @Setter
    private List<String> resources = new ArrayList<>();


}
