package com.portfolio.todolist.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisição de login.
 * Usado para autenticar um usuário existente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "O nome de usuário ou email é obrigatório")
    private String usernameOrEmail;

    @NotBlank(message = "A senha é obrigatória")
    private String password;
}
