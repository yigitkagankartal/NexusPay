package com.nexuspay.wallet_service.repository;

import com.nexuspay.wallet_service.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUserId(String userId);
}