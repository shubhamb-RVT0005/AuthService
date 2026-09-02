package com.shrayu.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthResponse {

    private UUID userId;

    private String username;

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private long expiresIn;

    private List<String> roles;

    private List<String> permissions;
}

//
//{
//	  "userId": "7b9f...",
//	  "username": "shubham",
//	  "accessToken": "eyJhbGci...",
//	  "refreshToken": "....",
//	  "tokenType": "Bearer",
//	  "expiresIn": 900,
//	  "roles": [
//	    "USER"
//	  ],
//	  "permissions": [
//	    "USER_READ"
//	  ]
//	}