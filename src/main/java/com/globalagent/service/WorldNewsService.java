package com.globalagent.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class WorldNewsService {

    @Value("${news-api.base-url}")
    private String baseUrl;

    @Value("${news-api.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public record ArticleResult(int articleId, String content, String title) {}

    public List<ArticleResult> fetchAndStoreArticles(List<CountryRecord> countries,
                                                      TranslationService translationService,
                                                      CaseFileService caseFileService) {
        List<ArticleResult> addedArticles = new ArrayList<>();
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        for (CountryRecord country : countries) {
            try {
                String url = String.format("%s?source-country=%s&language=%s&date=%s",
                        baseUrl, country.countryCode(), country.languageCode(), today);

                HttpHeaders headers = new HttpHeaders();
                headers.set("x-api-key", apiKey);

                ResponseEntity<Map> response = restTemplate.exchange(
                        url, HttpMethod.GET, new HttpEntity<>(headers), Map.class);

                if (response.getBody() == null || !response.getBody().containsKey("top_news")) {
                    log.warn("No news data for country: {}", country.name());
                    continue;
                }

                List<Map<String, Object>> topNews = (List<Map<String, Object>>) response.getBody().get("top_news");
                if (topNews == null || topNews.isEmpty()) continue;

                List<Map<String, Object>> articles = (List<Map<String, Object>>) topNews.get(0).get("news");
                if (articles == null || articles.isEmpty()) continue;

                int middle = articles.size() / 2;
                List<Map<String, Object>> selected = List.of(
                        articles.get(0),
                        articles.get(middle),
                        articles.get(articles.size() - 1)
                );

                for (Map<String, Object> article : selected) {
                    int articleId = ((Number) article.get("id")).intValue();
                    String content = (String) article.get("text");
                    String title = (String) article.get("title");
                    String publishDate = (String) article.get("publish_date");
                    String imageUrl = (String) article.get("image");

                    if (!"en".equals(country.languageCode())) {
                        content = translationService.translate(content, "en");
                        title = translationService.translate(title, "en");
                    }

                    caseFileService.saveCaseFile(
                            country.id(), articleId, content, title, publishDate, imageUrl);

                    addedArticles.add(new ArticleResult(articleId, content, title));
                }

                Thread.sleep(1000);
            } catch (Exception e) {
                log.error("Error fetching news for country {}: {}", country.name(), e.getMessage());
            }
        }

        return addedArticles;
    }

    public record CountryRecord(Long id, String countryCode, String languageCode, String name) {}
}
