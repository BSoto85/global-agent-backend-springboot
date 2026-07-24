package com.globalagent.controller;

import com.globalagent.model.dto.CaseFileDto;
import com.globalagent.service.CaseFileService;
import com.globalagent.service.NewsPipelineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/case_files")
@RequiredArgsConstructor
public class CaseFileController {

    private final CaseFileService caseFileService;
    private final NewsPipelineService newsPipelineService;

    @GetMapping("/world_news")
    public ResponseEntity<Map<String, String>> triggerWorldNewsPipeline() {
        String result = newsPipelineService.runPipeline();
        return ResponseEntity.ok(Map.of("message", result));
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<List<CaseFileDto>> getCaseFilesByCountry(@PathVariable Long countryId) {
        return ResponseEntity.ok(caseFileService.getCaseFilesByCountry(countryId));
    }
}
