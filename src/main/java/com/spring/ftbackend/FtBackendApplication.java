package com.spring.ftbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class FtBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FtBackendApplication.class, args);
    }

}
