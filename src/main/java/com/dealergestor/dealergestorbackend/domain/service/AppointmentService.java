/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.model.AppointmentModel;

import java.util.List;

public interface AppointmentService {

    List<AppointmentModel> findAll();
    AppointmentModel findById(Long id);
    AppointmentModel create(AppointmentModel request);
    AppointmentModel update(Long id, AppointmentModel request);
    void delete(Long id);
}