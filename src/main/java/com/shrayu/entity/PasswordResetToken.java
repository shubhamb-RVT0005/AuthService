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
    name = "password_reset_tokens",
    indexes = {
        @Index(
            name = "idx_password_reset_token_hash",
            columnList = "token_hash",
            unique = true
        ),
        @Index(
            name = "idx_password_reset_auth_user_id",
            columnList = "auth_user_id"
        ),
        @Index(
            name = "idx_password_reset_expires_at",
            columnList = "expires_at"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class PasswordResetToken {

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
     * No JPA relationship because authentication
     * data is handled independently.
     */
    @Column(
        name = "auth_user_id",
        nullable = false,
        updatable = false
    )
    private UUID authUserId;

    /**
     * SHA-256/hash of the reset token.
     *
     * NEVER store the raw reset token.
     */
    @Column(
        name = "token_hash",
        nullable = false,
        unique = true,
        length = 64
    )
    private String tokenHash;

    /**
     * Token expiration time.
     *
     * Password reset tokens should have a short lifetime.
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
     * Timestamp when token was consumed.
     */
    @Column(name = "used_at")
    private Instant usedAt;

    /**
     * Optional request IP for security auditing.
     */
    @Column(
        name = "request_ip",
        length = 45
    )
    private String requestIp;

    /**
     * Optional user-agent/device information.
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
//
//Production flow
//FORGOT PASSWORD
//       │
//       ▼
//User enters email
//       │
//       ▼
//Auth Service
//       │
//       ├── Find AuthUser
//       │
//       ├── Generate secure random token
//       │
//       ├── Hash token
//       │
//       ├── Save hash
//       │
//       └── Email raw token/link
//                    │
//                    ▼
//              User clicks link
//                    │
//                    ▼
//              RESET PASSWORD
//                    │
//                    ├── Hash incoming token
//                    ├── Find token
//                    ├── Check expiration
//                    ├── Check used
//                    ├── Change password
//                    ├── Mark token USED
//                    └── Revoke existing sessions
//Important security behavior
//
//A reset token should be:
//
//Random + high entropy
//        ↓
//Hash
//        ↓
//Database
//
//Not:
//
//"123456"
//"reset123"
//"email@example.com"
//
//Also, when the password is successfully reset, we should later invalidate/revoke existing refresh-token sessions so an old logged-in session cannot remain active after a password reset.
//
//We now have:
//
//entity
//├── AuthUser.java              ✅
//├── RefreshToken.java          ✅
//├── PasswordResetToken.java    ✅
//└── EmailVerificationToken.java ⬅ next