
package com.shrayu.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "refresh_tokens",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_refresh_token_token_hash",
            columnNames = "token_hash"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    // =========================================================
    // PRIMARY KEY
    // =========================================================

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        nullable = false,
        updatable = false
    )
    private UUID id;


    // =========================================================
    // USER ID
    // =========================================================

    @Column(
        name = "user_id",
        nullable = false,
        updatable = false
    )
    private UUID userId;


    // =========================================================
    // REFRESH TOKEN HASH
    // =========================================================

    @Column(
        name = "token_hash",
        nullable = false,
        unique = true,
        length = 255
    )
    private String tokenHash;


    // =========================================================
    // EXPIRATION
    // =========================================================

    @Column(
        name = "expires_at",
        nullable = false
    )
    private LocalDateTime expiresAt;


    // =========================================================
    // TOKEN STATUS
    // =========================================================

    @Column(
        name = "revoked",
        nullable = false
    )
    @Builder.Default
    private boolean revoked = false;


    // =========================================================
    // AUDITING
    // =========================================================

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    private LocalDateTime createdAt;


    @Column(
        name = "updated_at",
        nullable = false
    )
    private LocalDateTime updatedAt;


    // =========================================================
    // CREATE
    // =========================================================

    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;
    }


    // =========================================================
    // UPDATE
    // =========================================================

    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}
