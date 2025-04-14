package com.accenture.accreditation_service.services.mappers;

import com.accenture.accreditation_service.client.dtos.SalePointDtoOutput;
import com.accenture.accreditation_service.dtos.AccreditationDtoInput;
import com.accenture.accreditation_service.dtos.AccreditationDtoOutput;
import com.accenture.accreditation_service.models.AccreditationEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccreditationMapper {

    public AccreditationEntity toEntity(AccreditationDtoInput accreditationDtoInput, SalePointDtoOutput salePointDtoOutput) {
        AccreditationEntity accreditationEntity = new AccreditationEntity();
        accreditationEntity.setAmount(accreditationDtoInput.getAmount());
        accreditationEntity.setReceivedAt(LocalDateTime.now());
        accreditationEntity.setSalePointId(salePointDtoOutput.getSalePointId());
        accreditationEntity.setNameSalePoint(salePointDtoOutput.getName());

        return accreditationEntity;
    }

    public AccreditationDtoOutput toDto(AccreditationEntity accreditationEntity) {
        return new AccreditationDtoOutput(
                accreditationEntity.getAccreditationId(),
                accreditationEntity.getAmount(),
                accreditationEntity.getReceivedAt(),
                accreditationEntity.getSalePointId(),
                accreditationEntity.getNameSalePoint()
        );
    }

    public List<AccreditationDtoOutput> toDtoOutputList(List<AccreditationEntity> accreditationEntityList) {
        return accreditationEntityList
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
