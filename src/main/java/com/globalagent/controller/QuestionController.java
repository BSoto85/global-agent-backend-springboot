package com.globalagent.controller;

import com.globalagent.model.dto.QuestionDto;
import com.globalagent.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/younger_questions")
    public ResponseEntity<List<QuestionDto>> getAllYoungerQuestions() {
        return ResponseEntity.ok(questionService.getAllYoungerQuestions());
    }

    @GetMapping("/younger_questions/{articleId}")
    public ResponseEntity<List<QuestionDto>> getYoungerQuestionsByArticleId(@PathVariable Integer articleId) {
        return ResponseEntity.ok(questionService.getYoungerQuestionsByArticleId(articleId));
    }

    @GetMapping("/older_questions")
    public ResponseEntity<List<QuestionDto>> getAllOlderQuestions() {
        return ResponseEntity.ok(questionService.getAllOlderQuestions());
    }

    @GetMapping("/older_questions/{caseFileId}")
    public ResponseEntity<List<QuestionDto>> getOlderQuestionsByCaseFileId(@PathVariable Long caseFileId) {
        return ResponseEntity.ok(questionService.getOlderQuestionsByCaseFileId(caseFileId));
    }

    @GetMapping("/older_questions/by-article/{articleId}")
    public ResponseEntity<List<QuestionDto>> getOlderQuestionsByArticleId(@PathVariable Integer articleId) {
        var caseFile = questionService.getCaseFileByArticleId(articleId);
        if (caseFile.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        return ResponseEntity.ok(questionService.getOlderQuestionsByCaseFileId(caseFile.get().getId()));
    }
}
