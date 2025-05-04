package com.accenture.accreditation_service.services;

import com.accenture.accreditation_service.client.dtos.UserDtoIdUsernameEmail;
import com.accenture.accreditation_service.dtos.AccreditationDtoInput;

public interface UserService {

    UserDtoIdUsernameEmail getUserById(Long userId);
}
