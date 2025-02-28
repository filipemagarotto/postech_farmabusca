package br.com.postech_farmabusca.controller.dto.reservation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateReservationRequest {

    @NotNull
    private Long medicationId;

    @NotNull
    private Long pharmacyId;

    @Min(1)
    private int quantity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
