package com.portfolio.todolist.service;

import com.portfolio.todolist.dto.request.TaskRequest;
import com.portfolio.todolist.dto.response.TaskResponse;
import com.portfolio.todolist.entity.Task;
import com.portfolio.todolist.entity.User;
import com.portfolio.todolist.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela lógica de negócio relacionada a tarefas.
 * Garante isolamento de dados: usuários só podem acessar suas próprias tarefas.
 */
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    /**
     * Cria uma nova tarefa para o usuário autenticado.
     * 
     * @param request DTO com dados da tarefa
     * @param username Username do usuário autenticado
     * @return TaskResponse com os dados da tarefa criada
     */
    @Transactional
    public TaskResponse createTask(TaskRequest request, String username) {
        User user = userService.findByUsername(username);

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(request.getCompleted() != null ? request.getCompleted() : false)
                .user(user)
                .build();

        Task savedTask = taskRepository.save(task);
        return mapToResponse(savedTask);
    }

    /**
     * Busca todas as tarefas do usuário autenticado.
     * 
     * @param username Username do usuário autenticado
     * @return Lista de TaskResponse com todas as tarefas do usuário
     */
    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks(String username) {
        User user = userService.findByUsername(username);
        List<Task> tasks = taskRepository.findByUserId(user.getId());

        return tasks.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma tarefa específica do usuário autenticado.
     * Garante isolamento de dados: só retorna se a tarefa pertencer ao usuário.
     * 
     * @param taskId ID da tarefa
     * @param username Username do usuário autenticado
     * @return TaskResponse com os dados da tarefa
     * @throws RuntimeException Se a tarefa não for encontrada ou não pertencer ao usuário
     */
    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long taskId, String username) {
        User user = userService.findByUsername(username);
        Task task = taskRepository.findByIdAndUserId(taskId, user.getId())
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada ou você não tem permissão para acessá-la"));

        return mapToResponse(task);
    }

    /**
     * Atualiza uma tarefa existente do usuário autenticado.
     * Garante isolamento de dados: só atualiza se a tarefa pertencer ao usuário.
     * 
     * @param taskId ID da tarefa
     * @param request DTO com dados atualizados
     * @param username Username do usuário autenticado
     * @return TaskResponse com os dados da tarefa atualizada
     * @throws RuntimeException Se a tarefa não for encontrada ou não pertencer ao usuário
     */
    @Transactional
    public TaskResponse updateTask(Long taskId, TaskRequest request, String username) {
        User user = userService.findByUsername(username);
        Task task = taskRepository.findByIdAndUserId(taskId, user.getId())
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada ou você não tem permissão para editá-la"));

        // Atualiza apenas os campos fornecidos
        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getCompleted() != null) {
            task.setCompleted(request.getCompleted());
        }

        Task updatedTask = taskRepository.save(task);
        return mapToResponse(updatedTask);
    }

    /**
     * Remove uma tarefa do usuário autenticado.
     * Garante isolamento de dados: só remove se a tarefa pertencer ao usuário.
     * 
     * @param taskId ID da tarefa
     * @param username Username do usuário autenticado
     * @throws RuntimeException Se a tarefa não for encontrada ou não pertencer ao usuário
     */
    @Transactional
    public void deleteTask(Long taskId, String username) {
        User user = userService.findByUsername(username);
        
        if (!taskRepository.findByIdAndUserId(taskId, user.getId()).isPresent()) {
            throw new RuntimeException("Tarefa não encontrada ou você não tem permissão para excluí-la");
        }

        taskRepository.deleteByIdAndUserId(taskId, user.getId());
    }

    /**
     * Converte uma entidade Task para TaskResponse DTO.
     * 
     * @param task Entidade Task
     * @return TaskResponse DTO
     */
    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.getCompleted())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
