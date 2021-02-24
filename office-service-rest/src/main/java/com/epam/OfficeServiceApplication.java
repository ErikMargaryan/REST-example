package com.epam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.epam")
public class OfficeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficeServiceApplication.class, args);
    }

}
