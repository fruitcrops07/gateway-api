package com.fruitcrops07.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                                                      .apis(RequestHandlerSelectors.basePackage("com.fruitcrops07.gateway"))
                                                      .build()
                                                      .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Gateway API")
                                   .description("API for list of Gateways and Peripherals")
                                   .version("v1")
                                   .license("n/a")
                                   .licenseUrl("n/a")
                                   .contact(new Contact("marx macalalag", "https://github.com/fruitcrops07", "marx.macalalag@gmail.com"))
                                   .build();
    }
}
