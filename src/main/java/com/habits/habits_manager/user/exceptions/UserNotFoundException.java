package com.habits.habits_manager.user.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) { super(message);}
    public UserNotFoundException(Long id) { super("Resource not found. id:  " + id);}
}
