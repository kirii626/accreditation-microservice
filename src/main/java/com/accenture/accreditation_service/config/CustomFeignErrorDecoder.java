package com.accenture.accreditation_service.config;

import com.accenture.accreditation_service.exceptions.ForbiddenAccessException;
import com.accenture.accreditation_service.exceptions.SalePointNotFoundException;
import com.accenture.accreditation_service.exceptions.UserNotFoundException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        log.info("Starting decoding");
        int status = response.status();
        String body = "";
        try {
            body = response.body() != null ? Util.toString(response.body().asReader(StandardCharsets.UTF_8)) : "";
        } catch (Exception e) {
            log.error("Unexpected error while decoding exception of synchronous call");
        }

        if (status == 404) {
            if (methodKey.contains("SalePointClient#getSalePointById")) {
                Long id = extractIdFromPath(methodKey);
                return new SalePointNotFoundException(id);
            } else if (methodKey.contains("UserClient#getUserById")) {
                Long userId = extractIdFromUrl(response.request().url());
                return new UserNotFoundException(userId);
            }
        }

        if (status == 403) {
            return new ForbiddenAccessException();
        }

        log.debug("Feign methodKey: {} | {}", methodKey, response);
        return defaultDecoder.decode(methodKey, response);
    }

    private Long extractIdFromPath(String methodKey) {
        return -1L;
    }

    private Long extractIdFromUrl(String url) {
        try {
            String[] parts = url.split("/");
            return Long.parseLong(parts[parts.length - 1]);
        } catch (Exception e) {
            return -1L;
        }
    }
}
