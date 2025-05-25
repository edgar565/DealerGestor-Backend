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

public class RepairViewModel {

    private Long repairId;
    private String status;
    private String date;
    private Long partId;
    private String operator;
    private VehicleViewModel vehicleViewModel;
}