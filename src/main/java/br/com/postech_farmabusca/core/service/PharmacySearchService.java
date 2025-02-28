package br.com.postech_farmabusca.core.service;

import br.com.postech_farmabusca.resources.entities.PharmacyMedicationEntity;
import br.com.postech_farmabusca.resources.repository.PharmacyMedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PharmacySearchService {

    private final PharmacyMedicationRepository pharmacyMedicationRepository;

    public List<PharmacyMedicationEntity> searchPharmacies(String medicationName, Integer zipCode) {
        return pharmacyMedicationRepository.findByMedicationNameAndNearestZip(medicationName, zipCode)
                .stream()
                .limit(5)
                .toList();
    }
}
