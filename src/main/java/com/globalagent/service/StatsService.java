package com.globalagent.service;

import com.globalagent.model.dto.LeaderboardEntry;
import com.globalagent.model.dto.StatsDto;
import com.globalagent.model.dto.UpdateStatsRequest;
import com.globalagent.exception.ResourceNotFoundException;
import com.globalagent.model.entity.Stats;
import com.globalagent.model.entity.User;
import com.globalagent.repository.StatsRepository;
import com.globalagent.repository.UserRepository;
import com.globalagent.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final StatsRepository statsRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<LeaderboardEntry> getLeaderboard() {
        return statsRepository.findAllWithUsersOrderByXpDesc().stream()
                .map(DtoMapper::toLeaderboardEntry)
                .toList();
    }

    @Transactional(readOnly = true)
    public StatsDto getStatsByUserId(Long userId) {
        Stats stats = statsRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Stats not found for user: " + userId));
        return DtoMapper.toStatsDto(stats);
    }

    @Transactional
    public StatsDto updateStats(Long userId, UpdateStatsRequest request) {
        Stats stats = statsRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Stats not found for user: " + userId));

        if (request.getXp() != null) stats.setXp(stats.getXp() + request.getXp());
        if (request.getGamesPlayed() != null) stats.setGamesPlayed(stats.getGamesPlayed() + request.getGamesPlayed());
        if (request.getQuestionsCorrect() != null) stats.setQuestionsCorrect(stats.getQuestionsCorrect() + request.getQuestionsCorrect());
        if (request.getQuestionsWrong() != null) stats.setQuestionsWrong(stats.getQuestionsWrong() + request.getQuestionsWrong());

        Stats saved = statsRepository.save(stats);
        return DtoMapper.toStatsDto(saved);
    }
}
