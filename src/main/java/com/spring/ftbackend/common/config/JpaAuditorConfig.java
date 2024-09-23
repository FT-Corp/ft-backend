package com.spring.ftbackend.common.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(
        dateTimeProviderRef = "timeAuditorAware"
)
@Configuration
public class JpaAuditorConfig {
}
