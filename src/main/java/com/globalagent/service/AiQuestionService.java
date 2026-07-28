package com.globalagent.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globalagent.util.AiPrompts;
import com.globalagent.model.entity.CaseFile;
import com.globalagent.model.entity.QuestionYounger;
import com.globalagent.model.entity.QuestionOlder;
import com.globalagent.repository.CaseFileRepository;
import com.globalagent.repository.QuestionYoungerRepository;
import com.globalagent.repository.QuestionOlderRepository;
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
public class AiQuestionService {

    @Value("${anthropic.api-key}")
    private String anthropicApiKey;

    private final CaseFileRepository caseFileRepository;
    private final QuestionYoungerRepository youngerRepository;
    private final QuestionOlderRepository olderRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String ANTHROPIC_API_URL = "https://api.anthropic.com/v1/messages";
    private static final String MODEL = "claude-sonnet-4-6";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public record QuestionResult(int articleId, int youngerCount, int olderCount) {}

    public QuestionResult generateAndStoreQuestions(String summaryYoung, String summaryOld, Integer articleId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key", anthropicApiKey);
            headers.set("anthropic-version", "2023-06-01");

            Map<String, Object> body = Map.of(
                    "model", MODEL,
                    "max_tokens", 4096,
                    "temperature", 0,
                    "system", AiPrompts.QUESTIONS_SYSTEM_PROMPT,
                    "messages", new Object[]{
                            Map.of("role", "user", "content", summaryYoung + summaryOld)
                    }
            );

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    ANTHROPIC_API_URL, request, Map.class);

            String responseText = extractContent(response);
            responseText = responseText.replaceAll("```json\\s*", "").replaceAll("```\\s*", "").trim();
            JsonNode root;
            try {
                root = objectMapper.readTree(responseText);
            } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                throw new RuntimeException("Failed to parse Claude response. Raw: " + responseText, e);
            }

            CaseFile caseFile = caseFileRepository.findByArticleId(articleId)
                    .orElseThrow(() -> new RuntimeException("Case file not found: " + articleId));

            int youngerCount = 0;
            int olderCount = 0;

            JsonNode youngerQuestions = root.get("questionsForYounger");
            if (youngerQuestions != null && youngerQuestions.isArray()) {
                for (JsonNode q : youngerQuestions) {
                    JsonNode answers = q.get("answers");
                    if (answers == null || answers.size() < 4) continue;

                    QuestionYounger question = QuestionYounger.builder()
                            .question(q.get("question").asText())
                            .correctAnswer(answers.get(0).asText())
                            .incorrectAnswer1(answers.get(1).asText())
                            .incorrectAnswer2(answers.get(2).asText())
                            .incorrectAnswer3(answers.get(3).asText())
                            .caseFile(caseFile)
                            .build();
                    youngerRepository.save(question);
                    youngerCount++;
                }
            }

            JsonNode olderQuestions = root.get("questionsForOlder");
            if (olderQuestions != null && olderQuestions.isArray()) {
                for (JsonNode q : olderQuestions) {
                    JsonNode answers = q.get("answers");
                    if (answers == null || answers.size() < 4) continue;

                    QuestionOlder question = QuestionOlder.builder()
                            .question(q.get("question").asText())
                            .correctAnswer(answers.get(0).asText())
                            .incorrectAnswer1(answers.get(1).asText())
                            .incorrectAnswer2(answers.get(2).asText())
                            .incorrectAnswer3(answers.get(3).asText())
                            .caseFile(caseFile)
                            .build();
                    olderRepository.save(question);
                    olderCount++;
                }
            }

            return new QuestionResult(articleId, youngerCount, olderCount);
        } catch (Exception e) {
            log.error("Error generating questions for article {}: {}", articleId, e.getMessage(), e);
            throw new RuntimeException("Questions failed for article " + articleId + ": " + e.getMessage(), e);
        }
    }

    private String extractContent(ResponseEntity<Map> response) {
        if (response.getBody() == null) throw new RuntimeException("Empty response from Claude");
        var content = (java.util.List<Map<String, Object>>) response.getBody().get("content");
        for (Map<String, Object> block : content) {
            if ("text".equals(block.get("type"))) {
                return (String) block.get("text");
            }
        }
        throw new RuntimeException("No text block in Claude response");
    }
}
