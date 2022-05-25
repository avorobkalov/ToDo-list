package com.softserve.itacademy.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Getter
@Setter
public class TaskDto {
    private long id;

    @NotBlank(message = "The 'name' cannot be empty")
    private String name;

    @NotNull
    private String priority;

    @NotNull
    private long todoId;

    @NotNull
    private long stateId;

    public TaskDto() {
    }

    public TaskDto(long id, String name, String priority, long todoId, long stateId) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.todoId = todoId;
        this.stateId = stateId;
    }
}
