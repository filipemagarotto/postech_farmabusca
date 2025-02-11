package br.com.postech_farmabusca.controller;

import br.com.postech_farmabusca.commoms.exception.EmailAlreadyExistsException;
import br.com.postech_farmabusca.commoms.exception.ForbiddenException;
import br.com.postech_farmabusca.commoms.exception.InvalidCredentialsException;
import br.com.postech_farmabusca.commoms.exception.UnauthorizedException;
import br.com.postech_farmabusca.core.domain.UserType;
import br.com.postech_farmabusca.resources.entities.UserEntity;
import br.com.postech_farmabusca.resources.repository.UserRepository;
import br.com.postech_farmabusca.security.TokenService;
import br.com.postech_farmabusca.security.request.UserAuthRequest;
import br.com.postech_farmabusca.security.request.UserRequest;
import br.com.postech_farmabusca.security.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse login(@RequestBody @Valid UserAuthRequest data){
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((UserEntity) auth.getPrincipal(), data.keepLoggedIn());

            return new UserResponse(token);
        } catch (Exception e) {
            throw new InvalidCredentialsException("Email ou senha inválidos.");
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid UserRequest data) {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(data.name());

        if (auth == null || !auth.isAuthenticated()) {
            throw new UnauthorizedException("Usuário não autenticado.");
        }

        if (data.role() == UserType.ADMIN) {
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

            if (!isAdmin) {
                throw new ForbiddenException("Somente administradores podem criar usuários com role ADMIN.");
            }
        }

        if (this.repository.findByEmail(data.email()).isPresent()) {
            throw new EmailAlreadyExistsException("O email já está em uso.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserEntity newUser = new UserEntity(
                data.email(),
                encryptedPassword,
                data.role(),
                data.name(),
                data.street(),
                data.number(),
                data.complement(),
                data.neighborhood(),
                data.city(),
                data.state(),
                data.zipCode(),
                data.country());
        this.repository.save(newUser);
    }
}


