package com.accenture.accreditation_service.controllers;

import com.accenture.accreditation_service.dtos.AccreditationDtoInput;
import com.accenture.accreditation_service.dtos.AccreditationDtoOutput;
import com.accenture.accreditation_service.services.AccreditationService;
import com.accenture.accreditation_service.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin-accreditation")
public class AdminAccreditationController {

    private final AccreditationService accreditationService;

    @GetMapping("/all")
    public ApiResponse<List<AccreditationDtoOutput>> getAllAccreditations(HttpServletRequest httpServletRequest) {
        return accreditationService.allAccreditations(httpServletRequest);
    }
}
