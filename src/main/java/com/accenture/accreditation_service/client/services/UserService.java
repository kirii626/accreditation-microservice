package com.accenture.accreditation_service.client.services;

import com.accenture.accreditation_service.client.dtos.UserDtoIdUsernameEmail;

public interface UserService {

    UserDtoIdUsernameEmail getUserById(Long userId);
}
