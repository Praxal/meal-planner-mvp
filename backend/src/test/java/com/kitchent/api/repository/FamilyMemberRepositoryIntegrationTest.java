package com.kitchent.api.repository;

import com.kitchent.api.BaseIntegrationTest;
import com.kitchent.api.model.FamilyMember;
import com.kitchent.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class FamilyMemberRepositoryIntegrationTest extends BaseIntegrationTest {

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private FamilyMemberRepository familyMemberRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndRetrieveFamilyMember() {
        User user = new User();
        user.setEmail("family@example.com");
        user = userRepository.save(user);

        FamilyMember member = new FamilyMember();
        member.setName("Alice");
        member.setUser(user);
        member = familyMemberRepository.save(member);

        FamilyMember found = familyMemberRepository.findById(member.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getUser().getEmail()).isEqualTo("family@example.com");
        assertThat(found.getName()).isEqualTo("Alice");
    }
}
