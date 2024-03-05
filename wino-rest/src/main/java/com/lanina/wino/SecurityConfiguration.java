package com.lanina.wino;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@Slf4j
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean public CorsConfigurationSource corsConfigurationSource() {
        log.info("++++ CorsConfigurationSource +++");
        var source = new UrlBasedCorsConfigurationSource();
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("++++ SecurityFilterChain +++");
        return http
                //.cors(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .csrf(x -> x.disable())
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
