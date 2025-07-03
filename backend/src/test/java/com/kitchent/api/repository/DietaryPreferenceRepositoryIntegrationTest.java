package com.kitchent.api.repository;

import com.kitchent.api.BaseIntegrationTest;
import com.kitchent.api.model.DietaryPreference;
import com.kitchent.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class DietaryPreferenceRepositoryIntegrationTest extends BaseIntegrationTest {

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private DietaryPreferenceRepository dietaryPreferenceRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndRetrieveDietaryPreference() {
        User user = new User();
        user.setEmail("diet@example.com");
        user = userRepository.save(user);

        DietaryPreference pref = new DietaryPreference();
        pref.setPreference("vegan");
        pref.setUser(user);
        pref = dietaryPreferenceRepository.save(pref);

        DietaryPreference found = dietaryPreferenceRepository.findById(pref.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getUser().getEmail()).isEqualTo("diet@example.com");
        assertThat(found.getPreference()).isEqualTo("vegan");
    }
}
