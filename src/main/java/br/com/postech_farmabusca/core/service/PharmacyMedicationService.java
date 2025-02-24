package br.com.postech_farmabusca.core.service;

import br.com.postech_farmabusca.commoms.exception.BadRequestException;
import br.com.postech_farmabusca.commoms.exception.NotFoundException;
import br.com.postech_farmabusca.commoms.exception.UnprocessableEntityException;
import br.com.postech_farmabusca.commoms.mappers.MedicationMapper;
import br.com.postech_farmabusca.commoms.mappers.PharmacyMapper;
import br.com.postech_farmabusca.commoms.mappers.PharmacyMedicationMapper;
import br.com.postech_farmabusca.core.domain.PharmacyMedication;
import br.com.postech_farmabusca.resources.entities.MedicationEntity;
import br.com.postech_farmabusca.resources.entities.PharmacyEntity;
import br.com.postech_farmabusca.resources.entities.PharmacyMedicationEntity;
import br.com.postech_farmabusca.resources.repository.PharmacyMedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PharmacyMedicationService {

    private final PharmacyMedicationRepository repository;
    private final PharmacyMedicationMapper mapper;
    private final PharmacyMapper pharmacyMapper;
    private final MedicationMapper medicationMapper;

    @Transactional
    public PharmacyMedication createStock(PharmacyMedication stock) {
        boolean exists = repository.findByPharmacyAndMedication(
                pharmacyMapper.toEntity(stock.getPharmacy()),
                medicationMapper.toEntity(stock.getMedication())
        ).isPresent();

        if (exists) {
            throw new UnprocessableEntityException("Já existe estoque cadastrado para esta farmácia e medicamento.");
        }

        if (stock.getStock() < 0) {
            throw new BadRequestException("O estoque não pode ser negativo.");
        }

        PharmacyMedicationEntity entity = mapper.toEntity(stock);
        PharmacyMedicationEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Transactional(readOnly = true)
    public PharmacyMedication getStockById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Estoque não encontrado."));
    }

    @Transactional(readOnly = true)
    public List<PharmacyMedication> getAllStocks() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public PharmacyMedication updateStock(Long id, PharmacyMedication updatedStock) {
        PharmacyMedicationEntity existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estoque não encontrado."));

        PharmacyEntity pharmacyEntity = pharmacyMapper.toEntity(updatedStock.getPharmacy());
        MedicationEntity medicationEntity = medicationMapper.toEntity(updatedStock.getMedication());

        existingEntity.setPharmacy(pharmacyEntity);
        existingEntity.setMedication(medicationEntity);
        existingEntity.setStock(updatedStock.getStock());

        PharmacyMedicationEntity updatedEntity = repository.save(existingEntity);
        return mapper.toDomain(updatedEntity);
    }
}
