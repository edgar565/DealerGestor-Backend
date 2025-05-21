/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Repair {

    private Long repairId;
    private String status;
    private LocalDate date;
    private List<Part> part;
    private CompanyUser operator;
    private Vehicle vehicle;
    private boolean isActive;
}