package br.com.postech_farmabusca.commoms.mappers;

import br.com.postech_farmabusca.controller.dto.reservation.ReservationResponse;
import br.com.postech_farmabusca.core.domain.Reservation;
import br.com.postech_farmabusca.resources.entities.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {MedicationMapper.class, PharmacyMapper.class})
public interface ReservationMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "userName", source = "user.name"),
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "medicationId", source = "medication.id"),
            @Mapping(target = "medicationName", source = "medication.name"),
            @Mapping(target = "pharmacyId", source = "pharmacy.id"),
            @Mapping(target = "pharmacyName", source = "pharmacy.name")
    })
    ReservationResponse toResponse(Reservation reservation);

    @Mapping(target = "id", source = "id")
    Reservation toDomain(ReservationEntity entity);

    @Mapping(target = "id", ignore = true)
    ReservationEntity toEntity(Reservation domain);
}
