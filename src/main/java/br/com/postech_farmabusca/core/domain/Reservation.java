package br.com.postech_farmabusca.core.domain;

import br.com.postech_farmabusca.core.ENUM.ReservationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reservation {
    private Long id;
    private String userId;
    private Medication medication;
    private Pharmacy pharmacy;
    private int quantity;
    private ReservationStatus status;
    private LocalDateTime reservationTime;
}

