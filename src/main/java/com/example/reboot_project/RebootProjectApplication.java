package com.example.reboot_project;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableEncryptableProperties
@EnableScheduling
public class RebootProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(RebootProjectApplication.class, args);
    }
}
