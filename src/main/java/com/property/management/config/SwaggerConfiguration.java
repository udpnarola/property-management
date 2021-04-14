package com.property.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(true)
                .globalOperationParameters(globalParameterList())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfo(
                "Property Management",
                "Property Management Demo With Spring Boot",
                "1.0",
                "",
                new Contact("Narola Infotech", "https://www.narolainfotech.com/", "info@narola.email"),
                "",
                "",
                Collections.emptyList());
    }

    private List<Parameter> globalParameterList() {
        Parameter authTokenHeader =
                new ParameterBuilder()
                        .name("api-key")
                        .modelRef(new ModelRef("string"))
                        .required(true)
                        .parameterType("header")
                        .description("Api key for authentication")
                        .build();

        return Collections.singletonList(authTokenHeader);
    }

}


