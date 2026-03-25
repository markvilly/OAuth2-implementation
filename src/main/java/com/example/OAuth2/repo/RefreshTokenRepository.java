package com.example.oauth2.repo;

import java.util.List;
import java.util.Optional;

import com.example.oauth2.entity.User;
import com.example.oauth2.entity.RefreshToken;


import org.springframework.data.jpa.repository.JpaRepository;

public interface  RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    List<RefreshToken> findByUser(User user);
}