package com.kitchent.api.repository;

import com.kitchent.api.BaseIntegrationTest;
import com.kitchent.api.model.MealPlan;
import com.kitchent.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class MealPlanRepositoryIntegrationTest extends BaseIntegrationTest {

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MealPlanRepository mealPlanRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndRetrieveMealPlan() {
        User user = new User();
        user.setEmail("mealplan@example.com");
        user = userRepository.save(user);

        MealPlan mealPlan = new MealPlan();
        mealPlan.setUser(user);
        mealPlan.setStartDate(LocalDate.now());
        mealPlan.setEndDate(LocalDate.now().plusDays(7));
        mealPlan = mealPlanRepository.save(mealPlan);

        MealPlan found = mealPlanRepository.findById(mealPlan.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getUser().getEmail()).isEqualTo("mealplan@example.com");
    }
}
