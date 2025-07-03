package com.kitchent.api.dto;

import java.util.UUID;

public class FamilyMemberDto {
    private UUID id;
    private String name;

    // Constructors
    public FamilyMemberDto() {}

    public FamilyMemberDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
