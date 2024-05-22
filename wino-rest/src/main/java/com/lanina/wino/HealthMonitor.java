package com.lanina.wino;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.CompositeHealth;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class HealthMonitor {

    private final HealthEndpoint health;

    @Scheduled(fixedDelayString = "600000", initialDelayString = "100000")
    public void check() {
        if(!Status.UP.equals(health.health().getStatus())) {
            if(health.health() instanceof CompositeHealth actuator) {
                actuator.getComponents().forEach((key, value) -> {
                    if(!Status.UP.equals(value.getStatus())) {
                        log.error("component name " + key + " status :: " + value.getStatus().getCode());
                    }
                });
            } else {
                log.error("Application health is " + health.health().getStatus().getCode());
            }
        } else {
            log.info("Application health is " + health.health().getStatus().getCode());
        }

    }
}
