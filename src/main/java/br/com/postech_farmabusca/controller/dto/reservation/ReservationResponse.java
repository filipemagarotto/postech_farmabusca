package br.com.postech_farmabusca.controller.dto.reservation;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationResponse {
    private Long id;
    private String userId;
    private String userName;
    private Long medicationId;
    private String medicationName;
    private Long pharmacyId;
    private String pharmacyName;
    private int quantity;
    private String status;
    private LocalDateTime reservationTime;
}
