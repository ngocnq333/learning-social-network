package com.solution.ntq;

import com.solution.ntq.service.base.IClazzService;
import com.solution.ntq.service.impl.ClazzServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.text.ParseException;

@EntityScan(basePackages = {"com.solution.ntq.model"})
@SpringBootApplication(scanBasePackages = {"com.solution"})
@AllArgsConstructor
public class NtqApplication {

    public static void main(String[] args) {
        SpringApplication.run(NtqApplication.class, args);


    }

}
