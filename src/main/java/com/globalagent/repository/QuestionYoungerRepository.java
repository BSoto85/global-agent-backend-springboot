package com.globalagent.repository;

import com.globalagent.model.entity.QuestionYounger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionYoungerRepository extends JpaRepository<QuestionYounger, Long> {

    List<QuestionYounger> findByCaseFileArticleId(Integer articleId);

    List<QuestionYounger> findByCaseFileId(Long caseFileId);
}
