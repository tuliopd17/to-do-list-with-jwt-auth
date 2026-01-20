package com.portfolio.todolist.repository;

import com.portfolio.todolist.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para operações de persistência da entidade Task.
 * Spring Data JPA fornece implementação automática dos métodos básicos de CRUD.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Busca todas as tarefas de um usuário específico.
     * 
     * @param userId ID do usuário
     * @return Lista de tarefas do usuário
     */
    List<Task> findByUserId(Long userId);

    /**
     * Busca uma tarefa específica de um usuário.
     * Garante isolamento de dados: um usuário só pode acessar suas próprias tarefas.
     * 
     * @param id ID da tarefa
     * @param userId ID do usuário
     * @return Optional contendo a tarefa encontrada ou vazio
     */
    Optional<Task> findByIdAndUserId(Long id, Long userId);

    /**
     * Remove uma tarefa específica de um usuário.
     * Garante isolamento de dados na exclusão.
     * 
     * @param id ID da tarefa
     * @param userId ID do usuário
     */
    void deleteByIdAndUserId(Long id, Long userId);
}
