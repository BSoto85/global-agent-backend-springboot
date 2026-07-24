package com.globalagent.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "questions_older")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionOlder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "o_question", nullable = false, length = 150)
    private String question;

    @Column(name = "o_correct_answer", nullable = false, length = 100)
    private String correctAnswer;

    @Column(name = "o_incorrect_answer1", nullable = false, length = 100)
    private String incorrectAnswer1;

    @Column(name = "o_incorrect_answer2", nullable = false, length = 100)
    private String incorrectAnswer2;

    @Column(name = "o_incorrect_answer3", nullable = false, length = 100)
    private String incorrectAnswer3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "o_case_files_article_id", nullable = false)
    private CaseFile caseFile;
}
