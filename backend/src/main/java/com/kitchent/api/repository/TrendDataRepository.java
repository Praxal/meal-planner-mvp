package com.kitchent.api.repository;

import com.kitchent.api.model.TrendData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TrendDataRepository extends JpaRepository<TrendData, UUID> {
}
