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

public class VehiclePostViewModel {

    private String licensePlate;
    private String brand;
    private String model;
    private ClientPostViewModel clientViewModel;
}