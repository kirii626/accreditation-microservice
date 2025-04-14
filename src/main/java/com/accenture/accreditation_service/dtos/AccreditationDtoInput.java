package com.accenture.accreditation_service.dtos;

import java.time.LocalDateTime;

public class AccreditationDtoInput {

    private Float amount;

    private Long salePointId;

    public AccreditationDtoInput() {
    }

    public AccreditationDtoInput(Float amount, Long salePointId) {
        this.amount = amount;
        this.salePointId = salePointId;
    }

    public Float getAmount() {
        return amount;
    }

    public Long getSalePointId() {
        return salePointId;
    }
}
