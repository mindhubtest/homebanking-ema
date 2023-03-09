package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> findByAccountNumber(String number);
    void save(Transaction transaction);
}
