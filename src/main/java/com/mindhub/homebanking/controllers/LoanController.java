package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping
public class LoanController {
    @Autowired
    ClientLoanRepository clientLoanService;

    @Autowired
    LoanService loanService;

    @Autowired
    ClientService clientService;

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;



    @GetMapping("/api/loan")
    public List<LoanDTO> getClients() {

        return loanService.getLoans().stream().map(LoanDTO::new).collect(toList());
    }



    @PostMapping("/api/loan/")
    public ResponseEntity<?> createLoan(Authentication authentication, @RequestParam String name, @RequestParam double maxAmount, @RequestParam List<Integer> payments, @RequestParam double fee){

        if(maxAmount == 0 || name.isEmpty()  || fee == 0){
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }

       loanService.save(new Loan(name,maxAmount,payments,fee));

        return new ResponseEntity<>("Loan created", HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/api/clients/current/loans")
    public ResponseEntity<?> createClientLoan(@RequestBody LoanApplicationDTO loanApplicationDTO,
            Authentication authentication ) {

        Account accountDest = this.accountService.findByNumber(loanApplicationDTO.getAccountNumber());
        Loan loan = this.loanService.findById(loanApplicationDTO.getIdLoan());
        Client client = this.clientService.findByEmail(authentication.getName());

       if(loanApplicationDTO.getAmount() == 0 || loanApplicationDTO.getPayments() == 0.00 || loanApplicationDTO.getAccountNumber().isEmpty()) {
           return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
       }

       if (loan.getMaxAmount() < loanApplicationDTO.getAmount() ){
           return new ResponseEntity<>("Ammount error", HttpStatus.FORBIDDEN);
       }

       if(accountDest == null){
           return new ResponseEntity<>("Account doesn't exist",HttpStatus.FORBIDDEN);
       }
       if(!loan.getPayments().contains(loanApplicationDTO.getPayments())){
           return new ResponseEntity<>("Payments Error",HttpStatus.FORBIDDEN);
       }

       if (!client.getAccounts().contains(accountDest)){
           return new ResponseEntity<>("client don't have this account", HttpStatus.FORBIDDEN);
       }
        clientLoanService.save(new ClientLoan(loanApplicationDTO.getAmount() +loanApplicationDTO.getAmount() * loan.getPorcentaje(),loanApplicationDTO.getPayments(),client,loan));
       transactionService.save(new Transaction(loanApplicationDTO.getAmount(), LocalDate.now(),TransactionType.Credito,loan.getName()+ " " + "Loan Approved",accountDest.getBalance() + loanApplicationDTO.getAmount(),accountDest));
       accountDest.setBalance(accountDest.getBalance() + loanApplicationDTO.getAmount());
       accountService.save(accountDest);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


}
