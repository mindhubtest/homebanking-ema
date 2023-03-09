package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.service.CardService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.ultilties.CardUtils.getRandomNumber;


@RestController
@RequestMapping("/api")

public class CardController {
    @Autowired
    CardService cardService;

    @Autowired
    ClientService clientService;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardColor cardColor , @RequestParam CardType cardType, Authentication authentication ) {

        Client client = this.clientService.findByEmail(authentication.getName());

        long type = client.getCards().stream().filter(e -> e.getType().toString().equals(cardType.toString())).filter(Card::isStatus).count();

        if(type == 3){


            return new ResponseEntity<>("Already have 3 Cards", HttpStatus.FORBIDDEN);
        }

        cardService.save(new Card( cardColor, "2000"+ "-" + getRandomNumber(1000, 9999) + "-"+  getRandomNumber(1000, 9999)+ "-" + getRandomNumber(1000, 9999), getRandomNumber(100, 999), LocalDate.now(),LocalDate.now().plusYears(5),client.getFirstName() + " " + client.getLastName(), cardType,client,true));

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @PutMapping("/clients/current/cards")
    public ResponseEntity<?> DeleteCard(Authentication authentication, @RequestParam long id) {

    Card card = this.cardService.findById(id);
     card.setStatus(false);
    cardService.save(card);

    return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
