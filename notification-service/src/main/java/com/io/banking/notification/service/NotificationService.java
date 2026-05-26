package com.io.banking.notification.service;

import java.math.BigDecimal;
import java.util.UUID;

public interface NotificationService {

    void notifyUserRegistered(Long userId, String email);

    void notifyDeposit(Long userId, BigDecimal amount);

    void notifyWithdrawal(Long userId, BigDecimal amount);

    void notifyAccountFrozen(UUID accountId);
}
