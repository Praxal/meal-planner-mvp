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

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class RecipeIngredientRepositoryIntegrationTest extends BaseIntegrationTest {

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    void shouldSaveAndRetrieveRecipeIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Cheese");
        ingredient = ingredientRepository.save(ingredient);

        Recipe recipe = new Recipe();
        recipe.setName("Pizza");
        recipe = recipeRepository.save(recipe);

        RecipeIngredient ri = new RecipeIngredient();
        ri.setIngredient(ingredient);
        ri.setRecipe(recipe);
        ri.setQuantity(1.5);
        ri.setUnit("cups");
        ri = recipeIngredientRepository.save(ri);

        RecipeIngredient found = recipeIngredientRepository.findById(ri.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getIngredient().getName()).isEqualTo("Cheese");
        assertThat(found.getRecipe().getName()).isEqualTo("Pizza");
    }
}
