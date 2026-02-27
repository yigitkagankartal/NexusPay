package com.nexuspay.auth_service.dto;

import lombok.Data;

@Data
public class UserCreatedEvent {
    private Long userId;
    private String username;
    private String email;
}