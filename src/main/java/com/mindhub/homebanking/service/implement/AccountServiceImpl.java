package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account findByNumber(String number) {
        return this.accountRepository.findByNumber(number);
    }

    @Override
    public List<Account> findByStatus(boolean status) {
        return this.accountRepository.findByStatus(status);
    }

    @Override
    public List<Account> getAccounts() {
        return this.accountRepository.findAll();
    }

    @Override
    public Optional<Account> getAccount(long id) {
        return this.accountRepository.findById(id);
    }

    @Override
    public void save(Account account) {
        this.accountRepository.save(account);
    }
}
