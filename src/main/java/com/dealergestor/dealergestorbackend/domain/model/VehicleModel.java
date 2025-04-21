/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class VehicleModel {

    private Long vehicleId;
    private String licensePlate;
    private String brand;
    private String model;
    private RepairModel repairModel;
    private AppointmentModel appointmentModel;
}