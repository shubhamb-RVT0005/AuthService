//package com.shrayu.entity;
//
//public class Permission {
//
//}
//Permission
//----------------
//id
//name
//
//1  USER_READ
//2  USER_UPDATE
//3  ORDER_CREATE
//4  ORDER_DELETE



package com.shrayu.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
    name = "permissions",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_permission_name",
            columnNames = "name"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
        name = "id",
        nullable = false,
        updatable = false
    )
    private UUID id;


    @Column(
        name = "name",
        nullable = false,
        unique = true,
        length = 100
    )
    private String name;


    @Column(
        name = "description",
        length = 255
    )
    private String description;


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


    @ManyToMany(mappedBy = "permissions")
    @Builder.Default
    private Set<Role> roles = new HashSet<>();


    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;
    }


    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}



//
//
//permissions
//------------------------------------------------------------
//id | name          | description       | created_at | updated_at
//------------------------------------------------------------
//UUID | USER_READ   | Read users        | ...        | ...
//UUID | USER_UPDATE | Update users      | ...        | ...
//UUID | ORDER_CREATE| Create orders     | ...        | ...
//UUID | ORDER_DELETE| Delete orders     | ...        | ...
