package com.example.oauth2.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.oauth2.entity.RefreshToken;
import com.example.oauth2.entity.User;
import com.example.oauth2.repo.RefreshTokenRepository;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshtokenrepo;

    public RefreshTokenService(RefreshTokenRepository refreshtokenrepo){
        this.refreshtokenrepo = refreshtokenrepo;
    }

    public RefreshToken createRefreshToken(User user){
        RefreshToken token = RefreshToken.builder()
            .token(UUID.randomUUID().toString())
            .user(user)
            .expiryDate(LocalDateTime.now().plusDays(7))
            .revoked(false)
            .build();
        return refreshtokenrepo.save(token);
    }

    public void validateRefreshToken(String token){
        RefreshToken refreshToken = refreshtokenrepo.findByToken(token)
            .orElseThrow(()-> new RuntimeException("Not Found"));

        if(refreshToken.isRevoked()){
            throw new RuntimeException("Refresh Token has been revoked!");
        }

        if(refreshToken.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Refresh Token has expired");
        }

    }

    public RefreshToken rotateRefreshToken(String token){
        validateRefreshToken(token);
        RefreshToken existingToken = refreshtokenrepo.findByToken(token)
            .orElseThrow(()-> new RuntimeException("Not Found"));

        existingToken.setRevoked(true);

        RefreshToken newToken = RefreshToken.builder()
            .token(UUID.randomUUID().toString())
            .user(existingToken.getUser())
            .expiryDate(LocalDateTime.now().plusDays(7))
            .build();
        return refreshtokenrepo.save(newToken);
    }

    public void revokeAllUserTokens(User user){
        
    }

}