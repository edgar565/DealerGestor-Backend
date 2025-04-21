/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.model.VehicleModel;

import java.util.List;

public interface VehicleService {

    List<VehicleModel> findAll();
    VehicleModel findById(Long id);
    VehicleModel create(VehicleModel request);
    VehicleModel update(Long id, VehicleModel request);
    void delete(Long id);
}