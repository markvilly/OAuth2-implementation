package com.example.oauth2.dto;

import lombok.Data;



@Data
public class RefreshRequest {
    private String refreshToken;
}