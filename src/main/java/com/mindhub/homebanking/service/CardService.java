package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;

public interface CardService {

    Card findById(long id);
    Card findByNumber(String number);
    void save(Card card);
}
