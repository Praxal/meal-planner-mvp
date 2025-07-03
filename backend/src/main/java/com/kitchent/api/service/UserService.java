package com.kitchent.api.service;

import com.kitchent.api.dto.UserProfileDto;
import com.kitchent.api.dto.DietaryPreferenceDto;
import com.kitchent.api.dto.FamilyMemberDto;
import java.util.List;
import java.util.UUID;

public interface UserService {
    UserProfileDto getUserProfile(UUID userId);
    UserProfileDto updateUserProfile(UUID userId, UserProfileDto userProfileDto);
    List<DietaryPreferenceDto> getUserDietaryPreferences(UUID userId);
    DietaryPreferenceDto addDietaryPreference(UUID userId, DietaryPreferenceDto preferenceDto);
    void deleteDietaryPreference(UUID userId, UUID preferenceId);
    List<FamilyMemberDto> getUserFamilyMembers(UUID userId);
    FamilyMemberDto addFamilyMember(UUID userId, FamilyMemberDto familyMemberDto);
    void deleteFamilyMember(UUID userId, UUID familyMemberId);
}
