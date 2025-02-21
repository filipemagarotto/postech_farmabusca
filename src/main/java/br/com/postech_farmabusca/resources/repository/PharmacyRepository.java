package br.com.postech_farmabusca.resources.repository;

import br.com.postech_farmabusca.resources.entities.PharmacyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyRepository extends JpaRepository<PharmacyEntity, Long> {
}
