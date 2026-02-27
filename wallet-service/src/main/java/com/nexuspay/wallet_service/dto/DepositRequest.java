package com.nexuspay.wallet_service.dto;
import lombok.Data;

@Data
public class DepositRequest {
    private String username;
    private Double amount;
}