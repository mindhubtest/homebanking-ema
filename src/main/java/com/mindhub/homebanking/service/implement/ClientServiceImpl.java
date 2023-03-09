package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService{
    @Autowired
    ClientRepository clientRepository;




    @Override
    public Optional<Client> getClient(long id) {
     return this.clientRepository.findById(id);
    }

    @Override
    public List<Client> getClients() {
        return this.clientRepository.findAll();
    }

    @Override
    public Client findById(long id) {
        return this.clientRepository.findById(id).get();
    }

    @Override
    public Client findByEmail(String email) {
        return this.clientRepository.findByEmail(email);
    }

    @Override
    public Client findByCards(Card card) {
        return this.clientRepository.findByCards(card);
    }

    @Override
    public void save(Client client) {
        this.clientRepository.save(client);
    }


}
