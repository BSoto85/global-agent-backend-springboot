package com.globalagent.service;

import com.globalagent.util.AiPrompts;
import com.globalagent.repository.CaseFileRepository;
import com.globalagent.model.entity.CaseFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiSummaryService {

    @Value("${anthropic.api-key}")
    private String anthropicApiKey;

    private final CaseFileRepository caseFileRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String ANTHROPIC_API_URL = "https://api.anthropic.com/v1/messages";
    private static final String MODEL = "claude-sonnet-4-6";

    public record SummaryResult(String youngerSummary, String olderSummary, Integer articleId) {}

    public SummaryResult generateSummary(String articleContent, Integer articleId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key", anthropicApiKey);
            headers.set("anthropic-version", "2023-06-01");

            Map<String, Object> body = Map.of(
                    "model", MODEL,
                    "max_tokens", 4096,
                    "temperature", 0,
                    "system", AiPrompts.SUMMARY_SYSTEM_PROMPT,
                    "messages", new Object[]{
                            Map.of("role", "user", "content", articleContent)
                    }
            );

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    ANTHROPIC_API_URL, request, Map.class);

            String responseText = extractContent(response);

            caseFileRepository.findByArticleId(articleId).ifPresent(cf -> {
                cf.setSummaryYoung(responseText);
                cf.setSummaryOld(responseText);
                caseFileRepository.save(cf);
            });

            return new SummaryResult(responseText, responseText, articleId);
        } catch (Exception e) {
            log.error("Error generating summary for article {}: {}", articleId, e.getMessage(), e);
            throw new RuntimeException("Summary failed for article " + articleId + ": " + e.getMessage(), e);
        }
    }

    private String extractContent(ResponseEntity<Map> response) {
        if (response.getBody() == null) throw new RuntimeException("Empty response from Claude");

        var content = (java.util.List<Map<String, String>>) response.getBody().get("content");
        return content.get(0).get("text");
    }
}
