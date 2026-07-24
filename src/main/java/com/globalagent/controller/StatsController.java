package com.globalagent.controller;

import com.globalagent.model.dto.LeaderboardEntry;
import com.globalagent.model.dto.StatsDto;
import com.globalagent.model.dto.UpdateStatsRequest;
import com.globalagent.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderboardEntry>> getLeaderboard() {
        return ResponseEntity.ok(statsService.getLeaderboard());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<StatsDto> getStatsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(statsService.getStatsByUserId(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<StatsDto> updateStats(@PathVariable Long userId,
                                                @RequestBody UpdateStatsRequest request) {
        return ResponseEntity.ok(statsService.updateStats(userId, request));
    }
}
