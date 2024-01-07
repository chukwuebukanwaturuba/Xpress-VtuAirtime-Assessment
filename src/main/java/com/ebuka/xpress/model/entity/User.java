package com.ebuka.xpress.model.entity;

import com.ebuka.xpress.model.enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "users_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "email")
    private String email;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(name = "phone_number")
    private String phoneNumbers;
    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Column(name = "first_login")
    private Date firstLoginDate;

    @Column(name = "last_login")
    private Date lastLoginDate;

    @CreationTimestamp
    @Column(name = "creation_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private Date updatedAt;
}
