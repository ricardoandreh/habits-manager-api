package com.habits.habits_manager.user.service;

import com.habits.habits_manager.genericExceptions.DatabaseException;
import com.habits.habits_manager.user.exceptions.UserNotFoundException;
import com.habits.habits_manager.user.model.UserModel;
import com.habits.habits_manager.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<UserModel> findAll() {
        return repository.findAll();
    }

    public UserModel findById(String id) {
        Optional<UserModel> obj = repository.findById(id);
        return obj.orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserModel insert(UserModel obj) {
        return repository.save(obj);
    }

    public void delete(String id) {
        try {
            if(repository.existsById(id)) {
                repository.deleteById(id);
            } else {
                throw new UserNotFoundException(id);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public UserModel update(String id, UserModel obj) {
        if (repository.existsById(id)) {
            UserModel entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        }
        return null;
    }

    public void updateData(UserModel entity, UserModel obj) {
        entity.setFirstName(obj.getFirstName());
        entity.setLastName(obj.getLastName());
        entity.setEmail(obj.getEmail());
        entity.setRole(obj.getRole());
    }
}
