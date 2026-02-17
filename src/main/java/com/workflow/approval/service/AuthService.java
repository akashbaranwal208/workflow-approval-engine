package com.workflow.approval.service;

import com.workflow.approval.entity.User;
import com.workflow.approval.exception.BusinessException;
import com.workflow.approval.exception.ResourceNotFoundException;
import com.workflow.approval.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String email, String password)
    {
        User user=userRepository.findByEmail(email)
                    .orElseThrow(()->new ResourceNotFoundException("User not found with email" +  email));

        if(!user.getPassword().equals(password))
        {
            throw new BusinessException("Invalid Email  and Password");
        }

        return user;

    }
}
