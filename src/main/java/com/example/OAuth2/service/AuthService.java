package com.example.oauth2.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.oauth2.repo.RoleRepository;
import com.example.oauth2.repo.UserRepository;
import com.example.oauth2.security.JwtUtil;
import com.example.oauth2.security.TokenBlacklistService;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final TokenBlacklistService tokenBlacklist;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepo, RoleRepository roleRepo, JwtUtil jwtUtil, RefreshTokenService refreshTokenService, TokenBlacklistService tokenBlacklist, PasswordEncoder passwordEncoder ){
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
        this.tokenBlacklist = tokenBlacklist;
        this.passwordEncoder = passwordEncoder;
    }

}