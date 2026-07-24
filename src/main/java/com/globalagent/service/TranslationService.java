package com.globalagent.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
public class TranslationService {

    @Value("${google-cloud.credentials-base64:}")
    private String credentialsBase64;

    private Translate translate;

    @PostConstruct
    public void init() {
        if (credentialsBase64 == null || credentialsBase64.isBlank()) {
            log.warn("GOOGLE_APPLICATION_CREDENTIALS_BASE64 not set. Translation will not work.");
            return;
        }

        try {
            byte[] decoded = Base64.getDecoder().decode(credentialsBase64);
            GoogleCredentials credentials = GoogleCredentials
                    .fromStream(new ByteArrayInputStream(decoded))
                    .createScoped("https://www.googleapis.com/auth/cloud-platform");

            TranslateOptions options = TranslateOptions.newBuilder()
                    .setCredentials(credentials)
                    .build();
            translate = options.getService();
            log.info("Google Cloud Translate initialized successfully.");
        } catch (IOException e) {
            log.error("Failed to initialize Google Cloud Translate", e);
        }
    }

    public String translate(String text, String targetLanguage) {
        if (translate == null) {
            throw new IllegalStateException("Google Cloud Translate not initialized");
        }

        try {
            Translation translation = translate.translate(text,
                    Translate.TranslateOption.targetLanguage(targetLanguage));
            return translation.getTranslatedText();
        } catch (Exception e) {
            log.error("Error translating text: {}", e.getMessage());
            throw e;
        }
    }
}
