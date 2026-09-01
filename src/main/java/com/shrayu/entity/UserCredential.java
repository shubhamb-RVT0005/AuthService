
package com.shrayu.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
    name = "user_credentials",
    uniqueConstraints = {

        @UniqueConstraint(
            name = "uk_user_credentials_user_id",
            columnNames = "user_id"
        ),

        @UniqueConstraint(
            name = "uk_user_credentials_username",
            columnNames = "username"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCredential {

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
    // USERNAME
    // =========================================================

    @Column(
        name = "username",
        nullable = false,
        unique = true,
        length = 100
    )
    private String username;


    // =========================================================
    // PASSWORD HASH
    // =========================================================

    @Column(
        name = "password_hash",
        nullable = false,
        length = 255
    )
    private String passwordHash;


    // =========================================================
    // ROLE
    // =========================================================

    @Convert(converter = RolesConverter.class)
    @Column(
        name = "role",
        nullable = false
    )
    @Builder.Default
    private Roles role = Roles.USER;


    // =========================================================
    // ACCOUNT ENABLED
    // =========================================================

    @Column(
        name = "enabled",
        nullable = false
    )
    @Builder.Default
    private boolean enabled = true;


    // =========================================================
    // ACCOUNT LOCKED
    // =========================================================

    @Column(
        name = "account_locked",
        nullable = false
    )
    @Builder.Default
    private boolean accountLocked = false;


    // =========================================================
    // CREATED AT
    // =========================================================

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    private LocalDateTime createdAt;


    // =========================================================
    // UPDATED AT
    // =========================================================

    @Column(
        name = "updated_at",
        nullable = false
    )
    private LocalDateTime updatedAt;


    // =========================================================
    // BEFORE INSERT
    // =========================================================

    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;
    }


    // =========================================================
    // BEFORE UPDATE
    // =========================================================

    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
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
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
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
//    name = "user_credentials",
//    uniqueConstraints = {
//        @UniqueConstraint(
//            name = "uk_user_credentials_user_id",
//            columnNames = "user_id"
//        ),
//        @UniqueConstraint(
//            name = "uk_user_credentials_username",
//            columnNames = "username"
//        )
//    }
//)
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class UserCredential {
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
//    @Column(
//        name = "user_id",
//        nullable = false,
//        updatable = false
//    )
//    private UUID userId;
//
//
//    @Column(
//        name = "username",
//        nullable = false,
//        unique = true,
//        length = 100
//    )
//    private String username;
//
//
//    @Column(
//        name = "password_hash",
//        nullable = false,
//        length = 255
//    )
//    private String passwordHash;
//
////
////    @Enumerated(EnumType.STRING)
////    @Column(
////        name = "role",
////        nullable = false,
////        length = 50
////    )
////    @Builder.Default
////    private Role role = Role.USER;
//
//    
//    
//    
//    @Convert(converter = RolesConverter.class)
//    @Column(
//        name = "role",
//        nullable = false
//    )
//    @Builder.Default
//    private Roles role = Roles.USER;
//    
//    
//    
//
//    @Column(
//        name = "enabled",
//        nullable = false
//    )
//    @Builder.Default
//    private boolean enabled = true;
//
//
//    @Column(
//        name = "account_locked",
//        nullable = false
//    )
//    @Builder.Default
//    private boolean accountLocked = false;
//
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
//    @PreUpdate
//    protected void onUpdate() {
//
//        updatedAt = LocalDateTime.now();
//    }
//}
//




//package com.shrayu.entity;
//
//public class UserCredential {
//
//}
//UserCredential
//-------------------------
//id
//userId
//username
//passwordHash
//enabled
//accountLocked
//createdAt
//updatedAt

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
//    name = "user_credentials",
//    uniqueConstraints = {
//        @UniqueConstraint(
//            name = "uk_user_credentials_user_id",
//            columnNames = "user_id"
//        ),
//        @UniqueConstraint(
//            name = "uk_user_credentials_username",
//            columnNames = "username"
//        )
//    }
//)
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class UserCredential {
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
//    // USERNAME
//    // =========================================================
//
//    @Column(
//        name = "username",
//        nullable = false,
//        unique = true,
//        length = 100
//    )
//    private String username;
//
//
//    // =========================================================
//    // PASSWORD HASH
//    // =========================================================
//
//    @Column(
//        name = "password_hash",
//        nullable = false,
//        length = 255
//    )
//    private String passwordHash;
//
//
//    // =========================================================
//    // ACCOUNT STATUS
//    // =========================================================
//
//    @Column(
//        name = "enabled",
//        nullable = false
//    )
//    @Builder.Default
//    private boolean enabled = true;
//
//
//    @Column(
//        name = "account_locked",
//        nullable = false
//    )
//    @Builder.Default
//    private boolean accountLocked = false;
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
//    // CREATE TIMESTAMP
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
//    // UPDATE TIMESTAMP
//    // =========================================================
//
//    @PreUpdate
//    protected void onUpdate() {
//
//        updatedAt = LocalDateTime.now();
//    }
//}
