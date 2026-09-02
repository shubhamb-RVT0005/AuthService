package com.shrayu.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "refresh_tokens",
    indexes = {
        @Index(
            name = "idx_refresh_token_hash",
            columnList = "token_hash",
            unique = true
        ),
        @Index(
            name = "idx_refresh_token_user_id",
            columnList = "user_id"
        ),
        @Index(
            name = "idx_refresh_token_expires_at",
            columnList = "expires_at"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        nullable = false,
        updatable = false
    )
    private UUID id;

    /**
     * AuthUser.id from Auth Service.
     *
     * No JPA relationship is used.
     */
    @Column(
        name = "auth_user_id",
        nullable = false,
        updatable = false
    )
    private UUID authUserId;

    /**
     * User Service's User.id.
     *
     * Stored as UUID only because databases are separate.
     */
    @Column(
        name = "user_id",
        nullable = false,
        updatable = false
    )
    private UUID userId;

    /**
     * SHA-256/hash representation of the refresh token.
     *
     * NEVER store the raw refresh token.
     */
    @Column(
        name = "token_hash",
        nullable = false,
        unique = true,
        length = 64
    )
    private String tokenHash;

    /**
     * Refresh token expiration time.
     */
    @Column(
        name = "expires_at",
        nullable = false
    )
    private Instant expiresAt;

    /**
     * Whether this refresh token has been revoked.
     */
    @Column(
        name = "revoked",
        nullable = false
    )
    private boolean revoked;

    /**
     * Timestamp when token was revoked.
     */
    @Column(name = "revoked_at")
    private Instant revokedAt;

    /**
     * Used for refresh-token rotation.
     *
     * When token A is exchanged for token B:
     *
     * A -> replacedByTokenId = B
     */
    @Column(name = "replaced_by_token_id")
    private UUID replacedByTokenId;

    /**
     * Client/device information.
     */
    @Column(
        name = "device_info",
        length = 500
    )
    private String deviceInfo;

    /**
     * IP address from which the token was created.
     */
    @Column(
        name = "ip_address",
        length = 45
    )
    private String ipAddress;

    /**
     * Timestamp when refresh token was created.
     */
    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    private Instant createdAt;

    /**
     * Timestamp when entity was last updated.
     */
    @Column(
        name = "updated_at",
        nullable = false
    )
    private Instant updatedAt;


    // =========================================================
    // JPA Lifecycle
    // =========================================================

    @PrePersist
    protected void onCreate() {

        Instant now = Instant.now();

        this.createdAt = now;
        this.updatedAt = now;

        this.revoked = false;
    }

    @PreUpdate
    protected void onUpdate() {

        this.updatedAt = Instant.now();
    }


    // =========================================================
    // Security Helpers
    // =========================================================

    public boolean isExpired() {

        return expiresAt != null
                && Instant.now().isAfter(expiresAt);
    }

    public boolean isValid() {

        return !revoked && !isExpired();
    }

    public void revoke() {

        this.revoked = true;
        this.revokedAt = Instant.now();
    }
}










//
//package com.shrayu.entity;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.PrePersist;
//import jakarta.persistence.PreUpdate;
//import jakarta.persistence.Table;
//import jakarta.persistence.UniqueConstraint;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(
//    name = "refresh_tokens",
//    uniqueConstraints = {
//        @UniqueConstraint(
//            name = "uk_refresh_token_token_hash",
//            columnNames = "token_hash"
//        )
//    }
//)
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class RefreshToken {
//
//    // =========================================================
//    // PRIMARY KEY
//    // =========================================================
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(
//        name = "id",
//        nullable = false,
//        updatable = false
//    )
//    private UUID id;
//
//
//    // =========================================================
//    // USER ID
//    // =========================================================
//
//    @Column(
//        name = "user_id",
//        nullable = false,
//        updatable = false
//    )
//    private UUID userId;
//
//
//    // =========================================================
//    // REFRESH TOKEN HASH
//    // =========================================================
//
//    @Column(
//        name = "token_hash",
//        nullable = false,
//        unique = true,
//        length = 255
//    )
//    private String tokenHash;
//
//
//    // =========================================================
//    // EXPIRATION
//    // =========================================================
//
//    @Column(
//        name = "expires_at",
//        nullable = false
//    )
//    private LocalDateTime expiresAt;
//
//
//    // =========================================================
//    // TOKEN STATUS
//    // =========================================================
//
//    @Column(
//        name = "revoked",
//        nullable = false
//    )
//    @Builder.Default
//    private boolean revoked = false;
//
//
//    // =========================================================
//    // AUDITING
//    // =========================================================
//
//    @Column(
//        name = "created_at",
//        nullable = false,
//        updatable = false
//    )
//    private LocalDateTime createdAt;
//
//
//    @Column(
//        name = "updated_at",
//        nullable = false
//    )
//    private LocalDateTime updatedAt;
//
//
//    // =========================================================
//    // CREATE
//    // =========================================================
//
//    @PrePersist
//    protected void onCreate() {
//
//        LocalDateTime now = LocalDateTime.now();
//
//        createdAt = now;
//        updatedAt = now;
//    }
//
//
//    // =========================================================
//    // UPDATE
//    // =========================================================
//
//    @PreUpdate
//    protected void onUpdate() {
//
//        updatedAt = LocalDateTime.now();
//    }
//}
