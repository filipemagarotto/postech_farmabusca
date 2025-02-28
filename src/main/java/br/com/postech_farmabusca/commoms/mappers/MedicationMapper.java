package br.com.postech_farmabusca.commoms.mappers;

import br.com.postech_farmabusca.controller.dto.medication.CreateMedicationRequest;
import br.com.postech_farmabusca.controller.dto.medication.MedicationResponse;
import br.com.postech_farmabusca.controller.dto.medication.UpdateMedicationRequest;
import br.com.postech_farmabusca.core.domain.Medication;
import br.com.postech_farmabusca.resources.entities.MedicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static br.com.postech_farmabusca.commoms.mappers.utils.MappingUtils.LOCAL_DATE_TIME_NOW;

@Mapper(componentModel = "spring")
public interface MedicationMapper {

    Medication toDomain(MedicationEntity entity);

    @Mapping(target = "createdAt", defaultExpression = LOCAL_DATE_TIME_NOW)
    @Mapping(target = "updatedAt", defaultExpression = LOCAL_DATE_TIME_NOW)
    MedicationEntity toEntity(Medication domain);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Medication toDomain(CreateMedicationRequest request);

    MedicationResponse toResponse(Medication domain);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Medication toDomain(UpdateMedicationRequest request);

}
