/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.controller.ViewModel;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class RepairPostViewModel {

    private String status;
    private LocalDate date;
    private PartViewModel partViewModel;
    private CompanyUserViewModel operator;
    private VehiclePostViewModel vehicleViewModel;
}