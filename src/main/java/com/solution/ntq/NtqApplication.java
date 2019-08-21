package com.solution.ntq;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = {"com.solution.ntq.repository.entities"})
@SpringBootApplication(scanBasePackages = {"com.solution"})
@AllArgsConstructor
public class NtqApplication {

    public static void main(String[] args)  {
        SpringApplication.run(NtqApplication.class, args);


    }

}
