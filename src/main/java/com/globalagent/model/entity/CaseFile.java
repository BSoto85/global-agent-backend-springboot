package com.globalagent.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "case_files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaseFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_id", unique = true)
    private Integer articleId;

    @Column(name = "article_content", columnDefinition = "TEXT")
    private String articleContent;

    @Column(name = "article_title", columnDefinition = "TEXT")
    private String articleTitle;

    @Column(name = "publish_date", length = 50)
    private String publishDate;

    @Column(name = "summary_young", columnDefinition = "TEXT")
    private String summaryYoung;

    @Column(name = "summary_old", columnDefinition = "TEXT")
    private String summaryOld;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "countries_id")
    private Country country;

    @Column(name = "photo_url", columnDefinition = "TEXT")
    private String photoUrl;

    @OneToMany(mappedBy = "caseFile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<QuestionYounger> youngerQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "caseFile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<QuestionOlder> olderQuestions = new ArrayList<>();
}
