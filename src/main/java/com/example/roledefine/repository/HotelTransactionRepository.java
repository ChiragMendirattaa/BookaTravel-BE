package com.example.roledefine.repository;

import com.example.roledefine.entity.AnonymousUser;
import com.example.roledefine.entity.HotelTransaction;
import com.example.roledefine.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelTransactionRepository extends JpaRepository<HotelTransaction, Long> {

    Optional<HotelTransaction> findByPaymentIntentId(String paymentIntentId);

    List<HotelTransaction> findByAnonymousUser(AnonymousUser anonymousUser);

    @Modifying
    @Query("UPDATE HotelTransaction ht SET ht.user = :user, ht.anonymousUser = null WHERE ht.anonymousUser = :anonymousUser")
    int reassignAnonymousTransactions(@Param("user") User user, @Param("anonymousUser") AnonymousUser anonymousUser);

}