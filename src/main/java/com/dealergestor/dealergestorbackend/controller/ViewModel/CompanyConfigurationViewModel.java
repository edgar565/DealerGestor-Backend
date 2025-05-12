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

public class CompanyConfigurationViewModel {

    private String nameCompany;
    private String primaryColor;
    private String secondaryColor;
    private String logoPath;
    private String whatsappApiKey;
}