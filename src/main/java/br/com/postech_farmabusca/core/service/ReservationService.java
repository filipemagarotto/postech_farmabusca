package br.com.postech_farmabusca.core.service;

import br.com.postech_farmabusca.commoms.exception.BadRequestException;
import br.com.postech_farmabusca.commoms.exception.NotFoundException;
import br.com.postech_farmabusca.commoms.mappers.ReservationMapper;
import br.com.postech_farmabusca.core.ENUM.ReservationStatus;
import br.com.postech_farmabusca.core.domain.Reservation;
import br.com.postech_farmabusca.resources.entities.ReservationEntity;
import br.com.postech_farmabusca.resources.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;
    private final ReservationMapper mapper;

    @Transactional
    public Reservation createReservation(Reservation reservation) {
        reservation.setStatus(ReservationStatus.SCHEDULED);
        reservation.setReservationTime(LocalDateTime.now());

        ReservationEntity entity = mapper.toEntity(reservation);
        ReservationEntity saved = repository.save(entity);

        return mapper.toDomain(saved);
    }

    public Reservation getReservationById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Reserva não encontrada."));
    }

    public List<Reservation> getAllReservations() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public void expireReservations() {
        LocalDateTime expirationThreshold = LocalDateTime.now().minusHours(2);
        List<ReservationEntity> reservationsToExpire = repository
                .findByStatusAndReservationTimeBefore(ReservationStatus.SCHEDULED, expirationThreshold);

        reservationsToExpire.forEach(reservation -> reservation.setStatus(ReservationStatus.EXPIRED));
        repository.saveAll(reservationsToExpire);
    }

    @Transactional
    public void cancelReservation(Long id) {
        ReservationEntity entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva não encontrada."));

        if (entity.getStatus() != ReservationStatus.SCHEDULED) {
            throw new BadRequestException("Apenas reservas agendadas podem ser canceladas.");
        }

        entity.setStatus(ReservationStatus.CANCELED);
        repository.save(entity);
    }
}