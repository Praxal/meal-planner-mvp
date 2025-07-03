package com.kitchent.api.repository;

import com.kitchent.api.model.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MealPlanRepository extends JpaRepository<MealPlan, UUID> {
}
