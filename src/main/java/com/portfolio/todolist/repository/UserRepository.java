package com.portfolio.todolist.repository;

import com.portfolio.todolist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para operações de persistência da entidade User.
 * Spring Data JPA fornece implementação automática dos métodos básicos de CRUD.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca um usuário pelo nome de usuário.
     * 
     * @param username Nome de usuário
     * @return Optional contendo o usuário encontrado ou vazio
     */
    Optional<User> findByUsername(String username);

    /**
     * Busca um usuário pelo email.
     * 
     * @param email Email do usuário
     * @return Optional contendo o usuário encontrado ou vazio
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica se existe um usuário com o nome de usuário informado.
     * 
     * @param username Nome de usuário
     * @return true se existe, false caso contrário
     */
    boolean existsByUsername(String username);

    /**
     * Verifica se existe um usuário com o email informado.
     * 
     * @param email Email do usuário
     * @return true se existe, false caso contrário
     */
    boolean existsByEmail(String email);
}
