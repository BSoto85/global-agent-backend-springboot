package com.globalagent.service;

import com.globalagent.exception.ResourceNotFoundException;
import com.globalagent.model.dto.RegisterRequest;
import com.globalagent.model.dto.UserDto;
import com.globalagent.model.entity.Stats;
import com.globalagent.model.entity.User;
import com.globalagent.repository.StatsRepository;
import com.globalagent.repository.UserRepository;
import com.globalagent.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final StatsRepository statsRepository;

    @Transactional
    public UserDto register(RegisterRequest request) {
        LocalDate dob = null;
        if (request.getDob() != null && !request.getDob().isBlank()) {
            dob = LocalDate.parse(request.getDob());
        }

        User user = User.builder()
                .uid(request.getUid())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dob(dob)
                .photo(request.getPhoto())
                .build();

        User savedUser = userRepository.save(user);

        Stats stats = Stats.builder().user(savedUser).build();
        statsRepository.save(stats);

        savedUser.setStats(stats);
        return DtoMapper.toUserDto(savedUser);
    }

    @Transactional(readOnly = true)
    public UserDto getUserByUid(String uid) {
        User user = userRepository.findByUid(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with uid: " + uid));
        return DtoMapper.toUserDto(user);
    }
}
