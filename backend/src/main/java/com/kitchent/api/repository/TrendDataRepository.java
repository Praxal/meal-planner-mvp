package com.kitchent.api.repository;

import com.kitchent.api.model.TrendData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TrendDataRepository extends JpaRepository<TrendData, UUID> {
    @Query("SELECT t FROM TrendData t JOIN FETCH t.user WHERE t.id = :id")
    Optional<TrendData> findByIdWithUser(@Param("id") UUID id);
}
