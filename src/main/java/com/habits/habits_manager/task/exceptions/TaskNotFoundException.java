package com.habits.habits_manager.task.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) { super("Task not found. id:  " + id);}
}
