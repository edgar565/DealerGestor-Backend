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

public class PartPostViewModel {

    private String keychain;
    private String numberOrder;
    private String work;
    private String materials;
    private Long repairId;
}