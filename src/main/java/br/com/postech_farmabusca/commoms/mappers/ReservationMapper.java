package br.com.postech_farmabusca.commoms.mappers;

import br.com.postech_farmabusca.controller.dto.reservation.CreateReservationRequest;
import br.com.postech_farmabusca.controller.dto.reservation.ReservationResponse;
import br.com.postech_farmabusca.core.domain.Reservation;
import br.com.postech_farmabusca.resources.entities.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MedicationMapper.class, PharmacyMapper.class})
public interface ReservationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "SCHEDULED")
    @Mapping(target = "reservationTime", expression = "java(java.time.LocalDateTime.now())")
    Reservation toDomain(CreateReservationRequest request);

    ReservationEntity toEntity(Reservation domain);

    Reservation toDomain(ReservationEntity entity);

    ReservationResponse toResponse(Reservation domain);
}
