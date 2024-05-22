package com.lanina.wino;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Value("${wino.cache.initial-capacity:1000}")
    private int initialCapacity;

    @Value("30")
    private int expirationTime;


    public static final String ONE = "rules";
    public static final String TWO = "two";

    @Bean
    public CacheManager cacheManager() {
        var manager = new CaffeineCacheManager(ONE, TWO);
        manager.setCaffeine(cacheBuilder());
        return manager;
    }

    private Caffeine<Object, Object> cacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .expireAfterWrite(expirationTime, TimeUnit.MINUTES)
                .recordStats();
    }
}
