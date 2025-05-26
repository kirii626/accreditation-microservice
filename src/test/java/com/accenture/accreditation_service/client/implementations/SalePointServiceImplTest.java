package com.accenture.accreditation_service.client.implementations;

import com.accenture.accreditation_service.client.dtos.SalePointDtoOutput;
import com.accenture.accreditation_service.client.services.SalePointCacheService;
import com.accenture.accreditation_service.client.services.implementations.SalePointServiceImpl;
import com.accenture.accreditation_service.exceptions.SalePointNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SalePointServiceImplTest {

    @Mock
    private SalePointCacheService cacheService;

    @InjectMocks
    private SalePointServiceImpl salePointService;

    private SalePointDtoOutput salePointDto;

    @BeforeEach
    void setup() {
        salePointDto = new SalePointDtoOutput(1L, "Point A");
    }

    @Test
    void getSalePointById_shouldReturnSalePoint_whenFound() {
        when(cacheService.getSalePointById(1L)).thenReturn(salePointDto);

        SalePointDtoOutput result = salePointService.getSalePointById(1L);

        assertEquals(salePointDto, result);
    }

    @Test
    void getSalePointById_shouldThrowSalePointNotFoundException_whenNotFound() {
        when(cacheService.getSalePointById(1L)).thenThrow(new SalePointNotFoundException(1L));

        assertThrows(SalePointNotFoundException.class, () -> salePointService.getSalePointById(1L));
    }

}
