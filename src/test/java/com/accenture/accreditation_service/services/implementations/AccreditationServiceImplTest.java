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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private AccreditationEventPublisher accreditationEventPublisher;

    @InjectMocks
    private AccreditationServiceImpl accreditationService;

    private final Long userId = 1L;
    private final Long salePointId = 2L;

    private final AccreditationDtoInput input = new AccreditationDtoInput(1050F, salePointId, userId, "user", "user@example.com");
    private final UserDtoIdUsernameEmail userDto = new UserDtoIdUsernameEmail(userId, "user", "user@example.com");
    private final SalePointDtoOutput salePointDto = new SalePointDtoOutput(salePointId, "Point A");
    private final AccreditationEntity entity = new AccreditationEntity();
    private final AccreditationEntity savedEntity = new AccreditationEntity();
    private final AccreditationDtoOutput output = new AccreditationDtoOutput(10L, 1050F, LocalDateTime.now(), salePointDto.getSalePointId(), salePointDto.getName(), userDto.getUserId(), userDto.getUsername(), userDto.getEmail());

    @Test
    void createAccreditation_shouldReturnOutput_whenSuccess() {
        when(salePointService.getSalePointById(salePointId)).thenReturn(salePointDto);
        when(userService.getUserById(userId)).thenReturn(userDto);
        when(accreditationMapper.toEntity(input, salePointDto, userDto)).thenReturn(entity);
        when(accreditationRepository.save(entity)).thenReturn(savedEntity);
        when(accreditationMapper.toDto(savedEntity)).thenReturn(output);

        AccreditationDtoOutput result = accreditationService.createAccreditation(input);

        assertEquals(output.getAccreditationId(), result.getAccreditationId());
        verify(accreditationEventPublisher).sendAccreditationCreatedNotification(output);
    }

    @Test
    void createAccreditation_shouldThrowSalePointNotFound_whenSalePointMissing() {
        when(salePointService.getSalePointById(salePointId)).thenThrow(new SalePointNotFoundException(salePointId));

        assertThrows(SalePointNotFoundException.class, () -> accreditationService.createAccreditation(input));
    }

    @Test
    void createAccreditation_shouldThrowUserNotFound_whenUserMissing() {
        when(salePointService.getSalePointById(salePointId)).thenReturn(salePointDto);
        when(userService.getUserById(userId)).thenThrow(new UserNotFoundException(userId));

        assertThrows(UserNotFoundException.class, () -> accreditationService.createAccreditation(input));
    }

    @Test
    void createAccreditation_shouldThrowInternalServerError_whenUnexpectedError() {
        when(salePointService.getSalePointById(salePointId)).thenReturn(salePointDto);
        when(userService.getUserById(userId)).thenReturn(userDto);
        when(accreditationMapper.toEntity(any(), any(), any())).thenThrow(new RuntimeException("DB crash"));

        assertThrows(InternalServerErrorException.class, () -> accreditationService.createAccreditation(input));
    }

    @Test
    void allAccreditations_shouldReturnList_whenSuccess() {
        List<AccreditationEntity> entityList = List.of(entity);
        List<AccreditationDtoOutput> dtoList = List.of(output);

        when(accreditationRepository.findAll()).thenReturn(entityList);
        when(accreditationMapper.toDtoOutputList(entityList)).thenReturn(dtoList);

        ArrayList<AccreditationDtoOutput> result = accreditationService.allAccreditations();

        assertEquals(1, result.size());
        assertEquals(output.getAccreditationId(), result.get(0).getAccreditationId());
    }

    @Test
    void allAccreditations_shouldThrowInternalServerError_whenUnexpectedError() {
        when(accreditationRepository.findAll()).thenThrow(new RuntimeException("DB down"));

        assertThrows(InternalServerErrorException.class, () -> accreditationService.allAccreditations());
    }

}
