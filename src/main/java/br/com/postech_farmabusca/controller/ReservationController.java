package br.com.postech_farmabusca.controller;

import br.com.postech_farmabusca.commoms.mappers.ReservationMapper;
import br.com.postech_farmabusca.controller.dto.reservation.CreateReservationRequest;
import br.com.postech_farmabusca.controller.dto.reservation.ReservationResponse;
import br.com.postech_farmabusca.core.domain.PharmacyMedication;
import br.com.postech_farmabusca.core.domain.Reservation;
import br.com.postech_farmabusca.core.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;
    private final ReservationMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse createReservation(@RequestBody @Valid CreateReservationRequest request) {
        Reservation reservation = service.createReservation(request);
        return mapper.toResponse(reservation);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationResponse getReservationById(@PathVariable Long id) {
        return mapper.toResponse(service.getReservationById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponse> getAllReservations() {
        return service.getAllReservations().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponse> getMyReservations() {
        return service.getMyReservations().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/my/{medicationId}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationResponse getMyReservationByMedication(@PathVariable Long medicationId) {
        return mapper.toResponse(service.getMyReservationByMedication(medicationId));
    }

    @PatchMapping("/{id}/pickup")
    @ResponseStatus(HttpStatus.OK)
    public ReservationResponse pickUpReservation(@PathVariable Long id) {
        return mapper.toResponse(service.pickUpReservation(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void cancelReservation(@PathVariable Long id) {
        service.cancelReservation(id);
    }
}

