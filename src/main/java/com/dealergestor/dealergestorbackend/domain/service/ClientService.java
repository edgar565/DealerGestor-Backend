/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.model.ClientModel;

import java.util.List;

public interface ClientService {

    List<ClientModel> findAll();
    ClientModel findById(Long id);
    ClientModel create(ClientModel request);
    ClientModel update(Long id, ClientModel request);
    void delete(Long id);
}