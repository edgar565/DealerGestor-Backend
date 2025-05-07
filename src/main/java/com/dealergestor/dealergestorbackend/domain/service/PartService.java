/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.model.Part;

import java.util.List;

public interface PartService {

    List<Part> findAll();
    Part findById(Long id);
    Part create(Part request);
    Part update(Long id, Part request);
    void delete(Long id);
}