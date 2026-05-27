package com.io.banking.notification.service.impl;

import com.io.banking.notification.model.entity.Notification;
import com.io.banking.notification.model.enums.NotificationStatus;
import com.io.banking.notification.model.enums.NotificationType;
import com.io.banking.notification.persistence.NotificationRepository;
import com.io.banking.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void notifyUserRegistered(Long userId, String email) {
        save(Notification.builder()
                .userId(userId)
                .email(email)
                .type(NotificationType.USER_REGISTERED)
                .status(NotificationStatus.SENT)
                .message("Welcome! Your account has been created.")
                .build());
        log.info("[NOTIFICATION] USER_REGISTERED persisted: userId={}", userId);
    }

    @Override
    @Transactional
    public void notifyDeposit(Long userId, BigDecimal amount) {
        save(Notification.builder()
                .userId(userId)
                .type(NotificationType.DEPOSIT)
                .status(NotificationStatus.SENT)
                .message("Deposit of %s processed successfully.".formatted(amount))
                .build());
        log.info("[NOTIFICATION] DEPOSIT persisted: userId={}", userId);
    }

    @Override
    @Transactional
    public void notifyWithdrawal(Long userId, BigDecimal amount) {
        save(Notification.builder()
                .userId(userId)
                .type(NotificationType.WITHDRAWAL)
                .status(NotificationStatus.SENT)
                .message("Withdrawal of %s processed successfully.".formatted(amount))
                .build());
        log.info("[NOTIFICATION] WITHDRAWAL persisted: userId={}", userId);
    }

    @Override
    @Transactional
    public void notifyAccountFrozen(UUID accountId, Long userId) {
        save(Notification.builder()
                .userId(userId)
                .type(NotificationType.ACCOUNT_FROZEN)
                .status(NotificationStatus.SENT)
                .message("Account %s has been frozen.".formatted(accountId))
                .build());
        log.info("[NOTIFICATION] ACCOUNT_FROZEN persisted: userId={}, accountId={}", userId, accountId);
    }

    private void save(Notification notification) {
        notificationRepository.save(notification);
    }
}
