package com.nexuspay.wallet_service.repository;

import com.nexuspay.wallet_service.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<WalletTransaction, Long> {
    List<WalletTransaction> findByUserIdOrderByTimestampDesc(String userId);
}