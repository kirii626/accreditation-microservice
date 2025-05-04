package com.accenture.accreditation_service.services;

import com.accenture.accreditation_service.client.dtos.SalePointDtoOutput;

public interface SalePointService {

    public SalePointDtoOutput getSalePointById(Long salePointId);
}
