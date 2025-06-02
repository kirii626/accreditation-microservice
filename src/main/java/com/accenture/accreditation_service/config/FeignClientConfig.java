package com.accenture.accreditation_service.config;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FeignClientConfig {

    @Value("${internal.secret.token}")
    private String internalToken;

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomFeignErrorDecoder();
    }

    @Bean
    public RequestInterceptor internalRequestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("X-Internal-Token", internalToken);
            log.info("Adding internal token to request: {}", internalToken);
        };
    }
}
