package com.globalagent.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Builder.Default
    private Integer xp = 0;

    @Column(name = "games_played", nullable = false)
    @Builder.Default
    private Integer gamesPlayed = 0;

    @Column(name = "questions_correct", nullable = false)
    @Builder.Default
    private Integer questionsCorrect = 0;

    @Column(name = "questions_wrong", nullable = false)
    @Builder.Default
    private Integer questionsWrong = 0;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
