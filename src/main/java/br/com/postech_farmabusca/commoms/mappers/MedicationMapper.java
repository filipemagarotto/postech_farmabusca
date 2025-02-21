package br.com.postech_farmabusca.commoms.mappers;

import br.com.postech_farmabusca.controller.dto.medication.CreateMedicationRequest;
import br.com.postech_farmabusca.controller.dto.medication.MedicationResponse;
import br.com.postech_farmabusca.controller.dto.medication.UpdateMedicationRequest;
import br.com.postech_farmabusca.core.domain.Medication;
import br.com.postech_farmabusca.resources.entities.MedicationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicationMapper {

    Medication toDomain(MedicationEntity entity);

    MedicationEntity toEntity(Medication domain);

    Medication toDomain(CreateMedicationRequest request);

    MedicationResponse toResponse(Medication domain);

    Medication toDomain(UpdateMedicationRequest request);

}
