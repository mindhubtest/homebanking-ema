package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class LoanApplicationDTO {
        private  String accountNumber;
        private long idLoan;
        private double amount;
        private int payments;


        public LoanApplicationDTO() {
        }

    public LoanApplicationDTO(String accountNumber, long idLoan, double amount, int payments) {
        this.accountNumber = accountNumber;
        this.idLoan = idLoan;
        this.amount = amount;
        this.payments = payments;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getIdLoan() {
        return idLoan;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }
}
