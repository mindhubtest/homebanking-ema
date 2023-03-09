package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> findByAccountNumber(String number) {
        return this.transactionRepository.findByAccountNumber(number);
    }

    @Override
    public void save(Transaction transaction) {
        this.transactionRepository.save(transaction);
    }
}
