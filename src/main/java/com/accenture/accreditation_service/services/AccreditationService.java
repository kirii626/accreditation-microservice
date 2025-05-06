package com.accenture.accreditation_service.services;

import com.accenture.accreditation_service.dtos.AccreditationDtoInput;
import com.accenture.accreditation_service.dtos.AccreditationDtoOutput;
import com.accenture.accreditation_service.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AccreditationService {

    public AccreditationDtoOutput createAccreditation(HttpServletRequest httpServletRequest, AccreditationDtoInput accreditationDtoInput);

    public List<AccreditationDtoOutput> allAccreditations(HttpServletRequest httpServletRequest);
}
