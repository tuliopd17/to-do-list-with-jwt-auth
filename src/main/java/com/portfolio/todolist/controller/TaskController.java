package com.portfolio.todolist.controller;

import com.portfolio.todolist.dto.request.TaskRequest;
import com.portfolio.todolist.dto.response.TaskResponse;
import com.portfolio.todolist.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelos endpoints de gerenciamento de tarefas.
 * Todos os endpoints são protegidos e requerem autenticação JWT.
 * Usuários só podem acessar suas próprias tarefas (isolamento de dados).
 */
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * Endpoint para criar uma nova tarefa.
     * 
     * @param request DTO com dados da tarefa
     * @param authentication Objeto de autenticação do Spring Security (contém username)
     * @return ResponseEntity com a tarefa criada
     */
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        TaskResponse response = taskService.createTask(request, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint para listar todas as tarefas do usuário autenticado.
     * 
     * @param authentication Objeto de autenticação do Spring Security
     * @return ResponseEntity com lista de tarefas do usuário
     */
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(Authentication authentication) {
        String username = authentication.getName();
        List<TaskResponse> tasks = taskService.getAllTasks(username);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Endpoint para buscar uma tarefa específica por ID.
     * 
     * @param id ID da tarefa
     * @param authentication Objeto de autenticação do Spring Security
     * @return ResponseEntity com a tarefa encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable Long id,
            Authentication authentication) {
        String username = authentication.getName();
        TaskResponse response = taskService.getTaskById(id, username);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para atualizar uma tarefa existente.
     * 
     * @param id ID da tarefa
     * @param request DTO com dados atualizados
     * @param authentication Objeto de autenticação do Spring Security
     * @return ResponseEntity com a tarefa atualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        TaskResponse response = taskService.updateTask(id, request, username);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para deletar uma tarefa.
     * 
     * @param id ID da tarefa
     * @param authentication Objeto de autenticação do Spring Security
     * @return ResponseEntity sem conteúdo (204 No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id,
            Authentication authentication) {
        String username = authentication.getName();
        taskService.deleteTask(id, username);
        return ResponseEntity.noContent().build();
    }
}
