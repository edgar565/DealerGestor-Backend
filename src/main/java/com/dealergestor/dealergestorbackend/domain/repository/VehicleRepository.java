/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.repository;

import com.dealergestor.dealergestorbackend.domain.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    boolean existsByLicensePlate(String licensePlate);

    @Query("SELECT m FROM VehicleEntity m WHERE m.licensePlate = ?1")
    VehicleEntity findVehicleByLicensePlate(String licensePlate);
}