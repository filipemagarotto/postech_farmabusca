package br.com.postech_farmabusca.controller;

import br.com.postech_farmabusca.controller.dto.user.UserRequestDTO;
import br.com.postech_farmabusca.controller.dto.user.UserResponseDTO;
import br.com.postech_farmabusca.core.domain.User;
import br.com.postech_farmabusca.core.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PatchMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody @Valid UserRequestDTO request) {
        return service.updateUser(request);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO getUserData() {
        return service.getAuthenticatedUserData();
    }
}
