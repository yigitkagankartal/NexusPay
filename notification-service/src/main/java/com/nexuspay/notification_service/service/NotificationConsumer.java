package com.nexuspay.notification_service.service;

import com.nexuspay.auth_service.dto.UserCreatedEvent;
import com.nexuspay.wallet_service.model.TransactionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationConsumer {

    @KafkaListener(topics = "user-created-topic", groupId = "notification-group")
    public void consumeUserCreated(UserCreatedEvent event) {
        log.info("🔔 BİLDİRİM: Hoş geldin {}, NexusPay hesabın başarıyla oluşturuldu! (ID: {})",
                event.getUsername(), event.getUserId());
    }

    @KafkaListener(topics = "wallet-transactions", groupId = "notification-group")
    public void consumeTransaction(TransactionEvent event) {
        String action = event.getType().equals("DEPOSIT") ? "YATIRILDI" : "ÇEKİLDİ";
        log.info("💰 FİNANSAL BİLDİRİM: Sayın {}, {} tutarındaki işleminiz başarıyla gerçekleşti. İşlem Tipi: {}",
                event.getUserId(), event.getAmount(), action);
    }
}