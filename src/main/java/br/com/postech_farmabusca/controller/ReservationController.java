package br.com.postech_farmabusca.controller;

import br.com.postech_farmabusca.commoms.mappers.ReservationMapper;
import br.com.postech_farmabusca.controller.dto.reservation.CreateReservationRequest;
import br.com.postech_farmabusca.controller.dto.reservation.ReservationResponse;
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
        Reservation reservation = mapper.toDomain(request);
        return mapper.toResponse(service.createReservation(reservation));
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelReservation(@PathVariable Long id) {
        service.cancelReservation(id);
    }
}
