package com.globalagent.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatsDto {

    private Long id;
    private Integer xp;
    private Integer gamesPlayed;
    private Integer questionsCorrect;
    private Integer questionsWrong;
    private Long userId;
}
