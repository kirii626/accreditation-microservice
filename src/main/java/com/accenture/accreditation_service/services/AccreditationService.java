package com.accenture.accreditation_service.services;

import com.accenture.accreditation_service.dtos.AccreditationDtoInput;
import com.accenture.accreditation_service.dtos.AccreditationDtoOutput;

import java.util.ArrayList;

public interface AccreditationService {

    public AccreditationDtoOutput createAccreditation(AccreditationDtoInput accreditationDtoInput);

    public ArrayList<AccreditationDtoOutput> allAccreditations();
}
