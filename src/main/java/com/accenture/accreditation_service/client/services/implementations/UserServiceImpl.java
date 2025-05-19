package com.accenture.accreditation_service.client.services.implementations;

import com.accenture.accreditation_service.client.UserClient;
import com.accenture.accreditation_service.client.dtos.UserDtoIdUsernameEmail;
import com.accenture.accreditation_service.client.services.UserService;
import com.accenture.accreditation_service.exceptions.InternalServerErrorException;
import com.accenture.accreditation_service.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserClient userClient;

    @Override
    public UserDtoIdUsernameEmail getUserById(Long userId) {
        log.info("Starting search of user with ID {}", userId);

        try {
            UserDtoIdUsernameEmail user = userClient.getUserById(userId);
            log.info("User with ID {} fetched", userId);
            return user;
        } catch (UserNotFoundException ex) {
            log.error("User with ID {} not found", userId, ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error during user search with ID {}", userId);
            throw new InternalServerErrorException("Unexpected error during user search", ex);
        }
    }
}
