package com.accenture.accreditation_service.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AccreditationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accreditationId;

    @Column(nullable = false)
    private Float amount;

    @Column(nullable = false)
    private LocalDateTime receivedAt;

    @Column(nullable = false)
    private Long salePointId;

    @Column(nullable = false)
    private String nameSalePoint;

    public AccreditationEntity() {
    }

    public AccreditationEntity(Float amount, LocalDateTime receivedAt, Long salePointId, String nameSalePoint) {
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

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public Long getSalePointId() {
        return salePointId;
    }

    public void setSalePointId(Long salePointId) {
        this.salePointId = salePointId;
    }

    public String getNameSalePoint() {
        return nameSalePoint;
    }

    public void setNameSalePoint(String nameSalePoint) {
        this.nameSalePoint = nameSalePoint;
    }

    @Override
    public String toString() {
        return "AccreditationEntity{" +
                "accreditationId=" + accreditationId +
                ", amount=" + amount +
                ", salePointId=" + salePointId +
                ", nameSalePoint='" + nameSalePoint + '\'' +
                '}';
    }
}
