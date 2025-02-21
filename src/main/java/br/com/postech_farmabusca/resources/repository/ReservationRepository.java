package br.com.postech_farmabusca.resources.repository;

import br.com.postech_farmabusca.resources.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

}
