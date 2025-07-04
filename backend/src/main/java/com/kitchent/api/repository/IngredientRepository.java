package com.kitchent.api.repository;

import com.kitchent.api.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
}
