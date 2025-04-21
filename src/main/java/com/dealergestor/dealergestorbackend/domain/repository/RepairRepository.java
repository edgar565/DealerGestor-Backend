/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.repository;

import com.dealergestor.dealergestorbackend.domain.entity.Repair;
import com.dealergestor.dealergestorbackend.domain.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {

    boolean existsByVehicle(Vehicle vehicle);
}