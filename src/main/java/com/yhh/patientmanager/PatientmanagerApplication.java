package com.yhh.patientmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.yhh.patientmanager.mapper")
@SpringBootApplication()
public class PatientmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientmanagerApplication.class, args);
    }

}
