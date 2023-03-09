package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.FilterDateDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")



public class TransactionController {

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    ClientService clientService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(
            @RequestParam double amount, @RequestParam String description,
            @RequestParam String AccOrigen, @RequestParam String AccDest,
            Authentication authentication ) {

        Client client = this.clientService.findByEmail(authentication.getName());

        Account accountOrigin = this.accountService.findByNumber(AccOrigen);

        Account accountDest = this.accountService.findByNumber(AccDest);

        if (amount == 0 || description.isEmpty() || AccOrigen.isEmpty() || AccDest.isEmpty() ){
            return new ResponseEntity<>("Missing Data1", HttpStatus.FORBIDDEN);
        }
        if(accountOrigin == accountDest || accountOrigin == null || !client.getAccounts().contains(accountOrigin)){
            return new ResponseEntity<>("Missing Data2", HttpStatus.FORBIDDEN);
        }
        if(accountDest == null || accountOrigin.getBalance() < amount ){
            return new ResponseEntity<>("Missing Data3", HttpStatus.FORBIDDEN);
        }
        System.out.println(accountOrigin.getBalance());
        System.out.println(amount);



        transactionService.save(new Transaction(amount,LocalDate.now(),TransactionType.Debito,description  + AccDest,accountOrigin.getBalance() - amount,accountOrigin));
        transactionService.save(new Transaction(amount,LocalDate.now(),TransactionType.Credito,description  +AccOrigen,accountDest.getBalance() + amount,accountDest));

        double debito = accountOrigin.getBalance() - amount;
        double credito = accountDest.getBalance() + amount;

        accountOrigin.setBalance(debito);
        accountDest.setBalance(credito);
        return new ResponseEntity<>(HttpStatus.CREATED);


    }

    @PostMapping("/transactions/filter")
    public ResponseEntity<?> filterDate( @RequestBody FilterDateDTO filterDateDTO){

        Account account = this.accountService.findByNumber(filterDateDTO.getNumber());



       List<LocalDate> localDates = filterDateDTO.getDesde().datesUntil(filterDateDTO.getHasta().plusDays(1)).collect(Collectors.toList());

        List<TransactionDTO> transactions = account.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toList());
        List<TransactionDTO> transactionsFilt = transactions.stream().filter(e -> localDates.contains(e.getDate())).collect(Collectors.toList());
        return new ResponseEntity<>(transactionsFilt,HttpStatus.ACCEPTED);
    }

    }
