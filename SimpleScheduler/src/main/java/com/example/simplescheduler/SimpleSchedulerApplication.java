package com.example.simplescheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SimpleSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleSchedulerApplication.class, args);
    }

}
