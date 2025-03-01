package br.com.postech_farmabusca.core.service;

import br.com.postech_farmabusca.commoms.exception.BadRequestException;
import br.com.postech_farmabusca.commoms.exception.NotFoundException;
import br.com.postech_farmabusca.commoms.exception.UnauthorizedException;
import br.com.postech_farmabusca.commoms.mappers.ReservationMapper;
import br.com.postech_farmabusca.controller.dto.reservation.CreateReservationRequest;
import br.com.postech_farmabusca.core.ENUM.ReservationStatus;
import br.com.postech_farmabusca.core.domain.*;
import br.com.postech_farmabusca.resources.entities.MedicationEntity;
import br.com.postech_farmabusca.resources.entities.PharmacyEntity;
import br.com.postech_farmabusca.resources.entities.PharmacyMedicationEntity;
import br.com.postech_farmabusca.resources.entities.ReservationEntity;
import br.com.postech_farmabusca.resources.repository.MedicationRepository;
import br.com.postech_farmabusca.resources.repository.PharmacyMedicationRepository;
import br.com.postech_farmabusca.resources.repository.PharmacyRepository;
import br.com.postech_farmabusca.resources.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final PharmacyMedicationRepository pharmacyMedicationRepository;
    private final PharmacyRepository pharmacyRepository;
    private final MedicationRepository medicationRepository;

    @Transactional
    public Reservation createReservation(CreateReservationRequest createReservationRequest) {
        User user = getAuthenticatedUser();

        PharmacyEntity pharmacyEntity = pharmacyRepository.findById(createReservationRequest.getPharmacyId())
                .orElseThrow(() -> new NotFoundException("Farmácia não encontrada com ID: " + createReservationRequest.getPharmacyId()));

        MedicationEntity medicationEntity = medicationRepository.findById(createReservationRequest.getMedicationId())
                .orElseThrow(() -> new NotFoundException("Medicamento não encontrado com ID: " + createReservationRequest.getMedicationId()));

        PharmacyMedicationEntity pharmacyMedication = pharmacyMedicationRepository.findByPharmacyAndMedication(pharmacyEntity, medicationEntity)
                .orElseThrow(() -> new NotFoundException("Este medicamento não está disponível nesta farmácia."));

        if (pharmacyMedication.getStock() < createReservationRequest.getQuantity()) {
            throw new BadRequestException("Estoque insuficiente. Apenas " + pharmacyMedication.getStock() + " unidades disponíveis.");
        }

        pharmacyMedication.setStock(pharmacyMedication.getStock() - createReservationRequest.getQuantity());
        pharmacyMedicationRepository.save(pharmacyMedication);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setPharmacy(new Pharmacy(pharmacyEntity.getId(), pharmacyEntity.getName())); // Apenas informações essenciais
        reservation.setMedication(new Medication(medicationEntity.getId(), medicationEntity.getName())); // Apenas informações essenciais
        reservation.setQuantity(createReservationRequest.getQuantity());
        reservation.setStatus(ReservationStatus.SCHEDULED);
        reservation.setReservationTime(LocalDateTime.now());

        ReservationEntity entity = mapper.toEntity(reservation);
        entity.setMedication(medicationEntity);
        entity.setPharmacy(pharmacyEntity);
        ReservationEntity saved = repository.save(entity);

        return mapper.toDomain(saved);
    }

    public List<Reservation> getMyReservations() {
        User user = getAuthenticatedUser();
        return repository.findByUserId(user.getId()).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    public Reservation getMyReservationByMedication(Long medicationId) {
        User user = getAuthenticatedUser();
        return repository.findByUserIdAndMedicationId(user.getId(), medicationId)
                .map(mapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Reserva não encontrada para este medicamento."));
    }

    public Reservation getReservationById(Long id) {
        User user = getAuthenticatedUser();

        ReservationEntity reservation = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva não encontrada."));

        if (!user.getRole().equals(UserType.ADMIN) && !reservation.getUser().getId().equals(user.getId().toString())) {
            throw new UnauthorizedException("Acesso negado: você só pode visualizar suas próprias reservas.");
        }

        return mapper.toDomain(reservation);
    }

    public List<Reservation> getAllReservations() {
        User user = getAuthenticatedUser();

        List<ReservationEntity> reservations;

        if (user.getRole().equals(UserType.ADMIN)) {
            reservations = repository.findAll();
        } else {
            reservations = repository.findByUserId(user.getId());
        }

        return reservations.stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 30 * 60 * 1000)
    @Transactional
    public void runExpireReservationsJob() {
        System.out.println("🔄 Executando expiração de reservas...");
        expireReservations();
    }

    @Transactional
    public void expireReservations() {
        LocalDateTime expirationThreshold = LocalDateTime.now().minusHours(2);
        List<ReservationEntity> reservationsToExpire = repository
                .findByStatusAndReservationTimeBefore(ReservationStatus.SCHEDULED, expirationThreshold);

        if (!reservationsToExpire.isEmpty()) {
            reservationsToExpire.forEach(reservation -> reservation.setStatus(ReservationStatus.EXPIRED));
            repository.saveAll(reservationsToExpire);
            System.out.println("✅ Reservas expiradas: " + reservationsToExpire.size());
        } else {
            System.out.println("⚠ Nenhuma reserva para expirar.");
        }
    }

    @Transactional
    public void cancelReservation(Long id) {
        User user = getAuthenticatedUser();

        ReservationEntity entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva não encontrada."));

        if (!user.getRole().equals(UserType.ADMIN) && !entity.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Acesso negado: você só pode cancelar suas próprias reservas.");
        }

        if (entity.getStatus() != ReservationStatus.SCHEDULED) {
            throw new BadRequestException("Apenas reservas agendadas podem ser canceladas.");
        }

        PharmacyMedicationEntity pharmacyMedication = pharmacyMedicationRepository
                .findByPharmacyAndMedication(entity.getPharmacy(), entity.getMedication())
                .orElseThrow(() -> new NotFoundException("Registro de estoque não encontrado."));

        pharmacyMedication.setStock(pharmacyMedication.getStock() + entity.getQuantity());
        pharmacyMedicationRepository.save(pharmacyMedication);

        entity.setStatus(ReservationStatus.CANCELED);
        repository.save(entity);
    }

    @Transactional
    public Reservation pickUpReservation(Long id) {
        User user = getAuthenticatedUser();

        ReservationEntity entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva não encontrada."));

        if (!user.getRole().equals(UserType.ADMIN) && !entity.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Acesso negado: você só pode retirar suas próprias reservas.");
        }

        if (entity.getStatus() == ReservationStatus.PICKED_UP) {
            throw new BadRequestException("Esta reserva já foi retirada.");
        }

        if (entity.getStatus() == ReservationStatus.CANCELED || entity.getStatus() == ReservationStatus.EXPIRED) {
            throw new BadRequestException("Reservas canceladas ou expiradas não podem ser retiradas.");
        }

        entity.setStatus(ReservationStatus.PICKED_UP);
        repository.save(entity);

        return mapper.toDomain(entity);
    }


    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Usuário não autenticado. Faça login para continuar.");
        }

        return (User) authentication.getPrincipal();
    }
}
