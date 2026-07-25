package com.globalagent.util;

import com.globalagent.model.dto.*;
import com.globalagent.model.entity.*;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class DtoMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .uid(user.getUid())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dob(user.getDob() != null ? user.getDob().toString() : null)
                .photo(user.getPhoto())
                .createdAt(user.getCreatedAt() != null ? user.getCreatedAt().format(FORMATTER) : null)
                .build();
    }

    public static StatsDto toStatsDto(Stats stats) {
        return StatsDto.builder()
                .id(stats.getId())
                .xp(stats.getXp())
                .gamesPlayed(stats.getGamesPlayed())
                .questionsCorrect(stats.getQuestionsCorrect())
                .questionsWrong(stats.getQuestionsWrong())
                .userId(stats.getUser() != null ? stats.getUser().getId() : null)
                .build();
    }

    public static CountryDto toCountryDto(Country country) {
        return CountryDto.builder()
                .id(country.getId())
                .flag(country.getFlag())
                .countryCode(country.getCountryCode())
                .name(country.getName())
                .languageCode(country.getLanguageCode())
                .silhouette(country.getSilhouette())
                .build();
    }

    public static CaseFileDto toCaseFileDto(CaseFile cf) {
        return CaseFileDto.builder()
                .id(cf.getId())
                .articleId(cf.getArticleId())
                .articleContent(cf.getArticleContent())
                .articleTitle(cf.getArticleTitle())
                .publishDate(cf.getPublishDate())
                .summaryYoung(cf.getSummaryYoung())
                .summaryOld(cf.getSummaryOld())
                .countryId(cf.getCountry() != null ? cf.getCountry().getId() : null)
                .photoUrl(cf.getPhotoUrl())
                .build();
    }

    public static QuestionDto toQuestionYoungerDto(QuestionYounger q) {
        return QuestionDto.builder()
                .id(q.getId())
                .question(q.getQuestion())
                .correctAnswer(q.getCorrectAnswer())
                .incorrectAnswer1(q.getIncorrectAnswer1())
                .incorrectAnswer2(q.getIncorrectAnswer2())
                .incorrectAnswer3(q.getIncorrectAnswer3())
                .build();
    }

    public static QuestionDto toQuestionOlderDto(QuestionOlder q) {
        return QuestionDto.builder()
                .id(q.getId())
                .question(q.getQuestion())
                .correctAnswer(q.getCorrectAnswer())
                .incorrectAnswer1(q.getIncorrectAnswer1())
                .incorrectAnswer2(q.getIncorrectAnswer2())
                .incorrectAnswer3(q.getIncorrectAnswer3())
                .build();
    }

    public static LeaderboardEntry toLeaderboardEntry(Stats stats) {
        User user = stats.getUser();
        return LeaderboardEntry.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .photo(user.getPhoto())
                .xp(stats.getXp())
                .build();
    }
}
