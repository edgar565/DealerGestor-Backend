/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.repository;

import com.dealergestor.dealergestorbackend.domain.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dealergestor.dealergestorbackend.domain.entity.Vehicle;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByVehicle(Vehicle vehicle);
}