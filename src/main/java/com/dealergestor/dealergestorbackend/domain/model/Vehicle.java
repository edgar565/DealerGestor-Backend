/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Vehicle {

    private Long vehicleId;
    private String licensePlate;
    private String brand;
    private String model;
    private List<Repair> repairs;
    private Appointment appointment;
    private Client client;
}