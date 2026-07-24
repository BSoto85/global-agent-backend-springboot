package com.globalagent.controller;

import com.globalagent.model.dto.RegisterRequest;
import com.globalagent.model.dto.UserDto;
import com.globalagent.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterRequest request) {
        UserDto user = authService.register(request);
        return ResponseEntity.ok(Map.of("user", user));
    }

    @GetMapping("/user/{uid}")
    public ResponseEntity<UserDto> getUserByUid(@PathVariable String uid) {
        UserDto user = authService.getUserByUid(uid);
        return ResponseEntity.ok(user);
    }
}
