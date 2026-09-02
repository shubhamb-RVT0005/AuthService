package com.shrayu.entity;

//import java.security.AuthProvider;

//package com.shrayu.entity;

import java.time.Instant;
import java.util.UUID;

import com.shrayu.entity.enums.AuthProvider;
import com.shrayu.entity.enums.AuthUserStatus;

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
    name = "auth_users",
    indexes = {
        @Index(
            name = "idx_auth_user_email",
            columnList = "email",
            unique = true
        ),
        @Index(
            name = "idx_auth_user_user_id",
            columnList = "user_id",
            unique = true
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        nullable = false,
        updatable = false
    )
    private UUID id;

    /**
     * Same UUID as User Service's User.id.
     *
     * Auth DB and User DB are separate.
     * Therefore, no JPA relationship is used.
     */
    @Column(
        name = "user_id",
        nullable = false,
        unique = true,
        updatable = false
    )
    private UUID userId;

    /**
     * Login email.
     */
    @Column(
        name = "email",
        nullable = false,
        unique = true,
        length = 254
    )
    private String email;

    /**
     * BCrypt password hash.
     *
     * NEVER store the raw password.
     */
    @Column(
        name = "password_hash",
        nullable = false,
        length = 255
    )
    private String passwordHash;

    /**
     * Authentication provider.
     *
     * LOCAL  -> Email/password login
     * GOOGLE -> Google OAuth2 login
     */
    @Enumerated(EnumType.STRING)
    @Column(
        name = "auth_provider",
        nullable = false,
        length = 30
    )
    private AuthProvider authProvider;

    /**
     * Authentication account status.
     */
    @Enumerated(EnumType.STRING)
    @Column(
        name = "status",
        nullable = false,
        length = 30
    )
    private AuthUserStatus status;

    /**
     * Email verification status.
     */
    @Column(
        name = "email_verified",
        nullable = false
    )
    private boolean emailVerified;

    /**
     * Failed login counter.
     *
     * Used for brute-force protection.
     */
    @Column(
        name = "failed_login_attempts",
        nullable = false
    )
    private int failedLoginAttempts;

    /**
     * Temporary account lock timestamp.
     *
     * NULL = account is not locked.
     */
    @Column(name = "locked_until")
    private Instant lockedUntil;

    /**
     * Last successful login timestamp.
     */
    @Column(name = "last_login_at")
    private Instant lastLoginAt;

    /**
     * Last password change timestamp.
     *
     * Useful for token/session invalidation strategies.
     */
    @Column(name = "password_changed_at")
    private Instant passwordChangedAt;

    /**
     * Account creation timestamp.
     */
    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    private Instant createdAt;

    /**
     * Account update timestamp.
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

        if (this.authProvider == null) {
            this.authProvider = AuthProvider.LOCAL;
        }

        if (this.status == null) {
            this.status = AuthUserStatus.ACTIVE;
        }

        if (this.failedLoginAttempts < 0) {
            this.failedLoginAttempts = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}