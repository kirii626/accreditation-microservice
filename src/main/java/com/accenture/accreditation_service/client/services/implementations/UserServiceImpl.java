package com.accenture.accreditation_service.client.services.implementations;

import com.accenture.accreditation_service.client.UserClient;
import com.accenture.accreditation_service.client.dtos.UserDtoIdUsernameEmail;
import com.accenture.accreditation_service.client.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserClient userClient;

    @Override
    public UserDtoIdUsernameEmail getUserById(Long userId) {
        return userClient.getUserById(userId);
    }
}
