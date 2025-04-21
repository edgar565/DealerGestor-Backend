/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.model.PartModel;

import java.util.List;

public interface PartService {

    List<PartModel> findAll();
    PartModel findById(Long id);
    PartModel create(PartModel request);
    PartModel update(Long id, PartModel request);
    void delete(Long id);
}