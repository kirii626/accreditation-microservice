package com.accenture.accreditation_service.controllers;

import com.accenture.accreditation_service.dtos.AccreditationDtoInput;
import com.accenture.accreditation_service.dtos.AccreditationDtoOutput;
import com.accenture.accreditation_service.services.AccreditationService;
import com.accenture.accreditation_service.utils.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accreditations")
public class AccreditationController {

    private final AccreditationService accreditationService;

    public AccreditationController(AccreditationService accreditationService) {
        this.accreditationService = accreditationService;
    }

    @GetMapping("/all")
    public ApiResponse<List<AccreditationDtoOutput>> getAllAccreditations() {
        return accreditationService.allAccreditations();
    }

    @PostMapping("/create")
    public ApiResponse<AccreditationDtoOutput> createAccreditation(@RequestBody AccreditationDtoInput accreditationDtoInput) {
        return accreditationService.createAccreditation(accreditationDtoInput);
    }
}
