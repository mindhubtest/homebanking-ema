package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.PaymentDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.CardService;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    CardService cardService;

    @Autowired
    ClientService clientService;

    @Transactional
    @PostMapping("/payment")
  public ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO){

        Card card = this.cardService.findByNumber(paymentDTO.getNumber());
        Client client = this.clientService.findByCards(card);


        Set<Account> accounts = client.getAccounts().stream().filter(e -> e.getBalance() >= paymentDTO.getAmount()).collect(Collectors.toSet());

            accounts = client.getAccounts().stream().findAny().stream().collect(Collectors.toSet());
            Account account = accounts.stream().findAny().get();






        if (paymentDTO.getAmount() == 0 || paymentDTO.getDescription().isEmpty() || paymentDTO.getCvv() == 0 || paymentDTO.getNumber().isEmpty()){
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }

        if(card.getCvv() != paymentDTO.getCvv()){
            return new ResponseEntity<>("Security code error", HttpStatus.FORBIDDEN);
        }

        if (!card.isStatus()){
            return new ResponseEntity<>("Card status error",HttpStatus.FORBIDDEN);
        }

        if (account.getBalance() < paymentDTO.getAmount()){
            return new ResponseEntity<>("Amount error", HttpStatus.FORBIDDEN);
        }

        transactionService.save(new Transaction(-paymentDTO.getAmount(), LocalDate.now(), TransactionType.Debito,paymentDTO.getDescription() + " " +account.getClient().getFirstName(),account.getBalance() - paymentDTO.getAmount(),account));
        account.setBalance(account.getBalance() - paymentDTO.getAmount());
        accountService.save(account);

        return new ResponseEntity<>("Ema", HttpStatus.CREATED);
    }
}
