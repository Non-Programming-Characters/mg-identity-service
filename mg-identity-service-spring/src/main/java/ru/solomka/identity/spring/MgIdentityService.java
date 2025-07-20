package ru.solomka.identity.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.solomka.identity")
public class MgIdentityService {

    public static void main(String[] args) {
        SpringApplication.run(MgIdentityService.class, args);
    }
}