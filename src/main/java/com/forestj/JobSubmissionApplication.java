package com.forestj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JobSubmissionApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobSubmissionApplication.class,args);
    }
}
