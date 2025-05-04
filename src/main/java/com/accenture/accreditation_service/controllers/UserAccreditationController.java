package com.accenture.accreditation_service.controllers;

import com.accenture.accreditation_service.client.dtos.UserDtoIdUsernameEmail;
import com.accenture.accreditation_service.dtos.AccreditationDtoInput;
import com.accenture.accreditation_service.dtos.AccreditationDtoOutput;
import com.accenture.accreditation_service.services.AccreditationService;
import com.accenture.accreditation_service.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-accreditation")
public class UserAccreditationController {

    private final AccreditationService accreditationService;

    @PostMapping("/create")
    public ApiResponse<AccreditationDtoOutput> createAccreditation(HttpServletRequest httpServletRequest, @RequestBody AccreditationDtoInput accreditationDtoInput) {
        return accreditationService.createAccreditation(httpServletRequest, accreditationDtoInput);
    }
}
