//package com.io.banking.notifications.listener;
//
//import com.io.banking.auth.event.UserRegisteredEvent;
//import com.io.banking.notifications.service.NotificationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.event.EventListener;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class NotificationEventListener {
//
//    private final NotificationService notificationService;
//
//    @Async
//    @EventListener
//    public void onUserRegistered(UserRegisteredEvent event) {
//        notificationService.notifyUserRegistered(event.getUserId(), event.getEmail());
//    }
//
//    @Async
//    @EventListener
//    public void onMoneyDeposited(MoneyDepositedEvent event) {
//        notificationService.notifyDeposit(event.getUserId(), event.getAmount());
//    }
//
//    @Async
//    @EventListener
//    public void onMoneyWithdrawn(MoneyWithdrawnEvent event) {
//        notificationService.notifyWithdrawal(event.getUserId(), event.getAmount());
//    }
//
//    @Async
//    @EventListener
//    public void onAccountFrozen(AccountFrozenEvent event) {
//        notificationService.notifyAccountFrozen(event.getAccountId());
//    }
//}
