package com.example.oauth2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserExcetion;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
@Builder
@Table(name="users")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(unique=true, nullable=false)
    private String username;

    @Column()
    private String password;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column(nullable=false)
    private boolean enabled;
    
    Column(nullable=false)
    private Set<Role> roles;
    
    
    private LocalDateTime createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return roles.stream()
    }


}
