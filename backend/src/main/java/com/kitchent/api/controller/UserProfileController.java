package com.kitchent.api.controller;

import com.kitchent.api.dto.UserProfileDto;
import com.kitchent.api.dto.DietaryPreferenceDto;
import com.kitchent.api.dto.FamilyMemberDto;
import com.kitchent.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable UUID userId, Authentication authentication) {
        // In a real implementation, you'd validate that the authenticated user can access this profile
        UserProfileDto profile = userService.getUserProfile(userId);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{userId}/profile")
    public ResponseEntity<UserProfileDto> updateUserProfile(
            @PathVariable UUID userId, 
            @RequestBody UserProfileDto userProfileDto,
            Authentication authentication) {
        UserProfileDto updatedProfile = userService.updateUserProfile(userId, userProfileDto);
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/{userId}/dietary-preferences")
    public ResponseEntity<List<DietaryPreferenceDto>> getUserDietaryPreferences(@PathVariable UUID userId) {
        List<DietaryPreferenceDto> preferences = userService.getUserDietaryPreferences(userId);
        return ResponseEntity.ok(preferences);
    }

    @PostMapping("/{userId}/dietary-preferences")
    public ResponseEntity<DietaryPreferenceDto> addDietaryPreference(
            @PathVariable UUID userId,
            @RequestBody DietaryPreferenceDto preferenceDto) {
        DietaryPreferenceDto createdPreference = userService.addDietaryPreference(userId, preferenceDto);
        return ResponseEntity.ok(createdPreference);
    }

    @DeleteMapping("/{userId}/dietary-preferences/{preferenceId}")
    public ResponseEntity<Void> deleteDietaryPreference(
            @PathVariable UUID userId,
            @PathVariable UUID preferenceId) {
        userService.deleteDietaryPreference(userId, preferenceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/family-members")
    public ResponseEntity<List<FamilyMemberDto>> getUserFamilyMembers(@PathVariable UUID userId) {
        List<FamilyMemberDto> familyMembers = userService.getUserFamilyMembers(userId);
        return ResponseEntity.ok(familyMembers);
    }

    @PostMapping("/{userId}/family-members")
    public ResponseEntity<FamilyMemberDto> addFamilyMember(
            @PathVariable UUID userId,
            @RequestBody FamilyMemberDto familyMemberDto) {
        FamilyMemberDto createdMember = userService.addFamilyMember(userId, familyMemberDto);
        return ResponseEntity.ok(createdMember);
    }

    @DeleteMapping("/{userId}/family-members/{familyMemberId}")
    public ResponseEntity<Void> deleteFamilyMember(
            @PathVariable UUID userId,
            @PathVariable UUID familyMemberId) {
        userService.deleteFamilyMember(userId, familyMemberId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        
        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            return ResponseEntity.ok(oauth2User.getAttributes());
        }
        
        return ResponseEntity.ok(authentication.getPrincipal());
    }
}
