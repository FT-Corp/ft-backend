package com.spring.ftbackend.common.config;



import org.springframework.context.ApplicationContext;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Component
public class TimeAuditorAware implements DateTimeProvider {
    private final ApplicationContext applicationContext;

    public TimeAuditorAware(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    public Optional<TemporalAccessor> getNow(){
        return Optional.of(ZonedDateTime.now());
    }

}
