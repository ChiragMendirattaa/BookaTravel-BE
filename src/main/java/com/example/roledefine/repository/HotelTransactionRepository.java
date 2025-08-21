package com.example.roledefine.repository;

import com.example.roledefine.entity.HotelTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HotelTransactionRepository extends JpaRepository<HotelTransaction, Long> {

    Optional<HotelTransaction> findByPaymentIntentId(String paymentIntentId);
}