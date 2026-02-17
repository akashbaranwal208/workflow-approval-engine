package com.workflow.approval.service;

import com.workflow.approval.entity.User;
import com.workflow.approval.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user)
    {
        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUser()
    {
        return userRepository.findAll();
    }
}
