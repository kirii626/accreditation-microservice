package com.accenture.accreditation_service.services.implementations;

import com.accenture.accreditation_service.client.dtos.SalePointDtoOutput;
import com.accenture.accreditation_service.client.dtos.UserDtoIdUsernameEmail;
import com.accenture.accreditation_service.dtos.AccreditationDtoInput;
import com.accenture.accreditation_service.dtos.AccreditationDtoOutput;
import com.accenture.accreditation_service.models.AccreditationEntity;
import com.accenture.accreditation_service.repositories.AccreditationRepository;
import com.accenture.accreditation_service.services.AccreditationService;
import com.accenture.accreditation_service.client.services.SalePointService;
import com.accenture.accreditation_service.client.services.UserService;
import com.accenture.accreditation_service.services.mappers.AccreditationMapper;
import com.accenture.accreditation_service.services.validations.ValidRoleType;
import com.accenture.accreditation_service.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class AccreditationServiceImpl implements AccreditationService {

    private final AccreditationMapper accreditationMapper;
    private final AccreditationRepository accreditationRepository;
    private final SalePointService salePointService;
    private final UserService userService;
    private final ValidRoleType validRoleType;

    @Override
    @CacheEvict(value = "accreditations", allEntries = true)
    public AccreditationDtoOutput createAccreditation(HttpServletRequest httpServletRequest, AccreditationDtoInput accreditationDtoInput) {
        validRoleType.validateUserRole(httpServletRequest);
        UserDtoIdUsernameEmail userDtoIdUsernameEmail = userService.getUserById(accreditationDtoInput.getUserId());
        SalePointDtoOutput salePointDtoOutput = salePointService.getSalePointById(accreditationDtoInput.getSalePointId());

        if (salePointDtoOutput == null) {
            throw new NoSuchElementException("Sale Point with id " + accreditationDtoInput.getSalePointId() + " not found.");
        }

        AccreditationEntity accreditationEntity = accreditationMapper.toEntity(accreditationDtoInput, salePointDtoOutput, userDtoIdUsernameEmail);
        AccreditationEntity accreditationSaved = accreditationRepository.save(accreditationEntity);

        AccreditationDtoOutput accreditationDtoOutput = accreditationMapper.toDto(accreditationSaved);

        return accreditationDtoOutput;
    }

    @Override
    @Cacheable("accreditations")
    public List<AccreditationDtoOutput> allAccreditations(HttpServletRequest httpServletRequest) {
        validRoleType.validateAdminRole(httpServletRequest);
        List<AccreditationEntity> accreditationEntityList = accreditationRepository.findAll();
        List<AccreditationDtoOutput> accreditationDtoOutputList = accreditationMapper.toDtoOutputList(accreditationEntityList);

        return accreditationDtoOutputList;
    }
}
