package com.globalagent.service;

import com.globalagent.exception.ResourceNotFoundException;
import com.globalagent.model.dto.UpdateProfileRequest;
import com.globalagent.model.dto.UserDto;
import com.globalagent.model.entity.User;
import com.globalagent.repository.UserRepository;
import com.globalagent.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDto getProfileByUid(String uid) {
        User user = userRepository.findByUid(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with uid: " + uid));
        return DtoMapper.toUserDto(user);
    }

    @Transactional
    public UserDto updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getDob() != null) user.setDob(request.getDob());
        if (request.getPhoto() != null) user.setPhoto(request.getPhoto());

        User saved = userRepository.save(user);
        return DtoMapper.toUserDto(saved);
    }
}
