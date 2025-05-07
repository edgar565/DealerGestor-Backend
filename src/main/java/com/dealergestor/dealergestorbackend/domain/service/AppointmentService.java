/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.model.Appointment;

import java.util.List;

public interface AppointmentService {

    List<Appointment> findAll();
    Appointment findById(Long id);
    Appointment create(Appointment request);
    Appointment update(Long id, Appointment request);
    void delete(Long id);
}