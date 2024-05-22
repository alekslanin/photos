package com.lanina.wino;

import org.javatuples.Pair;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Configuration
public class MockPhotoServiceConfiguration {

    @Bean
    @ConditionalOnMissingBean(ITravelPhotoService.class)
    public ITravelPhotoService getTravelPhotoService() {
        return new MockTravelPhotoService();
    }

    public static class MockTravelPhotoService implements ITravelPhotoService {

        /**
         * @return
         */
        @Override
        public List<MetaLocation> getLocations() {
            return null;
        }

        /**
         * @return
         */
        @Override
        public Long countLocations() {
            return null;
        }

        /**
         * @param page
         * @param size
         * @return
         * @throws HttpClientErrorException.NotAcceptable
         */
        @Override
        public List<MetaLocation> getLocations(int page, int size) throws HttpClientErrorException.NotAcceptable {
            return null;
        }

        /**
         * @param id
         * @return
         */
        @Override
        public MetaLocation getLocationById(String id) {
            return null;
        }

        /**
         * @param location
         * @param id
         * @return
         */
        @Override
        public MetaImage getImageById(String location, Integer id) {
            return null;
        }

        /**
         * @return
         */
        @Override
        public Pair<Long, Integer> imagesStat() {
            return null;
        }
    }
}
