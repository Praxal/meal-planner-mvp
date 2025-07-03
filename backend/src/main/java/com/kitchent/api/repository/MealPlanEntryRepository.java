package com.kitchent.api.repository;

import com.kitchent.api.model.MealPlanEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MealPlanEntryRepository extends JpaRepository<MealPlanEntry, UUID> {
}
