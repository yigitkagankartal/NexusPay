package com.nexuspay.wallet_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String type;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    // Kayıt atılmadan hemen önce zamanı otomatik set etsin (PrePersist)
    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }
}
