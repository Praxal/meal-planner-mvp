package com.kitchent.api.repository;

import com.kitchent.api.BaseIntegrationTest;
import com.kitchent.api.model.Goal;
import com.kitchent.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class GoalRepositoryIntegrationTest extends BaseIntegrationTest {

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndRetrieveGoal() {
        User user = new User();
        user.setEmail("goal@example.com");
        user = userRepository.save(user);

        Goal goal = new Goal();
        goal.setUser(user);
        goal.setType("calorie");
        goal.setTargetValue(2000);
        goal = goalRepository.save(goal);

        Goal found = goalRepository.findById(goal.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getUser().getEmail()).isEqualTo("goal@example.com");
        assertThat(found.getType()).isEqualTo("calorie");
    }
}
