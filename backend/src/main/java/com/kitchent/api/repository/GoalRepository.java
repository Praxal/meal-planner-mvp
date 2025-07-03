package com.kitchent.api.repository;

import com.kitchent.api.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface GoalRepository extends JpaRepository<Goal, UUID> {
}
