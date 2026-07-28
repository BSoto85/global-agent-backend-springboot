package com.globalagent.service;

import com.globalagent.exception.ResourceNotFoundException;
import com.globalagent.model.dto.CaseFileDto;
import com.globalagent.model.entity.CaseFile;
import com.globalagent.model.entity.Country;
import com.globalagent.repository.CaseFileRepository;
import com.globalagent.repository.CountryRepository;
import com.globalagent.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaseFileService {

    private final CaseFileRepository caseFileRepository;
    private final CountryRepository countryRepository;

    @Transactional(readOnly = true)
    public List<CaseFileDto> getCaseFilesByCountry(Long countryId) {
        return caseFileRepository.findByCountryId(countryId).stream()
                .map(DtoMapper::toCaseFileDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CaseFileDto getCaseFileById(Long id) {
        CaseFile cf = caseFileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Case file not found: " + id));
        return DtoMapper.toCaseFileDto(cf);
    }

    @Transactional
    public CaseFile saveCaseFile(Long countryId, Integer articleId,
                                  String articleContent, String articleTitle,
                                  String publishDate, String photoUrl) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found: " + countryId));

        var existing = caseFileRepository.findByArticleId(articleId);
        if (existing.isPresent()) {
            return existing.get();
        }

        CaseFile caseFile = CaseFile.builder()
                .country(country)
                .articleId(articleId)
                .articleContent(articleContent)
                .articleTitle(articleTitle)
                .publishDate(publishDate)
                .photoUrl(photoUrl)
                .build();

        return caseFileRepository.save(caseFile);
    }

    @Transactional(readOnly = true)
    public List<CaseFile> getAllCaseFilesRaw() {
        return caseFileRepository.findAll();
    }
}
