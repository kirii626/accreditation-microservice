package com.accenture.accreditation_service.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
@Entity
public class AccreditationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accreditationId;

    @Setter
    @Column(nullable = false)
    private Float amount;

    @Setter
    @Column(nullable = false)
    private LocalDateTime receivedAt;

    @Setter
    @Column(nullable = false)
    private Long salePointId;

    @Setter
    @Column(nullable = false)
    private String nameSalePoint;

    @Setter
    @Column(nullable = false)
    private Long userId;

    @Setter
    @Column(nullable = false)
    private String username;

    @Setter
    @Column(nullable = false)
    private String email;

    public AccreditationEntity() {
    }

    public AccreditationEntity(Float amount, LocalDateTime receivedAt, Long salePointId, String nameSalePoint, Long userId, String username, String email) {
        this.amount = amount;
        this.receivedAt = receivedAt;
        this.salePointId = salePointId;
        this.nameSalePoint = nameSalePoint;
        this.userId = userId;
        this.username = username;
        this.email = email;
    }
}
