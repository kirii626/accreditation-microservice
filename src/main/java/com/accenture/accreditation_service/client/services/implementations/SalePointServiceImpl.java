package com.accenture.accreditation_service.client.services.implementations;

import com.accenture.accreditation_service.client.dtos.SalePointDtoOutput;
import com.accenture.accreditation_service.client.services.SalePointCacheService;
import com.accenture.accreditation_service.client.services.SalePointService;
import com.accenture.accreditation_service.exceptions.InternalServerErrorException;
import com.accenture.accreditation_service.exceptions.SalePointNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Slf4j
public class SalePointServiceImpl implements SalePointService {

    private final SalePointCacheService cacheService;

    @Override
    public SalePointDtoOutput getSalePointById(Long salePointId) {
        log.info("Starting search of sale point with ID {}", salePointId);

        try {
            log.info("Sale point with ID {} fetched successfully", salePointId);
            return cacheService.getSalePointById(salePointId);
        } catch (SalePointNotFoundException ex) {
            log.error("Sale point with ID {} not found", salePointId, ex);
            throw new SalePointNotFoundException(salePointId);
        } catch (Exception ex) {
            log.error("Unexpected error during sale point search with ID {}", salePointId);
            throw new InternalServerErrorException("Unexpected error during sale point search", ex);
        }
    }
}
