package com.globalagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GlobalAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlobalAgentApplication.class, args);
    }
}
