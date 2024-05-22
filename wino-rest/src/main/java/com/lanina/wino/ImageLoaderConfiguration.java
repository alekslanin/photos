package com.lanina.wino;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class ImageLoaderConfiguration {

    @Bean
    public ImageLoader getImageLoader() {
        return new ImageLoader();
    }

    public static class ImageLoader {

    }
}
