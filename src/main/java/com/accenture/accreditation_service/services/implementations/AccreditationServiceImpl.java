package com.accenture.accreditation_service.services.implementations;

import com.accenture.accreditation_service.client.SalePointClient;
import com.accenture.accreditation_service.client.dtos.SalePointDtoOutput;
import com.accenture.accreditation_service.dtos.AccreditationDtoInput;
import com.accenture.accreditation_service.dtos.AccreditationDtoOutput;
import com.accenture.accreditation_service.models.AccreditationEntity;
import com.accenture.accreditation_service.repositories.AccreditationRepository;
import com.accenture.accreditation_service.services.AccreditationService;
import com.accenture.accreditation_service.services.SalePointCacheService;
import com.accenture.accreditation_service.services.mappers.AccreditationMapper;
import com.accenture.accreditation_service.utils.ApiResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccreditationServiceImpl implements AccreditationService {

    private final AccreditationMapper accreditationMapper;
    private final AccreditationRepository accreditationRepository;
    private final SalePointCacheService salePointCacheService;

    public AccreditationServiceImpl(AccreditationMapper accreditationMapper, AccreditationRepository accreditationRepository, SalePointCacheService salePointCacheService) {
        this.accreditationMapper = accreditationMapper;
        this.accreditationRepository = accreditationRepository;
        this.salePointCacheService = salePointCacheService;
    }

    @Override
    @CacheEvict(value = "accreditations", allEntries = true)
    public ApiResponse<AccreditationDtoOutput> createAccreditation(AccreditationDtoInput accreditationDtoInput) {
        SalePointDtoOutput salePointDtoOutput = salePointCacheService.getSalePointById(accreditationDtoInput.getSalePointId());

        if (salePointDtoOutput == null) {
            throw new NoSuchElementException("Sale Point with id " + accreditationDtoInput.getSalePointId() + " not found.");
        }

        AccreditationEntity accreditationEntity = accreditationMapper.toEntity(accreditationDtoInput, salePointDtoOutput);
        AccreditationEntity accreditationSaved = accreditationRepository.save(accreditationEntity);

        AccreditationDtoOutput accreditationDtoOutput = accreditationMapper.toDto(accreditationSaved);

        ApiResponse response = new ApiResponse<>(
                "Order Received",
                accreditationDtoOutput
        );

        return response;
    }

    @Override
    @Cacheable("accreditations")
    public ApiResponse<List<AccreditationDtoOutput>> allAccreditations() {
        List<AccreditationEntity> accreditationEntityList = accreditationRepository.findAll();
        List<AccreditationDtoOutput> accreditationDtoOutputList = accreditationMapper.toDtoOutputList(accreditationEntityList);

        ApiResponse response = new ApiResponse<>(
                "List of all orders",
                accreditationDtoOutputList
        );

        return response;
    }
}
