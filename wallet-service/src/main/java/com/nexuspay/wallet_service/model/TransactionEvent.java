package com.nexuspay.wallet_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEvent {
    private String userId;
    private BigDecimal amount;
    private String type;
}