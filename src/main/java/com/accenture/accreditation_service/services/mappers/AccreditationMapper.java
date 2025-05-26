package com.accenture.accreditation_service.services.mappers;

import com.accenture.accreditation_service.client.dtos.SalePointDtoOutput;
import com.accenture.accreditation_service.client.dtos.UserDtoIdUsernameEmail;
import com.accenture.accreditation_service.dtos.AccreditationDtoInput;
import com.accenture.accreditation_service.dtos.AccreditationDtoOutput;
import com.accenture.accreditation_service.models.AccreditationEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AccreditationMapper {

    public AccreditationEntity toEntity(AccreditationDtoInput accreditationDtoInput, SalePointDtoOutput salePointDtoOutput, UserDtoIdUsernameEmail userDtoIdUsernameEmail) {
        AccreditationEntity accreditationEntity = new AccreditationEntity();
        accreditationEntity.setAmount(accreditationDtoInput.getAmount());
        accreditationEntity.setReceivedAt(LocalDateTime.now());
        accreditationEntity.setSalePointId(salePointDtoOutput.getSalePointId());
        accreditationEntity.setNameSalePoint(salePointDtoOutput.getName());
        accreditationEntity.setUserId(userDtoIdUsernameEmail.getUserId());
        accreditationEntity.setUsername(userDtoIdUsernameEmail.getUsername());
        accreditationEntity.setEmail(userDtoIdUsernameEmail.getEmail());

        return accreditationEntity;
    }

    public AccreditationDtoOutput toDto(AccreditationEntity accreditationEntity) {
        return new AccreditationDtoOutput(
                accreditationEntity.getAccreditationId(),
                accreditationEntity.getAmount(),
                accreditationEntity.getReceivedAt(),
                accreditationEntity.getSalePointId(),
                accreditationEntity.getNameSalePoint(),
                accreditationEntity.getUserId(),
                accreditationEntity.getUsername(),
                accreditationEntity.getEmail()
        );
    }

    public List<AccreditationDtoOutput> toDtoOutputList(List<AccreditationEntity> accreditationEntityList) {
        return accreditationEntityList
                .stream()
                .map(this::toDto)
                .toList();
    }
}
