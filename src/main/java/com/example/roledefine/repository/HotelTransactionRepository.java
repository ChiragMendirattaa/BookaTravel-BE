package com.example.roledefine.repository;

import com.example.roledefine.entity.HotelTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelTransactionRepository extends JpaRepository<HotelTransaction, Long> {
}