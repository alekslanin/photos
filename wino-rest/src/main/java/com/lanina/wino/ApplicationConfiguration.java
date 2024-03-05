package com.lanina.wino;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfiguration {

    @Configuration
    public static class WinoWebMvcConfigurer implements WebMvcConfigurer {
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addRedirectViewController("/swagger", "/swagger-ui.html").setStatusCode(HttpStatus.PERMANENT_REDIRECT);
        }
    }
}
