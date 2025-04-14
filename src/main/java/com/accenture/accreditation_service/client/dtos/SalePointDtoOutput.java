package com.accenture.accreditation_service.client.dtos;

public class SalePointDtoOutput {

    private Long salePointId;
    private String name;

    public SalePointDtoOutput() {
    }

    public SalePointDtoOutput(Long salePointId, String name) {
        this.salePointId = salePointId;
        this.name = name;
    }

    public Long getSalePointId() {
        return salePointId;
    }

    public String getName() {
        return name;
    }
}
