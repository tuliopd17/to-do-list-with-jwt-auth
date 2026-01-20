package com.portfolio.todolist.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisições de criação e atualização de tarefas.
 * Usado para receber dados do cliente sem expor a entidade Task diretamente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequest {

    @NotBlank(message = "O título da tarefa é obrigatório")
    @Size(min = 1, max = 200, message = "O título deve ter entre 1 e 200 caracteres")
    private String title;

    @Size(max = 1000, message = "A descrição não pode exceder 1000 caracteres")
    private String description;

    private Boolean completed;
}
