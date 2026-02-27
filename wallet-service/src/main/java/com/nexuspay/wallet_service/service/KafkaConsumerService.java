package com.nexuspay.wallet_service.service;

import com.nexuspay.auth_service.dto.UserCreatedEvent;
import com.nexuspay.wallet_service.model.TransactionEvent;
import com.nexuspay.wallet_service.model.Wallet;
import com.nexuspay.wallet_service.model.WalletTransaction;
import com.nexuspay.wallet_service.repository.TransactionRepository;
import com.nexuspay.wallet_service.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    @KafkaListener(topics = "wallet-transactions", groupId = "wallet-group")
    public void consumeTransaction(TransactionEvent event) {
        log.info("Kafka'dan İşlem Paketi geldi! Kullanıcı ID: {}", event.getUserId());

        WalletTransaction tx = new WalletTransaction();
        tx.setUserId(event.getUserId());
        tx.setType(event.getType());
        tx.setAmount(event.getAmount());
        tx.setTimestamp(LocalDateTime.now());

        transactionRepository.save(tx);
        log.info("İşlem geçmişe kaydedildi! Tutar: {}", event.getAmount());
    }
    @KafkaListener(topics = "user-created-topic", groupId = "wallet-group")
    public void consumeUserCreated(UserCreatedEvent event) {
        log.info("Kafka'dan Yeni Kullanıcı Bildirimi Geldi: {} (ID: {})", event.getUsername(), event.getUserId());

        if (walletRepository.findByUserId(event.getUsername()).isEmpty()) {
            Wallet wallet = new Wallet();
            wallet.setUserId(event.getUsername());
            wallet.setBalance(BigDecimal.ZERO);
            wallet.setCurrency("USD");
            wallet.setVersion(0L);

            walletRepository.save(wallet);
            log.info("Sistem: {} için otomatik cüzdan oluşturuldu.", event.getUsername());
        } else {
            log.warn("Kullanıcı {} için zaten bir cüzdan mevcut, atlanıyor.", event.getUsername());
        }
    }
}