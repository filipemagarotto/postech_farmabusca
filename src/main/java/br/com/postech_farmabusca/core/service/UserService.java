package br.com.postech_farmabusca.core.service;

import br.com.postech_farmabusca.commoms.exception.EmailAlreadyExistsException;
import br.com.postech_farmabusca.commoms.exception.UnauthorizedException;
import br.com.postech_farmabusca.commoms.mappers.UserMapper;
import br.com.postech_farmabusca.controller.dto.user.UserRequestDTO;
import br.com.postech_farmabusca.controller.dto.user.UserResponseDTO;
import br.com.postech_farmabusca.core.domain.User;
import br.com.postech_farmabusca.core.domain.UserType;
import br.com.postech_farmabusca.resources.entities.UserEntity;
import br.com.postech_farmabusca.resources.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Transactional
    public User updateUser(UserRequestDTO request) {
        User user = getAuthenticatedUserEntity();

        if(request.email() != null && !request.email().trim().isEmpty()) {
            if (this.repository.findByEmail(request.email()).isPresent()) {
                throw new EmailAlreadyExistsException("O email já está em uso.");
            }
        }

        mapper.updateUserFromRequest(request, mapper.toEntity(user));

        if (request.password() != null && !request.password().trim().isEmpty()) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(request.password());
            user.setPassword(encryptedPassword);
        }

        UserEntity updatedUser = repository.save(mapper.toEntity(user));

        return mapper.toDomain(updatedUser);
    }

    public UserResponseDTO getAuthenticatedUserData() {
        User user = getAuthenticatedUserEntity();
        return mapper.toResponse(user);
    }

    private User getAuthenticatedUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Usuário não autenticado.");
        }

        return (User) authentication.getPrincipal();
    }
}
