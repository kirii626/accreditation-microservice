package com.accenture.accreditation_service.services;

import com.accenture.accreditation_service.dtos.AccreditationDtoInput;
import com.accenture.accreditation_service.dtos.AccreditationDtoOutput;
import com.accenture.accreditation_service.utils.ApiResponse;

import java.util.List;

public interface AccreditationService {

    public ApiResponse<AccreditationDtoOutput> createAccreditation(AccreditationDtoInput accreditationDtoInput);

    public ApiResponse<List<AccreditationDtoOutput>> allAccreditations();
}
