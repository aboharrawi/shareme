package com.mharawi.shareme.config;

import com.mharawi.shareme.actuate.FileSystemHttpTraceRepository;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import java.io.File;

@Configuration
public class WebConfiguration {

    @Bean
    public HttpTraceRepository httpTraceRepository() {
        return new FileSystemHttpTraceRepository(new File("the_source_dir"));
    }

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