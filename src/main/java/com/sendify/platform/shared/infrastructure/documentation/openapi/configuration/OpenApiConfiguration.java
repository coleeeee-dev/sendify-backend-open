package com.sendify.platform.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI sendifyOpenAPI() {
        Server server = new Server();
        // URL relativa: usa el mismo dominio y http/https que la página donde se carga Swagger
        server.setUrl("/");

        return new OpenAPI()
                .info(new Info()
                        .title("Sendify Platform API")
                        .description("REST API for Sendify shipping, tracking and user management.")
                        .version("v1.0.0"))
                .servers(List.of(server));
    }

    @Bean
    public GroupedOpenApi v1Api() {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/api/v1/**")
                .build();
    }
}
