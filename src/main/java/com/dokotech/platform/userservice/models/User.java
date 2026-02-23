package com.dokotech.platform.userservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String passwordHash;
    private String fullName;

    // OAuth2 provider (GOOGLE, FACEBOOK, APPLE, null for local users)
    private String provider;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    private Boolean isActive = true;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
