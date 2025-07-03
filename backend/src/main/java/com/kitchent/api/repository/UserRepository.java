package com.kitchent.api.repository;

import com.kitchent.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    // Custom query methods will be added here later
}
