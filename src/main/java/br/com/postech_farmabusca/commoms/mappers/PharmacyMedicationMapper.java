package br.com.postech_farmabusca.commoms.mappers;

import br.com.postech_farmabusca.controller.dto.pharmacymedication.CreatePharmacyMedicationRequest;
import br.com.postech_farmabusca.controller.dto.pharmacymedication.PharmacyMedicationResponse;
import br.com.postech_farmabusca.controller.dto.pharmacymedication.UpdatePharmacyMedicationRequest;
import br.com.postech_farmabusca.core.domain.PharmacyMedication;
import br.com.postech_farmabusca.resources.entities.PharmacyMedicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PharmacyMedicationMapper {

    @Mapping(target = "id", ignore = true)
    PharmacyMedicationEntity toEntity(PharmacyMedication domain);

    PharmacyMedication toDomain(PharmacyMedicationEntity entity);

    PharmacyMedication toDomain(CreatePharmacyMedicationRequest request);

    PharmacyMedication toDomain(UpdatePharmacyMedicationRequest request);

    PharmacyMedicationResponse toResponse(PharmacyMedication domain);
}