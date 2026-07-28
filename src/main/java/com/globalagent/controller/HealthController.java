package com.globalagent.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    @Value("${anthropic.api-key:}")
    private String anthropicKey;

    @Value("${news-api.api-key:}")
    private String newsApiKey;

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of("message", "Welcome to Global Agent Backend Server"));
    }

    @GetMapping("/api/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "ok",
                "anthropicKeySet", anthropicKey != null && !anthropicKey.isBlank(),
                "newsApiKeySet", newsApiKey != null && !newsApiKey.isBlank()
        ));
    }
}
