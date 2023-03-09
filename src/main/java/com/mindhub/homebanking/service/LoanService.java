package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    Loan findById(long id);
    List<Loan> getLoans();
    void save(Loan loan);
}
