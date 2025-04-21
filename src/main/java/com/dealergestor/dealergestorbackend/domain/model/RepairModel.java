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

public class RepairModel {

    private Long repairId;
    private String status;
    private String date;
    private PartModel partModel;
    private CompanyUserModel companyUserModel;
}