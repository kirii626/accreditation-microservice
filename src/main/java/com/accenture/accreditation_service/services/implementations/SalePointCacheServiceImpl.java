package com.accenture.accreditation_service.services.implementations;

import com.accenture.accreditation_service.client.SalePointClient;
import com.accenture.accreditation_service.client.dtos.SalePointDtoOutput;
import com.accenture.accreditation_service.services.SalePointCacheService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SalePointCacheServiceImpl implements SalePointCacheService {

    private final SalePointClient salePointClient;

    public SalePointCacheServiceImpl(SalePointClient salePointClient) {
        this.salePointClient = salePointClient;
    }

    @Override
    @Cacheable(value = "salePointById", key = "#salePointId")
    public SalePointDtoOutput getSalePointById(Long salePointId) {
        return salePointClient.getSalePointById(salePointId);
    }
}
