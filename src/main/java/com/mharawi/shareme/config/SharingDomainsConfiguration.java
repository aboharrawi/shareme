package com.mharawi.shareme.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties("share-me")
public class SharingDomainsConfiguration {

    Map<String, String> domains;

    public void setDomains(Map<String, String> domains) {
        this.domains = domains;
    }

    public Map<String, String> getDomains() {
        return domains;
    }
}