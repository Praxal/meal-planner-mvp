package com.kitchent.api.repository;

import com.kitchent.api.model.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, UUID> {
}
