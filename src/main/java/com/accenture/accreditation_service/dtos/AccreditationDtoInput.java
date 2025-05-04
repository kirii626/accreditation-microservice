package com.accenture.accreditation_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccreditationDtoInput {

    private Float amount;

    private Long salePointId;

    private Long userId;

    private String username;

    private String email;

}
