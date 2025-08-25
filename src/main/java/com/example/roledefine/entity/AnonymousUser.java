package com.example.roledefine.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "anonymous_users")
@Data
public class AnonymousUser {

    @Id
    private UUID id;
}