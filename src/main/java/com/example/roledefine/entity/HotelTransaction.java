package com.example.roledefine.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "hotel_transactions")
@Data
public class HotelTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionId;
    private String productId;
    private String tokenId;
    private String rateBasisId;

    private String bookingStatus;

    @Column(length = 2000)
    private String guestData;

    private String paymentIntentId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}