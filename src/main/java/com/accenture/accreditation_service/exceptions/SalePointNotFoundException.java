package com.accenture.accreditation_service.exceptions;


public class SalePointNotFoundException extends ResourceNotFoundException {
    public SalePointNotFoundException(Long salePointId) {
        super("Sale point with ID "+salePointId+" not found");
    }

    public SalePointNotFoundException(Long salePointId, Throwable throwable) {
        super("Sale point with ID "+salePointId+" not found",throwable);
    }
}
