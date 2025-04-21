/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.model.RepairModel;

import java.util.List;

public interface RepairService {

    List<RepairModel> findAll();
    RepairModel findById(Long id);
    RepairModel create(RepairModel request);
    RepairModel update(Long id, RepairModel request);
    void delete(Long id);
}