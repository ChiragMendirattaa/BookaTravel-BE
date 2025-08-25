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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "anonymous_user_id")
    private AnonymousUser anonymousUser;

    @Column(length = 4000)
    private String bookingRequestData;

    private String bookingStatus;
    private String paymentIntentId;
    private String referenceNum;
    private String supplierConfirmationNum;

    @CreationTimestamp
    private LocalDateTime createdAt;
}