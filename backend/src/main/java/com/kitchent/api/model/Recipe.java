package com.kitchent.api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredient> ingredients;

    // Optional: description, instructions, nutritional_info (JSONB)
    private String description;
    private String instructions;
    
    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(org.hibernate.type.SqlTypes.JSON)
    private Map<String, Object> nutritionalInfo;

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<RecipeIngredient> getIngredients() { return ingredients; }
    public void setIngredients(Set<RecipeIngredient> ingredients) { this.ingredients = ingredients; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public Map<String, Object> getNutritionalInfo() { return nutritionalInfo; }
    public void setNutritionalInfo(Map<String, Object> nutritionalInfo) { this.nutritionalInfo = nutritionalInfo; }
}
