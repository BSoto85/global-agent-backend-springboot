package com.globalagent.repository;

import com.globalagent.model.entity.QuestionOlder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionOlderRepository extends JpaRepository<QuestionOlder, Long> {

    List<QuestionOlder> findByCaseFileArticleId(Integer articleId);

    List<QuestionOlder> findByCaseFileId(Long caseFileId);
}
