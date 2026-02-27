package com.nexuspay.wallet_service.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransactionEvent {
    private String userId;
    private BigDecimal amount;
    private String type;
}