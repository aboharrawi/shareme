package com.mharawi.shareme.endpoint;

import com.mharawi.shareme.domain.SharingDomainBuilder;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.Map;

@Controller
public class ShareLinkController {

    private final SharingDomainBuilder sharingDomainBuilder;

    public ShareLinkController(SharingDomainBuilder sharingDomainBuilder) {
        this.sharingDomainBuilder = sharingDomainBuilder;
    }

    @GetMapping("/share/{name}")
    public ResponseEntity<?> share(@PathVariable String name, @RequestParam Map<String, String> params) {
        URI target = sharingDomainBuilder.build(name, params);
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                .location(target)
                .cacheControl(CacheControl.noCache())
                .build();
    }
}
