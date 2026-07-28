package com.globalagent.repository;

import com.globalagent.model.entity.CaseFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CaseFileRepository extends JpaRepository<CaseFile, Long> {

    List<CaseFile> findByCountryId(Long countryId);

    Optional<CaseFile> findByArticleId(Integer articleId);

    @Query(value = "SELECT publish_date FROM case_files ORDER BY publish_date DESC LIMIT 1",
            nativeQuery = true)
    Optional<String> findLatestPublishDate();

    @Query("SELECT cf FROM CaseFile cf ORDER BY cf.id DESC LIMIT 1")
    Optional<CaseFile> findLatest();

    @Query("SELECT cf FROM CaseFile cf WHERE cf.summaryYoung IS NULL")
    List<CaseFile> findBySummaryYoungIsNull();
}
