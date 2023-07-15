package com.example.inobao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class InobaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(InobaoApplication.class, args);
    }

}
