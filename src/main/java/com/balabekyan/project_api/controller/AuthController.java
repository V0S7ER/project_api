package com.balabekyan.project_api.controller;

import com.balabekyan.project_api.model.db.User;
import com.balabekyan.project_api.model.dto.request.LoginRequest;
import com.balabekyan.project_api.model.dto.request.RegisterRequest;
import com.balabekyan.project_api.model.dto.response.StatusResponse;
import com.balabekyan.project_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Регистрация
     * Логин
     */

    @PostMapping("/register")
    private synchronized ResponseEntity<StatusResponse> register(RegisterRequest request) {
        StatusResponse response = authService.register(request);
        return response.getStatus().equals("OK") ? ResponseEntity.ok(response) : new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    private synchronized ResponseEntity<User> login(LoginRequest request) {
        User user = authService.login(request);
        return user == null ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : ResponseEntity.ok(user);
    }
}
