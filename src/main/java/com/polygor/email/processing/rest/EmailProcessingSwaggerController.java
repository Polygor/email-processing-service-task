package com.polygor.email.processing.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
@RequiredArgsConstructor
public class EmailProcessingSwaggerController {

    private static final String SWAGGER_UI_HTML = "redirect:swagger-ui.html";

    @RequestMapping("/")
    public String redirectToSwagger() {
        return "redirect:swagger-ui.html";
    }
}
