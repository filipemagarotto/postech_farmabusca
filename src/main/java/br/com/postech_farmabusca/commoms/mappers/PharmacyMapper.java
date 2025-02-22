package br.com.postech_farmabusca.commoms.mappers;

import br.com.postech_farmabusca.controller.dto.pharmacy.CreatePharmacyRequest;
import br.com.postech_farmabusca.controller.dto.pharmacy.PharmacyResponse;
import br.com.postech_farmabusca.controller.dto.pharmacy.UpdatePharmacyRequest;
import br.com.postech_farmabusca.core.domain.Pharmacy;
import br.com.postech_farmabusca.resources.entities.PharmacyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PharmacyMapper {
    Pharmacy toDomain(PharmacyEntity entity);
    PharmacyEntity toEntity(Pharmacy domain);
    Pharmacy toDomain(CreatePharmacyRequest request);
    PharmacyResponse toResponse(Pharmacy domain);
    Pharmacy toDomain(UpdatePharmacyRequest request);
}
