package com.sendify.platform.shared.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // Orígenes permitidos (añadiremos el del frontend deployado luego)
                .allowedOrigins(
                        "http://localhost:4200",        // Frontend Angular en local
                        "https://sendify-front.onrender.com" // Reemplazar cuando tengas el dominio real del front
                )
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
