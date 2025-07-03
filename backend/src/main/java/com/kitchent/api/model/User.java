package com.kitchent.api.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    private String googleOAuthId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DietaryPreference> dietaryPreferences;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FamilyMember> familyMembers;

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getGoogleOAuthId() { return googleOAuthId; }
    public void setGoogleOAuthId(String googleOAuthId) { this.googleOAuthId = googleOAuthId; }
    public List<DietaryPreference> getDietaryPreferences() { return dietaryPreferences; }
    public void setDietaryPreferences(List<DietaryPreference> dietaryPreferences) { this.dietaryPreferences = dietaryPreferences; }
    public List<FamilyMember> getFamilyMembers() { return familyMembers; }
    public void setFamilyMembers(List<FamilyMember> familyMembers) { this.familyMembers = familyMembers; }
}
