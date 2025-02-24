package br.com.postech_farmabusca.resources.repository;

import br.com.postech_farmabusca.core.ENUM.ReservationStatus;
import br.com.postech_farmabusca.resources.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByStatusAndReservationTimeBefore(ReservationStatus status, LocalDateTime time);
}
