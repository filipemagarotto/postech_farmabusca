package br.com.postech_farmabusca.core.service;

import br.com.postech_farmabusca.commoms.exception.BadRequestException;
import br.com.postech_farmabusca.commoms.exception.ForbiddenException;
import br.com.postech_farmabusca.commoms.exception.NotFoundException;
import br.com.postech_farmabusca.commoms.exception.UnprocessableEntityException;
import br.com.postech_farmabusca.commoms.mappers.MedicationMapper;
import br.com.postech_farmabusca.commoms.mappers.PharmacyMapper;
import br.com.postech_farmabusca.commoms.mappers.PharmacyMedicationMapper;
import br.com.postech_farmabusca.controller.dto.pharmacymedication.CreatePharmacyMedicationRequest;
import br.com.postech_farmabusca.controller.dto.pharmacymedication.UpdatePharmacyMedicationRequest;
import br.com.postech_farmabusca.core.domain.PharmacyMedication;
import br.com.postech_farmabusca.core.domain.User;
import br.com.postech_farmabusca.core.domain.UserType;
import br.com.postech_farmabusca.resources.entities.MedicationEntity;
import br.com.postech_farmabusca.resources.entities.PharmacyEntity;
import br.com.postech_farmabusca.resources.entities.PharmacyMedicationEntity;
import br.com.postech_farmabusca.resources.entities.UserEntity;
import br.com.postech_farmabusca.resources.repository.MedicationRepository;
import br.com.postech_farmabusca.resources.repository.PharmacyMedicationRepository;
import br.com.postech_farmabusca.resources.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PharmacyMedicationService {

    private final PharmacyMedicationRepository repository;
    private final PharmacyRepository pharmacyRepository;
    private final MedicationRepository medicationRepository;
    private final PharmacyMedicationMapper mapper;
    private final PharmacyMapper pharmacyMapper;
    private final MedicationMapper medicationMapper;

    @Transactional
    public PharmacyMedication createStock(CreatePharmacyMedicationRequest request) {
        ensureAdminAccess();

        PharmacyEntity pharmacy = pharmacyRepository.findById(request.getPharmacyId())
                .orElseThrow(() -> new NotFoundException("Farmácia não encontrada com ID: " + request.getPharmacyId()));

        MedicationEntity medication = medicationRepository.findById(request.getMedicationId())
                .orElseThrow(() -> new NotFoundException("Medicamento não encontrado com ID: " + request.getMedicationId()));

        boolean exists = repository.findByPharmacyAndMedication(pharmacy, medication).isPresent();
        if (exists) {
            throw new UnprocessableEntityException("Já existe estoque cadastrado para esta farmácia e medicamento.");
        }

        if (request.getStock() < 0) {
            throw new BadRequestException("O estoque não pode ser negativo.");
        }

        PharmacyMedicationEntity entity = new PharmacyMedicationEntity();
        entity.setPharmacy(pharmacy);
        entity.setMedication(medication);
        entity.setStock(request.getStock());

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
    public List<PharmacyMedication> getStockByPharmacyAndMedication(Long pharmacyId, Long medicationId) {
        PharmacyEntity pharmacy = pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() -> new NotFoundException("Farmácia não encontrada com ID: " + pharmacyId));

        MedicationEntity medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new NotFoundException("Medicamento não encontrado com ID: " + medicationId));

        return repository.findByPharmacyAndMedication(pharmacy, medication).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PharmacyMedication> getStockByPharmacy(Long pharmacyId) {
        PharmacyEntity pharmacy = pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() -> new NotFoundException("Farmácia não encontrada com ID: " + pharmacyId));

        return repository.findByPharmacy(pharmacy).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PharmacyMedication> getStockByMedication(Long medicationId) {
        MedicationEntity medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new NotFoundException("Medicamento não encontrado com ID: " + medicationId));

        return repository.findByMedication(medication).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PharmacyMedication> getAllStocks() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public PharmacyMedication updateStock(Long id, UpdatePharmacyMedicationRequest request) {
        ensureAdminAccess();

        PharmacyMedicationEntity existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estoque não encontrado."));

        if (request.getStock() < 0) {
            throw new BadRequestException("O estoque não pode ser negativo.");
        }

        existingEntity.setStock(request.getStock());
        existingEntity.setUpdatedAt(LocalDateTime.now());

        PharmacyMedicationEntity updatedEntity = repository.save(existingEntity);
        return mapper.toDomain(updatedEntity);
    }

    private void ensureAdminAccess() {
        User user = getAuthenticatedUser();
        if (!user.getRole().equals(UserType.ADMIN)) {
            throw new ForbiddenException("Acesso negado: Apenas administradores podem executar esta ação.");
        }
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ForbiddenException("Usuário não autenticado. Faça login como ADMIN para continuar.");
        }

        return (User) authentication.getPrincipal();
    }
}
