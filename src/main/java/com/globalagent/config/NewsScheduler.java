package com.globalagent.config;

import com.globalagent.service.NewsPipelineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsScheduler {

    private final NewsPipelineService newsPipelineService;

    @Scheduled(cron = "0 0 7 * * *", zone = "America/New_York")
    public void dailyNewsPipeline() {
        log.info("Cron triggered: starting daily news pipeline...");
        try {
            String result = newsPipelineService.runPipeline();
            log.info("Daily pipeline completed: {}", result);
        } catch (Exception e) {
            log.error("Daily pipeline failed: {}", e.getMessage());
        }
    }
}
