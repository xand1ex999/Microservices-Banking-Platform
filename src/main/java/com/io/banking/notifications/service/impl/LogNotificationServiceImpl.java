package com.io.banking.notifications.service.impl;

import com.io.banking.notifications.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
public class LogNotificationServiceImpl implements NotificationService {

    @Override
    public void notifyUserRegistered(Long userId, String email) {
        log.info("[NOTIFICATION] New user registered: id={}, email={}", userId, email);
    }

    @Override
    public void notifyDeposit(Long userId, BigDecimal amount) {
        log.info("[NOTIFICATION] Deposit: userId={}, amount={}", userId, amount);
    }

    @Override
    public void notifyWithdrawal(Long userId, BigDecimal amount) {
        log.info("[NOTIFICATION] Withdrawal: userId={}, amount={}", userId, amount);
    }

    @Override
    public void notifyAccountFrozen(UUID accountId) {
        log.info("[NOTIFICATION] Account frozen: accountId={}", accountId);
    }
}
