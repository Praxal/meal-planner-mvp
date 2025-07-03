package com.kitchent.api.repository;

import com.kitchent.api.BaseIntegrationTest;
import com.kitchent.api.model.MealPlan;
import com.kitchent.api.model.MealPlanEntry;
import com.kitchent.api.model.Recipe;
import com.kitchent.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class MealPlanEntryRepositoryIntegrationTest extends BaseIntegrationTest {

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MealPlanEntryRepository mealPlanEntryRepository;
    @Autowired
    private MealPlanRepository mealPlanRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndRetrieveMealPlanEntry() {
        User user = new User();
        user.setEmail("entry@example.com");
        user = userRepository.save(user);

        MealPlan mealPlan = new MealPlan();
        mealPlan.setUser(user);
        mealPlan.setStartDate(LocalDate.now());
        mealPlan.setEndDate(LocalDate.now().plusDays(7));
        mealPlan = mealPlanRepository.save(mealPlan);

        Recipe recipe = new Recipe();
        recipe.setName("Pasta");
        recipe = recipeRepository.save(recipe);

        MealPlanEntry entry = new MealPlanEntry();
        entry.setMealPlan(mealPlan);
        entry.setRecipe(recipe);
        entry.setDate(LocalDate.now());
        entry.setMealType("lunch");
        entry = mealPlanEntryRepository.save(entry);

        MealPlanEntry found = mealPlanEntryRepository.findById(entry.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getMealPlan().getId()).isEqualTo(mealPlan.getId());
        assertThat(found.getRecipe().getName()).isEqualTo("Pasta");
    }
}
