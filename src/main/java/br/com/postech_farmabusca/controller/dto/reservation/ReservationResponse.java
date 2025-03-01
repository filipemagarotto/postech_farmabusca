package br.com.postech_farmabusca.controller.dto.reservation;

import br.com.postech_farmabusca.core.ENUM.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    private Long id;
    private String userId;
    private String userName;
    private Long medicationId;
    private String medicationName;
    private Long pharmacyId;
    private String pharmacyName;
    private int quantity;
    private ReservationStatus status;
    private LocalDateTime reservationTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
