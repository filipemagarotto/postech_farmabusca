package br.com.postech_farmabusca.resources.repository;

import br.com.postech_farmabusca.core.ENUM.ReservationStatus;
import br.com.postech_farmabusca.resources.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    Optional<ReservationEntity>  findByUserIdAndMedicationId(Integer userId, Long medicationId);
    List<ReservationEntity> findByUserId(Integer userId);
    List<ReservationEntity> findByStatusAndReservationTimeBefore(ReservationStatus status, LocalDateTime time);
}
