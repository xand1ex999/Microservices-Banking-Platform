package com.io.banking.accounts.model.entity;

import com.io.banking.accounts.model.enums.AccountStatus;
import com.io.banking.accounts.model.enums.Currency;
import com.io.banking.transactions.model.entity.Transaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions = new ArrayList<>();

    private Instant createdAt;
}
