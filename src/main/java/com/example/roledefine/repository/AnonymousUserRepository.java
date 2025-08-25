package com.example.roledefine.repository;

import com.example.roledefine.entity.AnonymousUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface AnonymousUserRepository extends JpaRepository<AnonymousUser, UUID> {
}