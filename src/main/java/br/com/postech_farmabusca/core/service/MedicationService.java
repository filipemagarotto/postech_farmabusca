package br.com.postech_farmabusca.core.service;

import br.com.postech_farmabusca.commoms.exception.ForbiddenException;
import br.com.postech_farmabusca.commoms.exception.NotFoundException;
import br.com.postech_farmabusca.commoms.exception.UnprocessableEntityException;
import br.com.postech_farmabusca.commoms.mappers.MedicationMapper;
import br.com.postech_farmabusca.core.domain.Medication;
import br.com.postech_farmabusca.core.domain.User;
import br.com.postech_farmabusca.core.domain.UserType;
import br.com.postech_farmabusca.resources.entities.UserEntity;
import br.com.postech_farmabusca.resources.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private final MedicationMapper mapper;

    @Transactional
    public Medication createMedication(Medication medication) {
        ensureAdminAccess();

        return mapper.toDomain(medicationRepository.save(mapper.toEntity(medication)));
    }

    public Medication getMedication(Long id) {
        return medicationRepository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Medicamento não encontrado."));
    }

    public List<Medication> getAllMedications() {
        return medicationRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public Medication updateMedication(Long id, Medication updatedMedication) {
        ensureAdminAccess();

        Medication medication = getMedication(id);

        if (updatedMedication.getName() != null && !updatedMedication.getName().trim().isEmpty()) {
            medication.setName(updatedMedication.getName());
        }
        if (updatedMedication.getDescription() != null) {
            medication.setDescription(updatedMedication.getDescription());
        }
        if (updatedMedication.getDosage() != null) {
            medication.setDosage(updatedMedication.getDosage());
        }
        if (updatedMedication.getForm() != null) {
            medication.setForm(updatedMedication.getForm());
        }

        return mapper.toDomain(medicationRepository.save(mapper.toEntity(medication)));
    }

    @Transactional
    public void deleteMedication(Long id) {
        ensureAdminAccess();

        Medication medication = getMedication(id);
        medicationRepository.deleteById(medication.getId());
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
