package com.nexuspay.wallet_service.controller;

import com.nexuspay.wallet_service.model.WalletTransaction;
import com.nexuspay.wallet_service.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestParam BigDecimal amount) {
        walletService.addFunds(amount);
        return ResponseEntity.ok("Yatırım işlemi başarılı, bakiye güncellendi.");
    }

    // Para Çekme
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam BigDecimal amount) {
        walletService.withdrawFunds(amount);
        return ResponseEntity.ok("Çekim işlemi başarılı, bakiye güncellendi.");
    }

    // Kendi Bakiyesini Sorgulama
    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance() {
        return ResponseEntity.ok(walletService.getBalance());
    }

    // Kendi İşlem Geçmişini Sorgulama
    @GetMapping("/transactions")
    public ResponseEntity<List<WalletTransaction>> getHistory() {
        return ResponseEntity.ok(walletService.getTransactionHistory());
    }
}