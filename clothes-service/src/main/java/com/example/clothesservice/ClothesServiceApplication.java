package com.example.clothesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.example.clothesservice", "com.example.common"})
public class ClothesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClothesServiceApplication.class, args);
    }

}
