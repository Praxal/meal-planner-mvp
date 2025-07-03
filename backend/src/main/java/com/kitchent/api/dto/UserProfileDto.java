package com.kitchent.api.dto;

import java.util.List;
import java.util.UUID;

public class UserProfileDto {
    private UUID id;
    private String email;
    private List<DietaryPreferenceDto> dietaryPreferences;
    private List<FamilyMemberDto> familyMembers;

    // Constructors
    public UserProfileDto() {}

    public UserProfileDto(UUID id, String email) {
        this.id = id;
        this.email = email;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<DietaryPreferenceDto> getDietaryPreferences() {
        return dietaryPreferences;
    }

    public void setDietaryPreferences(List<DietaryPreferenceDto> dietaryPreferences) {
        this.dietaryPreferences = dietaryPreferences;
    }

    public List<FamilyMemberDto> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<FamilyMemberDto> familyMembers) {
        this.familyMembers = familyMembers;
    }
}
