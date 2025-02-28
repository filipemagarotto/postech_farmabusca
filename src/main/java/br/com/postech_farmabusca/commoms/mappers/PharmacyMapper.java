package br.com.postech_farmabusca.commoms.mappers;

import br.com.postech_farmabusca.controller.dto.pharmacy.CreatePharmacyRequest;
import br.com.postech_farmabusca.controller.dto.pharmacy.PharmacyResponse;
import br.com.postech_farmabusca.controller.dto.pharmacy.UpdatePharmacyRequest;
import br.com.postech_farmabusca.core.domain.Pharmacy;
import br.com.postech_farmabusca.resources.entities.PharmacyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static br.com.postech_farmabusca.commoms.mappers.utils.MappingUtils.LOCAL_DATE_TIME_NOW;

@Mapper(componentModel = "spring")
public interface PharmacyMapper {
    Pharmacy toDomain(PharmacyEntity entity);

    @Mapping(target = "createdAt", defaultExpression = LOCAL_DATE_TIME_NOW)
    @Mapping(target = "updatedAt", defaultExpression = LOCAL_DATE_TIME_NOW)
    PharmacyEntity toEntity(Pharmacy domain);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Pharmacy toDomain(CreatePharmacyRequest request);
    PharmacyResponse toResponse(Pharmacy domain);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Pharmacy toDomain(UpdatePharmacyRequest request);
}
