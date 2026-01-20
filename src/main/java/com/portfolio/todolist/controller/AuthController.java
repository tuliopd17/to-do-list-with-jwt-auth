package com.portfolio.todolist.controller;

import com.portfolio.todolist.dto.request.LoginRequest;
import com.portfolio.todolist.dto.request.RegisterRequest;
import com.portfolio.todolist.dto.response.AuthResponse;
import com.portfolio.todolist.entity.User;
import com.portfolio.todolist.service.JwtService;
import com.portfolio.todolist.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsável pelos endpoints de autenticação.
 * Endpoints públicos: /auth/register e /auth/login
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Endpoint para registro de novo usuário.
     * 
     * @param request DTO com dados de registro
     * @return ResponseEntity com token JWT e mensagem de sucesso
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        // Cria o novo usuário
        User user = userService.register(request);

        // Gera token JWT
        String token = jwtService.generateToken(user.getUsername());

        AuthResponse response = AuthResponse.builder()
                .token(token)
                .message("Usuário registrado com sucesso")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint para login de usuário existente.
     * Aceita username ou email para autenticação.
     * 
     * @param request DTO com credenciais de login
     * @return ResponseEntity com token JWT e mensagem de sucesso
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        // Busca o usuário por username ou email
        User user = userService.findByUsernameOrEmail(request.getUsernameOrEmail());

        // Autentica usando Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        request.getPassword()
                )
        );

        // Gera token JWT
        String token = jwtService.generateToken(user.getUsername());

        AuthResponse response = AuthResponse.builder()
                .token(token)
                .message("Login realizado com sucesso")
                .build();

        return ResponseEntity.ok(response);
    }
}
