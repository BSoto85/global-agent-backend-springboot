package com.globalagent.service;

import com.globalagent.model.entity.CaseFile;
import com.globalagent.model.entity.Country;
import com.globalagent.repository.CaseFileRepository;
import com.globalagent.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsPipelineService {

    private final CountryRepository countryRepository;
    private final CaseFileRepository caseFileRepository;
    private final WorldNewsService worldNewsService;
    private final TranslationService translationService;
    private final CaseFileService caseFileService;
    private final AiSummaryService aiSummaryService;
    private final AiQuestionService aiQuestionService;

    public String runPipeline() {
        log.info("Starting world news pipeline...");

        List<Country> countries = countryRepository.findAll();
        if (countries.isEmpty()) {
            throw new RuntimeException("No countries found in database");
        }

        List<WorldNewsService.CountryRecord> countryRecords = countries.stream()
                .map(c -> new WorldNewsService.CountryRecord(
                        c.getId(), c.getCountryCode(), c.getLanguageCode(), c.getName()))
                .toList();

        List<WorldNewsService.ArticleResult> articles = worldNewsService.fetchAndStoreArticles(
                countryRecords, translationService, caseFileService);
        log.info("Added {} articles", articles.size());

        if (articles.isEmpty()) {
            throw new RuntimeException("No articles were added");
        }

        int summaryCount = 0;
        for (WorldNewsService.ArticleResult article : articles) {
            try {
                aiSummaryService.generateSummary(article.content(), article.articleId());
                summaryCount++;
                Thread.sleep(250);
            } catch (Exception e) {
                log.error("Error generating summary for article {}: {}", article.articleId(), e.getMessage());
            }
        }
        log.info("Generated {} summaries", summaryCount);

        int questionCount = 0;
        for (WorldNewsService.ArticleResult article : articles) {
            try {
                var summaryResult = aiSummaryService.generateSummary(article.content(), article.articleId());
                aiQuestionService.generateAndStoreQuestions(
                        summaryResult.youngerSummary(),
                        summaryResult.olderSummary(),
                        article.articleId());
                questionCount++;
                Thread.sleep(500);
            } catch (Exception e) {
                log.error("Error generating questions for article {}: {}", article.articleId(), e.getMessage());
            }
        }
        log.info("Generated questions for {} articles", questionCount);

        log.info("World news pipeline completed successfully.");
        return "Added " + articles.size() + " articles, " + summaryCount + " summaries, " + questionCount + " question sets";
    }

    public String regenerateAiForExisting() {
        log.info("Regenerating AI content for existing articles...");

        List<CaseFile> missingSummaries = caseFileRepository.findBySummaryYoungIsNull();
        log.info("Found {} articles missing summaries", missingSummaries.size());

        int summaryCount = 0;
        int questionCount = 0;

        for (CaseFile cf : missingSummaries) {
            try {
                var summaryResult = aiSummaryService.generateSummary(cf.getArticleContent(), cf.getArticleId());
                summaryCount++;

                aiQuestionService.generateAndStoreQuestions(
                        summaryResult.youngerSummary(),
                        summaryResult.olderSummary(),
                        cf.getArticleId());
                questionCount++;

                Thread.sleep(500);
            } catch (Exception e) {
                log.error("Error processing article {}: {}", cf.getArticleId(), e.getMessage());
            }
        }

        log.info("Generated {} summaries and {} question sets", summaryCount, questionCount);
        return "Processed " + missingSummaries.size() + " articles: " + summaryCount + " summaries, " + questionCount + " question sets";
    }
}
