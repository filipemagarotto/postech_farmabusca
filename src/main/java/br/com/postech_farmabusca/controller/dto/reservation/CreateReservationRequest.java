package br.com.postech_farmabusca.controller.dto.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateReservationRequest {
    @NotNull
    private String userId;
    @NotNull
    private Long pharmacyId;
    @NotNull
    private Long medicationId;
    @NotNull
    private int quantity;
}
