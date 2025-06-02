package com.accenture.accreditation_service.controllers;

import com.accenture.accreditation_service.dtos.AccreditationDtoOutput;
import com.accenture.accreditation_service.services.AccreditationService;
import com.accenture.accreditation_service.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accreditation/admin")
public class AdminAccreditationController {

    private final AccreditationService accreditationService;

    @GetMapping("/all")
    public ApiResponse<ArrayList<AccreditationDtoOutput>> getAllAccreditations() {
        ArrayList<AccreditationDtoOutput> accreditationDtoOutputList = accreditationService.allAccreditations();

        return new ApiResponse<>(
                "All orders: ",
                accreditationDtoOutputList
        );
    }
}
