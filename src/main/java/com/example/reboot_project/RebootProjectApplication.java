package com.example.reboot_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RebootProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(RebootProjectApplication.class, args);
    }

}
