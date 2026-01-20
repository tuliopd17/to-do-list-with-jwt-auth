package com.portfolio.todolist.service;

import com.portfolio.todolist.dto.request.RegisterRequest;
import com.portfolio.todolist.entity.User;
import com.portfolio.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável pela lógica de negócio relacionada a usuários.
 * Implementa UserDetailsService para integração com Spring Security.
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Carrega um usuário pelo nome de usuário para autenticação.
     * Método obrigatório da interface UserDetailsService.
     * 
     * @param username Nome de usuário
     * @return UserDetails do usuário encontrado
     * @throws UsernameNotFoundException Se o usuário não for encontrado
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_USER")
                .build();
    }

    /**
     * Registra um novo usuário no sistema.
     * Valida se username e email já existem antes de criar.
     * 
     * @param request DTO com dados de registro
     * @return Usuário criado
     * @throws IllegalArgumentException Se username ou email já existirem
     */
    @Transactional
    public User register(RegisterRequest request) {
        // Validação de username único
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Nome de usuário já está em uso");
        }

        // Validação de email único
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso");
        }

        // Criação do novo usuário com senha criptografada
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return userRepository.save(user);
    }

    /**
     * Busca um usuário pelo nome de usuário.
     * 
     * @param username Nome de usuário
     * @return Usuário encontrado
     * @throws UsernameNotFoundException Se o usuário não for encontrado
     */
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    /**
     * Busca um usuário pelo email ou username.
     * Útil para login que aceita ambos.
     * 
     * @param usernameOrEmail Username ou email
     * @return Usuário encontrado
     * @throws UsernameNotFoundException Se o usuário não for encontrado
     */
    @Transactional(readOnly = true)
    public User findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + usernameOrEmail));
    }
}
