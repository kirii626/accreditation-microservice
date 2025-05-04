package com.accenture.accreditation_service.services.implementations;

import com.accenture.accreditation_service.client.SalePointClient;
import com.accenture.accreditation_service.client.dtos.SalePointDtoOutput;
import com.accenture.accreditation_service.services.SalePointService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SalePointServiceImpl implements SalePointService {

    private final SalePointClient salePointClient;

    @Override
    @Cacheable(value = "salePointById", key = "#salePointId")
    public SalePointDtoOutput getSalePointById(Long salePointId) {
        return salePointClient.getSalePointById(salePointId);
    }
}
