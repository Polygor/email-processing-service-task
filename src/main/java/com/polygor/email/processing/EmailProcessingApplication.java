package com.polygor.email.processing;

import com.polygor.email.processing.logging.LoggingInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Collections;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2
public class EmailProcessingApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(EmailProcessingApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(getClass().getPackage().getName()))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(generateApiInfo());
    }


    private ApiInfo generateApiInfo() {
        return new ApiInfo("Email Processing service", "Email Processing service task", "1.0",
                "Terms of service", new Contact("Igor Polchshikov", "https://www.linkedin.com/in/igor-polchshikov-315b83b0/", "polygor92@gmail.com"), "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", Collections.singletonList(new VendorExtension() {
            @Override
            public String getName() {
                return "email processing application name";
            }

            @Override
            public Object getValue() {
                return "email processing application value";
            }
        }));
    }
}