package com.kitchent.api.repository;

import com.kitchent.api.BaseIntegrationTest;
import com.kitchent.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional // Ensures that test data is rolled back after each test method
class UserRepositoryIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndRetrieveUser() {
        // Given: A new user object is created
        User newUser = new User();
        newUser.setEmail("test.user@example.com");
        newUser.setGoogleOAuthId("google-oauth-id-12345");

        // When: The new user is saved to the database
        User savedUser = userRepository.save(newUser);

        // Then: The saved user should not be null and should have an ID assigned
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
    }
}
