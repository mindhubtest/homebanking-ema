package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Optional<Client> getClient(long id);
    List<Client> getClients();
    Client findById(long id);
    Client findByEmail(String email);
    Client findByCards(Card card);
    void save(Client client);
}
