package com.example.noticeboardprj.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class UserEntity {
//
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, length = 20)
    private String username;
    @Column(length = 255)
    private String password;
    @Column(length = 50)
    private String role;
    @Column(length = 50)
    private String email;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_date;
    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime modified_date;
    private LocalDateTime removed_date;
}
