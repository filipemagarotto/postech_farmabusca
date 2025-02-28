package br.com.postech_farmabusca.core.service;

import br.com.postech_farmabusca.commoms.exception.NotFoundException;
import br.com.postech_farmabusca.commoms.mappers.PharmacyMapper;
import br.com.postech_farmabusca.core.domain.Pharmacy;
import br.com.postech_farmabusca.resources.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private final PharmacyMapper mapper;

    public Pharmacy createPharmacy(Pharmacy pharmacy) {
        return mapper.toDomain(pharmacyRepository.save(mapper.toEntity(pharmacy)));
    }

    public Pharmacy getPharmacy(Long id) {
        return pharmacyRepository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Farmácia não encontrada."));
    }

    public List<Pharmacy> getAllPharmacies() {
        return pharmacyRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public Pharmacy updatePharmacy(Long id, Pharmacy updatedPharmacy) {
        Pharmacy pharmacy = getPharmacy(id);

        if (updatedPharmacy.getName() != null) {
            pharmacy.setName(updatedPharmacy.getName());
        }
        if (updatedPharmacy.getZipCode() != null) {
            pharmacy.setZipCode(updatedPharmacy.getZipCode());
        }

        return mapper.toDomain(pharmacyRepository.save(mapper.toEntity(pharmacy)));
    }

    @Transactional
    public void deletePharmacy(Long id) {
        Pharmacy pharmacy = getPharmacy(id);
        pharmacyRepository.deleteById(pharmacy.getId());
    }
}
