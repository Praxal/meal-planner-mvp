package com.kitchent.api.repository;

import com.kitchent.api.BaseIntegrationTest;
import com.kitchent.api.model.Ingredient;
import com.kitchent.api.model.Recipe;
import com.kitchent.api.model.RecipeIngredient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class RecipeRepositoryIntegrationTest extends BaseIntegrationTest {

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    void shouldSaveAndRetrieveRecipeWithIngredients() {
        // Given: A new ingredient that is saved to the database first
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Tomato");
        Ingredient savedIngredient = ingredientRepository.save(ingredient);

        // Given: A new recipe
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Tomato Soup");
        newRecipe.setDescription("A simple tomato soup recipe.");
        newRecipe.setInstructions("1. Boil tomatoes. 2. Blend.");
        newRecipe.setNutritionalInfo(Map.of("calories", 100, "fat", 0.5)); // Set valid JSON string

        // Given: The link between the recipe and the ingredient, with quantity and unit
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipe(newRecipe);
        recipeIngredient.setIngredient(savedIngredient);
        recipeIngredient.setQuantity(500);
        recipeIngredient.setUnit("grams");

        // Add the recipe-ingredient link to the recipe's set of ingredients
        newRecipe.setIngredients(Set.of(recipeIngredient));

        // When: The new recipe is saved (which should cascade to RecipeIngredient)
        Recipe savedRecipe = recipeRepository.save(newRecipe);

        // Then: The saved recipe should not be null and have an ID
        assertThat(savedRecipe).isNotNull();
        assertThat(savedRecipe.getId()).isNotNull();

        // When: The recipe is retrieved from the database
        Recipe foundRecipe = recipeRepository.findById(savedRecipe.getId()).orElse(null);

        // Then: The retrieved recipe and its ingredients should be correct
        assertThat(foundRecipe).isNotNull();
        assertThat(foundRecipe.getName()).isEqualTo("Tomato Soup");
        assertThat(foundRecipe.getIngredients()).hasSize(1);

        RecipeIngredient foundRecipeIngredient = foundRecipe.getIngredients().iterator().next();
        assertThat(foundRecipeIngredient.getIngredient().getName()).isEqualTo("Tomato");
        assertThat(foundRecipeIngredient.getQuantity()).isEqualTo(500);
        assertThat(foundRecipeIngredient.getUnit()).isEqualTo("grams");
    }
}