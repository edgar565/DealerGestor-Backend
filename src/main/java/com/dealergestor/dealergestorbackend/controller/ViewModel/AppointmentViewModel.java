/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.controller.ViewModel;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class AppointmentViewModel {

    private Long appointmentId;
    private String dateTime;
    private String task;
    private String nameClient;
    private String licensePlate;
}