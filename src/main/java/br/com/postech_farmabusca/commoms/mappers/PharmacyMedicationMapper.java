package br.com.postech_farmabusca.commoms.mappers;

import br.com.postech_farmabusca.controller.dto.pharmacymedication.CreatePharmacyMedicationRequest;
import br.com.postech_farmabusca.controller.dto.pharmacymedication.PharmacyMedicationResponse;
import br.com.postech_farmabusca.controller.dto.pharmacymedication.UpdatePharmacyMedicationRequest;
import br.com.postech_farmabusca.core.domain.PharmacyMedication;
import br.com.postech_farmabusca.resources.entities.PharmacyMedicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static br.com.postech_farmabusca.commoms.mappers.utils.MappingUtils.LOCAL_DATE_TIME_NOW;

@Mapper(componentModel = "spring")
public interface PharmacyMedicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", defaultExpression = LOCAL_DATE_TIME_NOW)
    @Mapping(target = "updatedAt", defaultExpression = LOCAL_DATE_TIME_NOW)
    PharmacyMedicationEntity toEntity(PharmacyMedication domain);

    PharmacyMedication toDomain(PharmacyMedicationEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PharmacyMedication toDomain(CreatePharmacyMedicationRequest request);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PharmacyMedication toDomain(UpdatePharmacyMedicationRequest request);

    PharmacyMedicationResponse toResponse(PharmacyMedication domain);
}