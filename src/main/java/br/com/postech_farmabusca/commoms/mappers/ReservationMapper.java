package br.com.postech_farmabusca.commoms.mappers;

import br.com.postech_farmabusca.controller.dto.reservation.ReservationResponse;
import br.com.postech_farmabusca.core.domain.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {PharmacyMapper.class, MedicationMapper.class}
)
public interface ReservationMapper {

    @Mapping(source = "medication.id", target = "medicationId")
    @Mapping(source = "medication.name", target = "medicationName")
    @Mapping(source = "pharmacy.id", target = "pharmacyId")
    @Mapping(source = "pharmacy.name", target = "pharmacyName")
    ReservationResponse toResponse(Reservation reservation);
}
