package com.kitchent.api.controller;

import com.kitchent.api.dto.UserProfileDto;
import com.kitchent.api.dto.DietaryPreferenceDto;
import com.kitchent.api.dto.FamilyMemberDto;
import com.kitchent.api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserProfileController.class)
@ActiveProfiles("test")
@Import(ControllerTestSecurityConfig.class)
class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void getUserProfile_ShouldReturnUserProfile() throws Exception {
        // Arrange
        UUID userId = UUID.randomUUID();
        UserProfileDto userProfile = new UserProfileDto(userId, "test@example.com");
        userProfile.setDietaryPreferences(Arrays.asList(
            new DietaryPreferenceDto(UUID.randomUUID(), "Vegetarian")
        ));
        userProfile.setFamilyMembers(Arrays.asList(
            new FamilyMemberDto(UUID.randomUUID(), "John Doe")
        ));

        when(userService.getUserProfile(userId)).thenReturn(userProfile);

        // Act & Assert
        mockMvc.perform(get("/api/users/{userId}/profile", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.dietaryPreferences[0].name").value("Vegetarian"))
                .andExpect(jsonPath("$.familyMembers[0].name").value("John Doe"));
    }

    @Test
    @WithMockUser
    void updateUserProfile_ShouldReturnUpdatedProfile() throws Exception {
        // Arrange
        UUID userId = UUID.randomUUID();
        UserProfileDto inputProfile = new UserProfileDto(userId, "updated@example.com");
        UserProfileDto updatedProfile = new UserProfileDto(userId, "updated@example.com");

        when(userService.updateUserProfile(eq(userId), any(UserProfileDto.class)))
                .thenReturn(updatedProfile);

        // Act & Assert
        mockMvc.perform(put("/api/users/{userId}/profile", userId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputProfile)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }

    @Test
    @WithMockUser
    void getUserDietaryPreferences_ShouldReturnDietaryPreferences() throws Exception {
        // Arrange
        UUID userId = UUID.randomUUID();
        DietaryPreferenceDto preference = new DietaryPreferenceDto(UUID.randomUUID(), "Gluten-Free");

        when(userService.getUserDietaryPreferences(userId))
                .thenReturn(Arrays.asList(preference));

        // Act & Assert
        mockMvc.perform(get("/api/users/{userId}/dietary-preferences", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gluten-Free"));
    }

    @Test
    @WithMockUser
    void addDietaryPreference_ShouldReturnCreatedPreference() throws Exception {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID preferenceId = UUID.randomUUID();
        DietaryPreferenceDto inputPreference = new DietaryPreferenceDto(null, "Vegan");
        DietaryPreferenceDto createdPreference = new DietaryPreferenceDto(preferenceId, "Vegan");

        when(userService.addDietaryPreference(eq(userId), any(DietaryPreferenceDto.class)))
                .thenReturn(createdPreference);

        // Act & Assert
        mockMvc.perform(post("/api/users/{userId}/dietary-preferences", userId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputPreference)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(preferenceId.toString()))
                .andExpect(jsonPath("$.name").value("Vegan"));
    }

    @Test
    @WithMockUser
    void getUserFamilyMembers_ShouldReturnFamilyMembers() throws Exception {
        // Arrange
        UUID userId = UUID.randomUUID();
        FamilyMemberDto familyMember = new FamilyMemberDto(UUID.randomUUID(), "Jane Doe");

        when(userService.getUserFamilyMembers(userId))
                .thenReturn(Arrays.asList(familyMember));

        // Act & Assert
        mockMvc.perform(get("/api/users/{userId}/family-members", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Jane Doe"));
    }

    @Test
    @WithMockUser
    void addFamilyMember_ShouldReturnCreatedMember() throws Exception {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID memberId = UUID.randomUUID();
        FamilyMemberDto inputMember = new FamilyMemberDto(null, "Jane Doe");
        FamilyMemberDto createdMember = new FamilyMemberDto(memberId, "Jane Doe");

        when(userService.addFamilyMember(eq(userId), any(FamilyMemberDto.class)))
                .thenReturn(createdMember);

        // Act & Assert
        mockMvc.perform(post("/api/users/{userId}/family-members", userId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputMember)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(memberId.toString()))
                .andExpect(jsonPath("$.name").value("Jane Doe"));
    }

    @Test
    void getMyProfile_shouldReturn401_whenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOidcUser(email = "testuser@example.com")
    void getMyProfile_shouldReturnProfile_whenAuthenticated() throws Exception {
        // Arrange
        UserProfileDto expectedProfile = new UserProfileDto(UUID.randomUUID(), "testuser@example.com");
        when(userService.getUserProfile(any(OidcUser.class))).thenReturn(expectedProfile);
        
        // Act & Assert
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("testuser@example.com"));
    }
}
