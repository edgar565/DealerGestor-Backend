/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.controller.ViewModel;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class VehicleViewModel {

    private Long vehicleId;
    private String licensePlate;
    private String brand;
    private String model;
}