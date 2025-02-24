package br.com.postech_farmabusca.controller.dto.reservation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateReservationRequest {

    @NotNull
    private Long medicationId;

    @NotNull
    private Long pharmacyId;

    @Min(1)
    private int quantity;
}
