package com.globalagent.service;

import com.globalagent.model.dto.CountryDto;
import com.globalagent.repository.CountryRepository;
import com.globalagent.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    @Transactional(readOnly = true)
    public List<CountryDto> getAllCountries() {
        return countryRepository.findAll().stream()
                .map(DtoMapper::toCountryDto)
                .toList();
    }
}
