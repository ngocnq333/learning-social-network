package com.solution.ntq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = {"com.solution.ntq.model"})
@SpringBootApplication(scanBasePackages = {"com.solution"})
public class NtqApplication {

    public static void main(String[] args) {
        SpringApplication.run(NtqApplication.class, args);
    }

}
