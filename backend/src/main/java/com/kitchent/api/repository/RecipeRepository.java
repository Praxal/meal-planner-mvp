package com.kitchent.api.repository;

import com.kitchent.api.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
}
