package br.com.postech_farmabusca.commoms.mappers;

import br.com.postech_farmabusca.controller.dto.pharmacymedication.CreatePharmacyMedicationRequest;
import br.com.postech_farmabusca.controller.dto.pharmacymedication.PharmacyMedicationResponse;
import br.com.postech_farmabusca.controller.dto.pharmacymedication.UpdatePharmacyMedicationRequest;
import br.com.postech_farmabusca.core.domain.PharmacyMedication;
import br.com.postech_farmabusca.resources.entities.PharmacyMedicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import static br.com.postech_farmabusca.commoms.mappers.utils.MappingUtils.LOCAL_DATE_TIME_NOW;

@Mapper(componentModel = "spring")
public interface PharmacyMedicationMapper {

    @Mapping(target = "createdAt", defaultExpression = LOCAL_DATE_TIME_NOW)
    @Mapping(target = "updatedAt", defaultExpression = LOCAL_DATE_TIME_NOW)
    @Mapping(target = "pharmacy", ignore = true)
    @Mapping(target = "medication", ignore = true)
    PharmacyMedication toDomain(CreatePharmacyMedicationRequest request);

    @Mapping(target = "pharmacy", ignore = true)
    @Mapping(target = "medication", ignore = true)
    PharmacyMedication toDomain(UpdatePharmacyMedicationRequest request);

    @Mapping(target = "pharmacy", ignore = true)
    @Mapping(target = "medication", ignore = true)
    PharmacyMedicationEntity toEntity(PharmacyMedication domain);

    PharmacyMedication toDomain(PharmacyMedicationEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "pharmacyId", source = "pharmacy.id")
    @Mapping(target = "medicationId", source = "medication.id")
    PharmacyMedicationResponse toResponse(PharmacyMedication domain);
}
