package com.example.service;

import com.example.model.Account;

import java.util.List;

public interface AccountService {
    Account findById(long id);

    Account findByName(String account);

    void saveAccount(Account account);

    void updateAccount(Account account);

    void deleteAccountById(long id);

    List<Account> findAllAccounts();

    void deleteAllAccounts();

    public boolean isAccountExist(Account account);
}
