package com.solucionestecnologicas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
    "com.solucionestecnologicas",
    "controller",
    "service"
})
@EntityScan(basePackages = "domain")
@EnableJpaRepositories(basePackages = "dao")
public class SolucionestecnologicasApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolucionestecnologicasApplication.class, args);
    }

}
