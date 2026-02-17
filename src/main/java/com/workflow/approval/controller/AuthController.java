package com.workflow.approval.controller;

import com.workflow.approval.dto.LoginRequest;
import com.workflow.approval.entity.User;
import com.workflow.approval.security.JwtUtil;
import com.workflow.approval.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        User user=authService.authenticate(
                loginRequest.getEmail(),
                loginRequest.getPassword());
        System.out.println("Before token generation");
        String token= JwtUtil.generateToken(user);
        System.out.println("After token generation");
        return ResponseEntity.ok(token);
    }
}
