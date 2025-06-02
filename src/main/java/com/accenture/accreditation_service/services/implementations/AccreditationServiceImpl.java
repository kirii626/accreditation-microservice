package com.accenture.accreditation_service.services.implementations;

import com.accenture.accreditation_service.client.dtos.SalePointDtoOutput;
import com.accenture.accreditation_service.client.dtos.UserDtoIdUsernameEmail;
import com.accenture.accreditation_service.dtos.AccreditationDtoInput;
import com.accenture.accreditation_service.dtos.AccreditationDtoOutput;
import com.accenture.accreditation_service.events.publishers.AccreditationEventPublisher;
import com.accenture.accreditation_service.exceptions.*;
import com.accenture.accreditation_service.models.AccreditationEntity;
import com.accenture.accreditation_service.repositories.AccreditationRepository;
import com.accenture.accreditation_service.services.AccreditationService;
import com.accenture.accreditation_service.client.services.SalePointService;
import com.accenture.accreditation_service.client.services.UserService;
import com.accenture.accreditation_service.services.mappers.AccreditationMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccreditationServiceImpl implements AccreditationService {

    private final AccreditationMapper accreditationMapper;
    private final AccreditationRepository accreditationRepository;
    private final SalePointService salePointService;
    private final UserService userService;
    private final AccreditationEventPublisher accreditationEventPublisher;

    @Override
    @CacheEvict(value = "accreditations", allEntries = true)
    @Transactional
    public AccreditationDtoOutput createAccreditation(AccreditationDtoInput accreditationDtoInput) {
        log.info("Creating accreditation for userId={}, salePointId={}", accreditationDtoInput.getUserId(), accreditationDtoInput.getSalePointId());

        try {
            log.debug("User role validated");

            UserDtoIdUsernameEmail userDto;
            SalePointDtoOutput salePointDto;

            try {
                salePointDto = salePointService.getSalePointById(accreditationDtoInput.getSalePointId());
                log.debug("Sale point fetched: {}", salePointDto.getName());
            } catch (SalePointNotFoundException ex) {
                log.warn("Sale point with ID {} not found", accreditationDtoInput.getSalePointId(), ex);
                throw ex;
            }

            try {
                userDto = userService.getUserById(accreditationDtoInput.getUserId());
                log.debug("User fetched: {}", userDto.getUsername());
            } catch (UserNotFoundException ex) {
                log.warn("User with ID {} not found", accreditationDtoInput.getUserId(), ex);
                throw ex;
            }

            AccreditationEntity accreditationEntity = accreditationMapper.toEntity(accreditationDtoInput, salePointDto, userDto);
            AccreditationEntity savedEntity = accreditationRepository.save(accreditationEntity);

            AccreditationDtoOutput accreditationDtoOutput = accreditationMapper.toDto(savedEntity);
            accreditationEventPublisher.sendAccreditationCreatedNotification(accreditationDtoOutput);

            log.info("Accreditation created successfully: id={}", accreditationDtoOutput.getAccreditationId());
            return accreditationDtoOutput;
        } catch (UserNotFoundException | SalePointNotFoundException ex) {
            throw ex;
        }  catch (Exception ex) {
            log.error("Unexpected error during accreditation creation", ex);
            throw new InternalServerErrorException("Internal error while creating accreditation", ex);
        }
    }

    @Override
    @Cacheable("accreditations")
    public ArrayList<AccreditationDtoOutput> allAccreditations() {
        log.info("Fetching all accreditations");

        try {
            log.debug("Admin role validated");

            List<AccreditationEntity> accreditationEntityList = accreditationRepository.findAll();
            List<AccreditationDtoOutput> accreditationDtoOutputList = accreditationMapper.toDtoOutputList(accreditationEntityList);
            ArrayList<AccreditationDtoOutput> accreditationDtoOutputArrayList = new ArrayList<>(accreditationDtoOutputList);

            log.info("Fetched {} accreditations", accreditationDtoOutputList.size());
            return accreditationDtoOutputArrayList;
        } catch (Exception ex) {
            log.error("Error fetching accreditations", ex);
            throw new InternalServerErrorException("Internal error fetching accreditations", ex);
        }
    }
}
