package com.mharawi.shareme.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

@Configuration
public class WebConfiguration {

    @Bean
    @RequestScope
    @Primary
    UriBuilder uriBuilder(UriBuilderFactory factory) {
        return factory.builder();
    }

    @Bean
    DefaultUriBuilderFactory uriBuilderFactory() {
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        return defaultUriBuilderFactory;
    }
}