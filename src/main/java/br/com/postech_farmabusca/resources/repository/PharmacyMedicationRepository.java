package br.com.postech_farmabusca.resources.repository;

import br.com.postech_farmabusca.core.domain.Medication;
import br.com.postech_farmabusca.core.domain.Pharmacy;
import br.com.postech_farmabusca.resources.entities.PharmacyMedicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PharmacyMedicationRepository extends JpaRepository<PharmacyMedicationEntity, Long> {

    Optional<PharmacyMedicationEntity> findByPharmacyAndMedication(Pharmacy pharmacy, Medication medication);
}
