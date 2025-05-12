package com.dealergestor.dealergestorbackend.controller.ViewModel;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyConfigurationPostViewModel {

    private String companyName;
    private String primaryColor;
    private String secondaryColor;
    private String whatsappApiKey;
}