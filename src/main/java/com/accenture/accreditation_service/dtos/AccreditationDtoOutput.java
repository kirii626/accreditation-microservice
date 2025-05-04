package com.accenture.accreditation_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccreditationDtoOutput {

    private Long accreditationId;

    private Float amount;

    private LocalDateTime receivedAt;

    private Long salePointId;

    private String nameSalePoint;

    private Long userId;

    private String username;

    private String email;

}
