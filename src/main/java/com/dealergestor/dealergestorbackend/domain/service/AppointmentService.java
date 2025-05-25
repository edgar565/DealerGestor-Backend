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

    List<Appointment> findAllAppointments();
    List<Appointment> findNowAppointments();
    Appointment findAppointmentById(Long id);
    Appointment saveAppointment(Appointment request);
    Appointment updateAppointment(Long id, Appointment request);
    void deleteAppointment(Long id);
    List<Appointment> findAppointmentByLicensePlate(String licensePlate);
    List<Appointment> findAppointmentByClientName(String name);
}