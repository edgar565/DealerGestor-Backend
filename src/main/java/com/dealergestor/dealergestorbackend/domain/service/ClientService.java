/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> findAllClients();
    Client findClientById(Long id);
    Client saveClient(Client request);
    Client updateClient(Long id, Client request);
    void deleteClient(Long id);
    Client findClientByName(String name);
}