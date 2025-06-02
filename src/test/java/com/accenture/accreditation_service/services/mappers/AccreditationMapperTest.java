package com.accenture.accreditation_service.services.mappers;

import com.accenture.accreditation_service.client.dtos.SalePointDtoOutput;
import com.accenture.accreditation_service.client.dtos.UserDtoIdUsernameEmail;
import com.accenture.accreditation_service.dtos.AccreditationDtoInput;
import com.accenture.accreditation_service.dtos.AccreditationDtoOutput;
import com.accenture.accreditation_service.models.AccreditationEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class AccreditationMapperTest {

    private AccreditationMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new AccreditationMapper();
    }

    @Test
    void toEntity_shouldMapAllFieldsCorrectly() {
        AccreditationDtoInput input = new AccreditationDtoInput(100.0f, 10L, 20L, "user", "user@example.com");
        SalePointDtoOutput salePointDto = new SalePointDtoOutput(10L, "Point A");
        UserDtoIdUsernameEmail userDto = new UserDtoIdUsernameEmail(20L, "user", "user@example.com");

        AccreditationEntity entity = mapper.toEntity(input, salePointDto, userDto);

        assertEquals(100.0f, entity.getAmount());
        assertNotNull(entity.getReceivedAt());
        assertEquals(10L, entity.getSalePointId());
        assertEquals("Point A", entity.getNameSalePoint());
        assertEquals(20L, entity.getUserId());
        assertEquals("user", entity.getUsername());
        assertEquals("user@example.com", entity.getEmail());
    }

    @Test
    void toDto_shouldMapAllFieldsCorrectly() {
        LocalDateTime now = LocalDateTime.now();

        AccreditationEntity entity = new AccreditationEntity();
        entity.setAmount(200.0f);
        entity.setReceivedAt(now);
        entity.setSalePointId(15L);
        entity.setNameSalePoint("Point B");
        entity.setUserId(25L);
        entity.setUsername("admin");
        entity.setEmail("admin@example.com");

        AccreditationDtoOutput dto = mapper.toDto(entity);

        assertEquals(200.0f, dto.getAmount());
        assertEquals(now, dto.getReceivedAt());
        assertEquals(15L, dto.getSalePointId());
        assertEquals("Point B", dto.getNameSalePoint());
        assertEquals(25L, dto.getUserId());
        assertEquals("admin", dto.getUsername());
        assertEquals("admin@example.com", dto.getEmail());
    }

    @Test
    void toDtoOutputList_shouldMapListCorrectly() {
        AccreditationEntity entity1 = new AccreditationEntity();
        entity1.setAmount(100.0f);
        entity1.setReceivedAt(LocalDateTime.now());
        entity1.setSalePointId(1L);
        entity1.setNameSalePoint("Point A");
        entity1.setUserId(1L);
        entity1.setUsername("user1");
        entity1.setEmail("user1@example.com");

        AccreditationEntity entity2 = new AccreditationEntity();
        entity2.setAmount(200.0f);
        entity2.setReceivedAt(LocalDateTime.now());
        entity2.setSalePointId(2L);
        entity2.setNameSalePoint("Point B");
        entity2.setUserId(2L);
        entity2.setUsername("user2");
        entity2.setEmail("user2@example.com");

        List<AccreditationDtoOutput> result = mapper.toDtoOutputList(List.of(entity1, entity2));

        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());
    }
}
