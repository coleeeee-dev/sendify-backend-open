package com.sendify.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SendifyPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(SendifyPlatformApplication.class, args);
    }
}
