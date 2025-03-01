package br.com.postech_farmabusca.resources.repository;

import br.com.postech_farmabusca.resources.entities.MedicationEntity;
import br.com.postech_farmabusca.resources.entities.PharmacyEntity;
import br.com.postech_farmabusca.resources.entities.PharmacyMedicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PharmacyMedicationRepository extends JpaRepository<PharmacyMedicationEntity, Long> {

    Optional<PharmacyMedicationEntity> findByPharmacyAndMedication(PharmacyEntity pharmacy, MedicationEntity medication);
    @Query("SELECT pm FROM PharmacyMedicationEntity pm " +
            "WHERE LOWER(pm.medication.name) LIKE LOWER(CONCAT('%', :medicationName, '%')) " +
            "AND pm.stock > 0 " +
            "ORDER BY pm.pharmacy.zipCode - :zipCode ASC")
    List<PharmacyMedicationEntity> findByMedicationNameAndNearestZip(
            @Param("medicationName") String medicationName,
            @Param("zipCode") Integer zipCode);

    List<PharmacyMedicationEntity> findByPharmacy(PharmacyEntity pharmacy);

    List<PharmacyMedicationEntity> findByMedication(MedicationEntity medication);
}
