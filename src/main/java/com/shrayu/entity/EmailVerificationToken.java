package com.shrayu.entity;



import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    name = "email_verification_tokens",
    indexes = {
        @Index(
            name = "idx_email_verification_token_hash",
            columnList = "token_hash",
            unique = true
        ),
        @Index(
            name = "idx_email_verification_auth_user_id",
            columnList = "auth_user_id"
        ),
        @Index(
            name = "idx_email_verification_expires_at",
            columnList = "expires_at"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class EmailVerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        nullable = false,
        updatable = false
    )
    private UUID id;

    /**
     * AuthUser.id.
     *
     * No JPA relationship because Auth Service
     * manages its authentication database independently.
     */
    @Column(
        name = "auth_user_id",
        nullable = false,
        updatable = false
    )
    private UUID authUserId;

    /**
     * SHA-256 hash of the verification token.
     *
     * NEVER store the raw verification token.
     */
    @Column(
        name = "token_hash",
        nullable = false,
        unique = true,
        length = 64
    )
    private String tokenHash;

    /**
     * Verification token expiration time.
     */
    @Column(
        name = "expires_at",
        nullable = false
    )
    private Instant expiresAt;

    /**
     * One-time-use protection.
     */
    @Column(
        name = "used",
        nullable = false
    )
    private boolean used;

    /**
     * Timestamp when verification was completed.
     */
    @Column(name = "used_at")
    private Instant usedAt;

    /**
     * IP address from which verification was requested.
     */
    @Column(
        name = "request_ip",
        length = 45
    )
    private String requestIp;

    /**
     * Browser/device information.
     */
    @Column(
        name = "user_agent",
        length = 500
    )
    private String userAgent;

    /**
     * Creation timestamp.
     */
    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    private Instant createdAt;

    /**
     * Last update timestamp.
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

        this.used = false;
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

        return !used && !isExpired();
    }

    public void markAsUsed() {

        this.used = true;
        this.usedAt = Instant.now();
    }
}




//
//Your complete Auth entities are now
//entity
//│
//├── AuthUser.java
//│   ├── UUID
//│   ├── userId
//│   ├── email
//│   ├── passwordHash
//│   ├── authProvider
//│   ├── status
//│   ├── emailVerified
//│   ├── failedLoginAttempts
//│   ├── lockedUntil
//│   ├── lastLoginAt
//│   ├── passwordChangedAt
//│   └── timestamps
//│
//├── RefreshToken.java
//│   ├── hashed token
//│   ├── expiration
//│   ├── revocation
//│   ├── rotation
//│   ├── device
//│   └── IP
//│
//├── PasswordResetToken.java
//│   ├── hashed token
//│   ├── expiration
//│   ├── one-time use
//│   ├── IP
//│   └── user-agent
//│
//└── EmailVerificationToken.java
//    ├── hashed token
//    ├── expiration
//    ├── one-time use
//    ├── IP
//    └── user-agent
//Security flow
//REGISTER
//   ↓
//AuthUser
//   ↓
//password → BCrypt
//   ↓
//EmailVerificationToken
//   ↓
//Email
//   ↓
//Verify
//   ↓
//emailVerified = true
//
//Then:
//
//LOGIN
//   ↓
//BCrypt password verification
//   ↓
//JWT Access Token
//   +
//Refresh Token
//
//And:
//
//FORGOT PASSWORD
//   ↓
//PasswordResetToken
//   ↓
//Email
//   ↓
//Reset Password
//   ↓
//Invalidate reset token
//   ↓
//Invalidate existing refresh sessions
//
//One important production point: the entities don't generate tokens or passwords themselves. Token generation, hashing, expiration policy, rotation, and revocation will be implemented in the service/security layer.
//
//Next layer
//
//Now that the entities are finished, the correct next step is:
//
//repository
//├── AuthUserRepository.java
//├── RefreshTokenRepository.java
//├── PasswordResetTokenRepository.java
//└── EmailVerificationTokenRepository.java