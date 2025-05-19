package com.accenture.accreditation_service.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccreditationDtoInput {

    @Positive(message = "The amount input must be positive")
    private Float amount;

    @NotNull(message = "The Sale Point ID can't be null")
    private Long salePointId;

    @NotNull(message = "The User ID can't be null")
    private Long userId;

    @NotBlank(message = "The username can't be null")
    private String username;

    @NotBlank(message = "The email can't be null")
    @Email(message = "Invalid email format")
    private String email;

}
