package com.nexuspay.wallet_service.service;

import com.nexuspay.wallet_service.model.TransactionEvent;
import com.nexuspay.wallet_service.model.Wallet;
import com.nexuspay.wallet_service.model.WalletTransaction;
import com.nexuspay.wallet_service.repository.TransactionRepository;
import com.nexuspay.wallet_service.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    private String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Transactional
    public void addFunds(BigDecimal amount) {
        String userId = getCurrentUser();

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseGet(() -> new Wallet(null, userId, BigDecimal.ZERO, "USD", 0L));
        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);
        redisTemplate.opsForValue().set("wallet:" + userId, wallet.getBalance().toString());
        kafkaTemplate.send("wallet-transactions", new TransactionEvent(userId, amount, "DEPOSIT"));
    }

    @Transactional
    public void withdrawFunds(BigDecimal amount) {
        String userId = getCurrentUser();
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cüzdan bulunamadı!"));

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Yetersiz bakiye!");
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);

        redisTemplate.opsForValue().set("wallet:" + userId, wallet.getBalance().toString());
        kafkaTemplate.send("wallet-transactions", new TransactionEvent(userId, amount, "WITHDRAW"));
    }

    public BigDecimal getBalance() {
        String userId = getCurrentUser();
        Object cached = redisTemplate.opsForValue().get("wallet:" + userId);
        if (cached != null) return new BigDecimal(cached.toString());

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseGet(() -> walletRepository.save(new Wallet(null, userId, BigDecimal.ZERO, "USD", 0L)));

        redisTemplate.opsForValue().set("wallet:" + userId, wallet.getBalance().toString());
        return wallet.getBalance();
    }

    public List<WalletTransaction> getTransactionHistory() {
        return transactionRepository.findByUserIdOrderByTimestampDesc(getCurrentUser());
    }
}