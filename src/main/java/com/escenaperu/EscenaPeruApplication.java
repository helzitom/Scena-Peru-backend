package com.escenaperu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EscenaPeruApplication {
    public static void main(String[] args) {
        SpringApplication.run(EscenaPeruApplication.class, args);
    }
}
