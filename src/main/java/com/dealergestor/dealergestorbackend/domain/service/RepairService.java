/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.model.Repair;

import java.util.List;

public interface RepairService {

    List<Repair> findAll();
    Repair findById(Long id);
    Repair create(Repair request);
    Repair update(Long id, Repair request);
    void delete(Long id);
}