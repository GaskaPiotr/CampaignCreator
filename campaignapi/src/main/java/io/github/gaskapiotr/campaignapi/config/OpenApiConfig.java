package io.github.gaskapiotr.campaignapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Campaign Management API")
                        .version("1.0.0")
                        .description("REST API for managing seller product campaigns")
                        .contact(new Contact()
                                .name("Recruitment Task")
                                .email("piotr.gaska.dev@gmail.com")));
    }
}
