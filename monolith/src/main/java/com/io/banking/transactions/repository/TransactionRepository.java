package com.io.banking.transactions.repository;

import com.io.banking.transactions.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("""
            SELECT t
            FROM Transaction t
            WHERE t.account.user.id = :userId
            ORDER BY t.createdAt DESC
            """)
    List<Transaction> findMyTransactions(@Param("userId") Long userId);

}
