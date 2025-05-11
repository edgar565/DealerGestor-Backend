/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.model.Vehicle;

import java.util.List;

public interface VehicleService {

    List<Vehicle> findAllVehicles();
    Vehicle findVehicleById(Long id);
    Vehicle saveVehicle(Vehicle request);
    Vehicle updateVehicle(Long id, Vehicle request);
    void deleteVehicle(Long id);
    Vehicle findVehicleByLicensePlate(String licensePlate);
}