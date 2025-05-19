package com.accenture.accreditation_service.client.services.implementations;

import com.accenture.accreditation_service.client.SalePointClient;
import com.accenture.accreditation_service.client.dtos.SalePointDtoOutput;
import com.accenture.accreditation_service.client.services.SalePointCacheService;
import com.accenture.accreditation_service.exceptions.InternalServerErrorException;
import com.accenture.accreditation_service.exceptions.SalePointNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Service
public class SalePointCacheServiceImpl implements SalePointCacheService {

    private final RedisTemplate<String, SalePointDtoOutput> salePointRedisTemplate;
    private final SalePointClient salePointClient;

    @Override
    public SalePointDtoOutput getSalePointById(Long salePointId) {
        log.info("Starting to search sale point with salePointId {}", salePointId);

        try {
            String key = "salePoint:" + salePointId;
            SalePointDtoOutput cached = salePointRedisTemplate.opsForValue().get(key);

            if (cached != null) return cached;

            SalePointDtoOutput fromService = salePointClient.getSalePointById(salePointId);
            salePointRedisTemplate.opsForValue().set(key, fromService, Duration.ofMinutes(10)); // TTL

            log.info("Sale point with ID {} was founded", salePointId);
            return fromService;
        } catch (SalePointNotFoundException ex) {
            log.error("Sale point not found: {}", salePointId, ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error fetching sale point {}", salePointId, ex);
            throw new InternalServerErrorException("Unexpected error during sale point search", ex);
        }
    }
}
