package com.kitchent.api.repository;

import com.kitchent.api.model.DietaryPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DietaryPreferenceRepository extends JpaRepository<DietaryPreference, UUID> {
}
