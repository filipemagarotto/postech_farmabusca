package br.com.postech_farmabusca.resources.repository;

import br.com.postech_farmabusca.resources.entities.MedicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<MedicationEntity, Long> {
    Optional<MedicationEntity> findByName(String name);
}
