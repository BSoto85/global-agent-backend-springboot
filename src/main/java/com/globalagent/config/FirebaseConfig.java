package com.globalagent.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Configuration
public class FirebaseConfig {

    private static final Logger log = LoggerFactory.getLogger(FirebaseConfig.class);

    @Value("${firebase.service-account:}")
    private String serviceAccountBase64;

    @PostConstruct
    public void init() {
        if (serviceAccountBase64 == null || serviceAccountBase64.isBlank()) {
            log.warn("FIREBASE_SERVICE_ACCOUNT not set. Firebase auth will not work.");
            return;
        }

        try {
            byte[] decoded = Base64.getDecoder().decode(serviceAccountBase64);
            GoogleCredentials credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(decoded));

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase Admin SDK initialized successfully.");
            }
        } catch (IOException e) {
            log.error("Failed to initialize Firebase Admin SDK", e);
            throw new RuntimeException("Failed to initialize Firebase", e);
        }
    }
}
