package com.globalagent.service;

import com.globalagent.model.dto.QuestionDto;
import com.globalagent.model.entity.CaseFile;
import com.globalagent.repository.CaseFileRepository;
import com.globalagent.repository.QuestionOlderRepository;
import com.globalagent.repository.QuestionYoungerRepository;
import com.globalagent.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final CaseFileRepository caseFileRepository;
    private final QuestionYoungerRepository youngerRepository;
    private final QuestionOlderRepository olderRepository;

    @Transactional(readOnly = true)
    public Optional<CaseFile> getCaseFileByArticleId(Integer articleId) {
        return caseFileRepository.findByArticleId(articleId);
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> getAllYoungerQuestions() {
        return youngerRepository.findAll().stream()
                .map(DtoMapper::toQuestionYoungerDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> getYoungerQuestionsByArticleId(Integer articleId) {
        return youngerRepository.findByCaseFileArticleId(articleId).stream()
                .map(DtoMapper::toQuestionYoungerDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> getYoungerQuestionsByCaseFileId(Long caseFileId) {
        return youngerRepository.findByCaseFileId(caseFileId).stream()
                .map(DtoMapper::toQuestionYoungerDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> getAllOlderQuestions() {
        return olderRepository.findAll().stream()
                .map(DtoMapper::toQuestionOlderDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> getOlderQuestionsByArticleId(Integer articleId) {
        return olderRepository.findByCaseFileArticleId(articleId).stream()
                .map(DtoMapper::toQuestionOlderDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> getOlderQuestionsByCaseFileId(Long caseFileId) {
        return olderRepository.findByCaseFileId(caseFileId).stream()
                .map(DtoMapper::toQuestionOlderDto)
                .toList();
    }
}
