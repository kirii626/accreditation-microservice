package com.accenture.accreditation_service.client.services.implementations;

import com.accenture.accreditation_service.client.dtos.SalePointDtoOutput;
import com.accenture.accreditation_service.client.services.SalePointCacheService;
import com.accenture.accreditation_service.client.services.SalePointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SalePointServiceImpl implements SalePointService {

    private final SalePointCacheService cacheService;

    @Override
    public SalePointDtoOutput getSalePointById(Long salePointId) {
        return cacheService.getSalePointById(salePointId);
    }
}
