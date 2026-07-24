package com.globalagent.controller;

import com.globalagent.model.dto.UpdateProfileRequest;
import com.globalagent.model.dto.UserDto;
import com.globalagent.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{uid}")
    public ResponseEntity<UserDto> getProfile(@PathVariable String uid) {
        return ResponseEntity.ok(profileService.getProfileByUid(uid));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateProfile(@PathVariable Long userId,
                                                 @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(profileService.updateProfile(userId, request));
    }
}
