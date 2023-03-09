package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    CardRepository cardRepository;

    @Override
    public Card findById(long id) {
        return this.cardRepository.findById(id);
    }

    @Override
    public Card findByNumber(String number) {
        return this.cardRepository.findByNumber(number);
    }

    @Override
    public void save(Card card) {
        this.cardRepository.save(card);
    }
}
