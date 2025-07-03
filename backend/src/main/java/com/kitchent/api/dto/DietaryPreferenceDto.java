package com.kitchent.api.dto;

import java.util.UUID;

public class DietaryPreferenceDto {
    private UUID id;
    private String preference;

    // Constructors
    public DietaryPreferenceDto() {}

    public DietaryPreferenceDto(UUID id, String preference) {
        this.id = id;
        this.preference = preference;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }
}
