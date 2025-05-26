package com.accenture.accreditation_service.client.implementations;

import com.accenture.accreditation_service.client.UserClient;
import com.accenture.accreditation_service.client.dtos.UserDtoIdUsernameEmail;
import com.accenture.accreditation_service.client.services.implementations.UserServiceImpl;
import com.accenture.accreditation_service.exceptions.InternalServerErrorException;
import com.accenture.accreditation_service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserClient userClient;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDtoIdUsernameEmail userDto;

    @BeforeEach
    void setup() {
        userDto = new UserDtoIdUsernameEmail(1L, "username", "email@example.com");
    }

    @Test
    void getUserById_shouldReturnUser_whenFound() {
        when(userClient.getUserById(1L)).thenReturn(userDto);

        UserDtoIdUsernameEmail result = userService.getUserById(1L);

        assertEquals(userDto, result);
        verify(userClient).getUserById(1L);
    }

    @Test
    void getUserById_shouldThrowUserNotFoundException_whenNotFound() {
        when(userClient.getUserById(1L)).thenThrow(new UserNotFoundException(1L));

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void getUserById_shouldThrowInternalServerErrorException_whenUnexpectedError() {
        when(userClient.getUserById(1L)).thenThrow(new RuntimeException());

        assertThrows(InternalServerErrorException.class, () -> userService.getUserById(1L));
    }

}
