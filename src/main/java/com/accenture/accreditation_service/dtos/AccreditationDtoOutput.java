package com.accenture.accreditation_service.dtos;

import java.time.LocalDateTime;

public class AccreditationDtoOutput {

    private Long accreditationId;

    private Float amount;

    private LocalDateTime receivedAt;

    private Long salePointId;

    private String nameSalePoint;

    public AccreditationDtoOutput() {
    }

    public AccreditationDtoOutput(Long accreditationId, Float amount, LocalDateTime receivedAt, Long salePointId, String nameSalePoint) {
        this.accreditationId = accreditationId;
        this.amount = amount;
        this.receivedAt = receivedAt;
        this.salePointId = salePointId;
        this.nameSalePoint = nameSalePoint;
    }

    public Long getAccreditationId() {
        return accreditationId;
    }

    public Float getAmount() {
        return amount;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public Long getSalePointId() {
        return salePointId;
    }

    public String getNameSalePoint() {
        return nameSalePoint;
    }
}
