package com.globalagent.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "questions_younger")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionYounger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "y_question", nullable = false, length = 150)
    private String question;

    @Column(name = "y_correct_answer", nullable = false, length = 100)
    private String correctAnswer;

    @Column(name = "y_incorrect_answer1", nullable = false, length = 100)
    private String incorrectAnswer1;

    @Column(name = "y_incorrect_answer2", nullable = false, length = 100)
    private String incorrectAnswer2;

    @Column(name = "y_incorrect_answer3", nullable = false, length = 100)
    private String incorrectAnswer3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "y_case_files_article_id", nullable = false)
    private CaseFile caseFile;
}
