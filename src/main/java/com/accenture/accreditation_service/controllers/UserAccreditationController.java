package com.accenture.accreditation_service.controllers;

import com.accenture.accreditation_service.dtos.AccreditationDtoInput;
import com.accenture.accreditation_service.dtos.AccreditationDtoOutput;
import com.accenture.accreditation_service.services.AccreditationService;
import com.accenture.accreditation_service.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accreditation/user")
public class UserAccreditationController {

    private final AccreditationService accreditationService;

    @PostMapping("/create")
    public ApiResponse<AccreditationDtoOutput> createAccreditation(@Valid @RequestBody AccreditationDtoInput accreditationDtoInput) {
        AccreditationDtoOutput accreditationDtoOutput = accreditationService.createAccreditation(accreditationDtoInput);

        return new ApiResponse<>(
                "Order Received",
                accreditationDtoOutput
        );
    }
}
