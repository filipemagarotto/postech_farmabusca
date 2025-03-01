package br.com.postech_farmabusca.core.service;

import br.com.postech_farmabusca.commoms.exception.ForbiddenException;
import br.com.postech_farmabusca.commoms.exception.NotFoundException;
import br.com.postech_farmabusca.commoms.mappers.PharmacyMapper;
import br.com.postech_farmabusca.core.domain.Pharmacy;
import br.com.postech_farmabusca.core.domain.User;
import br.com.postech_farmabusca.core.domain.UserType;
import br.com.postech_farmabusca.resources.entities.UserEntity;
import br.com.postech_farmabusca.resources.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private final PharmacyMapper mapper;

    @Transactional
    public Pharmacy createPharmacy(Pharmacy pharmacy) {
        ensureAdminAccess();

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
        ensureAdminAccess();

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
        ensureAdminAccess();

        Pharmacy pharmacy = getPharmacy(id);
        pharmacyRepository.deleteById(pharmacy.getId());
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
