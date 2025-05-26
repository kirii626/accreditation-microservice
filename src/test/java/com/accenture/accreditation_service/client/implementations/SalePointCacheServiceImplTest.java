package com.accenture.accreditation_service.client.implementations;

import com.accenture.accreditation_service.client.SalePointClient;
import com.accenture.accreditation_service.client.dtos.SalePointDtoOutput;
import com.accenture.accreditation_service.client.services.implementations.SalePointCacheServiceImpl;
import com.accenture.accreditation_service.exceptions.InternalServerErrorException;
import com.accenture.accreditation_service.exceptions.SalePointNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalePointCacheServiceImplTest {

    @Mock
    private RedisTemplate<String, SalePointDtoOutput> redisTemplate;

    @Mock
    private ValueOperations<String, SalePointDtoOutput> valueOperations;

    @Mock
    private SalePointClient salePointClient;

    private SalePointCacheServiceImpl salePointCacheService;

    private final String key = "salePoint:1";
    private final Long salePointId = 1L;
    private final SalePointDtoOutput salePointDto = new SalePointDtoOutput(1L, "Test Point");

    @BeforeEach
    void setUp() {
        salePointCacheService = new SalePointCacheServiceImpl(redisTemplate, salePointClient);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void getSalePointById_shouldReturnFromCache_whenExists() {
        when(valueOperations.get(key)).thenReturn(salePointDto);

        SalePointDtoOutput result = salePointCacheService.getSalePointById(salePointId);

        assertEquals(salePointDto, result);
        verify(valueOperations).get(key);
        verify(salePointClient, never()).getSalePointById(any());
        verify(valueOperations, never()).set(any(), any(), any());
    }

    @Test
    void getSalePointById_shouldFetchFromClientAndCache_whenNotInCache() {
        when(valueOperations.get(key)).thenReturn(null);
        when(salePointClient.getSalePointById(salePointId)).thenReturn(salePointDto);

        SalePointDtoOutput result = salePointCacheService.getSalePointById(salePointId);

        assertEquals(salePointDto, result);
        verify(valueOperations).get(key);
        verify(salePointClient).getSalePointById(salePointId);
        verify(valueOperations).set(key, salePointDto ,Duration.ofMinutes(10));
    }

    @Test
    void getSalePointById_shouldThrowSalePointNotFoundException_whenClientThrowsIt() {
        when(valueOperations.get(key)).thenReturn(null);
        when(salePointClient.getSalePointById(salePointId))
                .thenThrow(new SalePointNotFoundException(salePointId));

        assertThrows(SalePointNotFoundException.class, () ->
                salePointCacheService.getSalePointById(salePointId));

        verify(salePointClient).getSalePointById(salePointId);
    }

    @Test
    void getSalePointById_shouldThrowInternalServerErrorException_whenUnexpectedErrorOccurs() {
        when(valueOperations.get(key)).thenThrow(new RuntimeException("Redis error"));

        assertThrows(InternalServerErrorException.class, () ->
                salePointCacheService.getSalePointById(salePointId));
    }
}
