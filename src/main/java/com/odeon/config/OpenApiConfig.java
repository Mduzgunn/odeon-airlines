package com.odeon.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI odeonAirlinesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Odeon Airlines API")
                        .description("Odeon Airlines Case Study REST API Documentation")
                        .version("1.0"));
    }
} 
