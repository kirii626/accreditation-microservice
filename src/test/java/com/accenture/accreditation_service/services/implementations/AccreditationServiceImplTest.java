package com.accenture.accreditation_service.services.implementations;

import com.accenture.accreditation_service.client.dtos.SalePointDtoOutput;
import com.accenture.accreditation_service.client.dtos.UserDtoIdUsernameEmail;
import com.accenture.accreditation_service.client.services.SalePointService;
import com.accenture.accreditation_service.client.services.UserService;
import com.accenture.accreditation_service.dtos.AccreditationDtoInput;
import com.accenture.accreditation_service.dtos.AccreditationDtoOutput;
import com.accenture.accreditation_service.events.publishers.AccreditationEventPublisher;
import com.accenture.accreditation_service.exceptions.InternalServerErrorException;
import com.accenture.accreditation_service.exceptions.SalePointNotFoundException;
import com.accenture.accreditation_service.exceptions.UserNotFoundException;
import com.accenture.accreditation_service.models.AccreditationEntity;
import com.accenture.accreditation_service.repositories.AccreditationRepository;
import com.accenture.accreditation_service.services.mappers.AccreditationMapper;
import com.accenture.accreditation_service.services.validations.ValidRoleType;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccreditationServiceImplTest {

    @Mock
    private AccreditationMapper accreditationMapper;

    @Mock
    private AccreditationRepository accreditationRepository;

    @Mock
    private SalePointService salePointService;

    @Mock
    private UserService userService;

    @Mock
    private ValidRoleType validRoleType;

    @Mock
    private AccreditationEventPublisher accreditationEventPublisher;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AccreditationServiceImpl accreditationService;

    private AccreditationDtoInput dtoInput;
    private SalePointDtoOutput salePointDto;
    private UserDtoIdUsernameEmail userDto;
    private AccreditationEntity entity;
    private AccreditationDtoOutput dtoOutput;

    @BeforeEach
    void setup() {
        dtoInput = new AccreditationDtoInput(1020F, 1L, 1L, "username", "email@example.com");
        salePointDto = new SalePointDtoOutput(1L, "Point A");
        userDto = new UserDtoIdUsernameEmail(1L, "username", "email@example.com");
        entity = new AccreditationEntity();
        dtoOutput = new AccreditationDtoOutput(1L, 1020F, LocalDateTime.now(), 1L, "Point A", 1L, "username", "email@example.com");
    }

    @Test
    void createAccreditation_shouldReturnOutput_whenSuccess() {
        doNothing().when(validRoleType).validateUserRole(request);
        when(salePointService.getSalePointById(1L)).thenReturn(salePointDto);
        when(userService.getUserById(1L)).thenReturn(userDto);
        when(accreditationMapper.toEntity(dtoInput, salePointDto, userDto)).thenReturn(entity);
        when(accreditationRepository.save(entity)).thenReturn(entity);
        when(accreditationMapper.toDto(entity)).thenReturn(dtoOutput);

        AccreditationDtoOutput result = accreditationService.createAccreditation(request, dtoInput);

        assertEquals(dtoOutput.getAccreditationId(), result.getAccreditationId());
        verify(accreditationEventPublisher).sendAccreditationCreatedNotification(dtoOutput);
    }

    @Test
    void createAccreditation_shouldThrowUserNotFoundException() {
        doNothing().when(validRoleType).validateUserRole(request);
        when(salePointService.getSalePointById(1L)).thenReturn(salePointDto);
        when(userService.getUserById(1L)).thenThrow(new UserNotFoundException(1L));

        assertThrows(UserNotFoundException.class, () -> accreditationService.createAccreditation(request, dtoInput));
    }

    @Test
    void createAccreditation_shouldThrowSalePointNotFoundException() {
        doNothing().when(validRoleType).validateUserRole(request);
        when(salePointService.getSalePointById(1L)).thenThrow(new SalePointNotFoundException(1L));

        assertThrows(SalePointNotFoundException.class, () -> accreditationService.createAccreditation(request, dtoInput));
    }

    @Test
    void createAccreditation_shouldThrowRuntimeException_onUnexpectedError() {
        doThrow(new RuntimeException()).when(validRoleType).validateUserRole(request);

        assertThrows(InternalServerErrorException.class, () -> accreditationService.createAccreditation(request, dtoInput));
    }

    @Test
    void allAccreditations_shouldReturnList_whenSuccess() {
        doNothing().when(validRoleType).validateAdminRole(request);
        when(accreditationRepository.findAll()).thenReturn(Collections.singletonList(entity));
        when(accreditationMapper.toDtoOutputList(anyList())).thenReturn(Collections.singletonList(dtoOutput));

        List<AccreditationDtoOutput> result = accreditationService.allAccreditations(request);

        assertEquals(1, result.size());
    }

    @Test
    void allAccreditations_shouldThrowRuntimeException_onError() {
        doThrow(new RuntimeException()).when(validRoleType).validateAdminRole(request);

        assertThrows(InternalServerErrorException.class, () -> accreditationService.allAccreditations(request));
    }
}
