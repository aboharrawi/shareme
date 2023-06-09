package com.mharawi.shareme.domain;

import com.mharawi.shareme.config.SharingDomainsConfiguration;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Service
public class SharingDomainBuilder {

    private final DefaultUriBuilderFactory uriBuilder;
    private final SharingDomainsConfiguration properties;

    public SharingDomainBuilder(DefaultUriBuilderFactory uriBuilder, SharingDomainsConfiguration properties) {
        this.uriBuilder = uriBuilder;
        this.properties = properties;
    }

    @Nullable
    public URI build(String name, Map<String, ?> context) {
        String domain = properties.getDomains().get(name);
        if (domain != null) {
            return UriComponentsBuilder.fromUriString(domain)
                    .build()
                    .expand(new OptionalMapTemplateVariables(context))
                    .toUri();
        }
        return null;
    }

    private static class OptionalMapTemplateVariables implements UriComponents.UriTemplateVariables {

        private final Map<String, ?> uriVariables;

        public OptionalMapTemplateVariables(Map<String, ?> uriVariables) {
            this.uriVariables = uriVariables;
        }

        @Override
        public Object getValue(String name) {
            if (uriVariables.containsKey(name)) {
                return uriVariables.get(name);
            }
            return null;
        }
    }
}
