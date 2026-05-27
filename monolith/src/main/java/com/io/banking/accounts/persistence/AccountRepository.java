package com.io.banking.accounts.persistence;

import com.io.banking.accounts.model.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findAllByUserId(Long userId);

    Page<Account> findAll(Pageable pageable);

}
