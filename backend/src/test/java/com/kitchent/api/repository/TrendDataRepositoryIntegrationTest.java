package com.kitchent.api.repository;

import com.kitchent.api.BaseIntegrationTest;
import com.kitchent.api.model.TrendData;
import com.kitchent.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class TrendDataRepositoryIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private TrendDataRepository trendDataRepository;
    @Autowired
    private UserRepository userRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void shouldSaveAndRetrieveTrendData() {
        User user = new User();
        user.setEmail("trend@example.com");
        user = userRepository.save(user);

        TrendData trend = new TrendData();
        trend.setUser(user);
        trend.setDate(LocalDate.now());
        trend.setMetric("calories");
        trend.setValue(1800);
        trend = trendDataRepository.save(trend);

        TrendData found = trendDataRepository.findById(trend.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getUser().getEmail()).isEqualTo("trend@example.com");
        assertThat(found.getMetric()).isEqualTo("calories");
    }
}
