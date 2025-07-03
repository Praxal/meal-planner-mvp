package com.kitchent.api.service.impl;

import com.kitchent.api.dto.UserProfileDto;
import com.kitchent.api.dto.DietaryPreferenceDto;
import com.kitchent.api.dto.FamilyMemberDto;
import com.kitchent.api.model.User;
import com.kitchent.api.model.DietaryPreference;
import com.kitchent.api.model.FamilyMember;
import com.kitchent.api.repository.UserRepository;
import com.kitchent.api.repository.DietaryPreferenceRepository;
import com.kitchent.api.repository.FamilyMemberRepository;
import com.kitchent.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DietaryPreferenceRepository dietaryPreferenceRepository;

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Override
    public UserProfileDto getUserProfile(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        UserProfileDto dto = new UserProfileDto(user.getId(), user.getEmail());
        
        // Convert dietary preferences
        if (user.getDietaryPreferences() != null) {
            dto.setDietaryPreferences(
                user.getDietaryPreferences().stream()
                    .map(pref -> new DietaryPreferenceDto(pref.getId(), pref.getPreference()))
                    .collect(Collectors.toList())
            );
        }
        
        // Convert family members
        if (user.getFamilyMembers() != null) {
            dto.setFamilyMembers(
                user.getFamilyMembers().stream()
                    .map(member -> new FamilyMemberDto(member.getId(), member.getName()))
                    .collect(Collectors.toList())
            );
        }
        
        return dto;
    }

    @Override
    public UserProfileDto updateUserProfile(UUID userId, UserProfileDto userProfileDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Update user fields (email shouldn't be updated as it's used for OAuth)
        // Only update if provided and different
        if (userProfileDto.getEmail() != null && !userProfileDto.getEmail().equals(user.getEmail())) {
            user.setEmail(userProfileDto.getEmail());
        }
        
        User savedUser = userRepository.save(user);
        return getUserProfile(savedUser.getId());
    }

    @Override
    public List<DietaryPreferenceDto> getUserDietaryPreferences(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return user.getDietaryPreferences().stream()
                .map(pref -> new DietaryPreferenceDto(pref.getId(), pref.getPreference()))
                .collect(Collectors.toList());
    }

    @Override
    public DietaryPreferenceDto addDietaryPreference(UUID userId, DietaryPreferenceDto preferenceDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        DietaryPreference preference = new DietaryPreference();
        preference.setPreference(preferenceDto.getPreference());
        preference.setUser(user);
        
        DietaryPreference savedPreference = dietaryPreferenceRepository.save(preference);
        return new DietaryPreferenceDto(savedPreference.getId(), savedPreference.getPreference());
    }

    @Override
    public void deleteDietaryPreference(UUID userId, UUID preferenceId) {
        DietaryPreference preference = dietaryPreferenceRepository.findById(preferenceId)
                .orElseThrow(() -> new RuntimeException("Dietary preference not found"));
        
        if (!preference.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized to delete this preference");
        }
        
        dietaryPreferenceRepository.delete(preference);
    }

    @Override
    public List<FamilyMemberDto> getUserFamilyMembers(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return user.getFamilyMembers().stream()
                .map(member -> new FamilyMemberDto(member.getId(), member.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public FamilyMemberDto addFamilyMember(UUID userId, FamilyMemberDto familyMemberDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        FamilyMember familyMember = new FamilyMember();
        familyMember.setName(familyMemberDto.getName());
        familyMember.setUser(user);
        
        FamilyMember savedMember = familyMemberRepository.save(familyMember);
        return new FamilyMemberDto(savedMember.getId(), savedMember.getName());
    }

    @Override
    public void deleteFamilyMember(UUID userId, UUID familyMemberId) {
        FamilyMember familyMember = familyMemberRepository.findById(familyMemberId)
                .orElseThrow(() -> new RuntimeException("Family member not found"));
        
        if (!familyMember.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized to delete this family member");
        }
        
        familyMemberRepository.delete(familyMember);
    }
}
