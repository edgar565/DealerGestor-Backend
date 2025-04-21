/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.model.AccidentModel;

import java.util.List;

public interface AccidentService {

    List<AccidentModel> findAll();
    AccidentModel findById(Long id);
    AccidentModel create(AccidentModel request);
    AccidentModel update(Long id, AccidentModel request);
    void delete(Long id);
}