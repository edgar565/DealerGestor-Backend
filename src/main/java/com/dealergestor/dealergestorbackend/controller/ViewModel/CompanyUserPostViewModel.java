/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.controller.ViewModel;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class CompanyUserPostViewModel {

    private Long companyUserId;
    private String username;
    private String password;
    private String role;
    private List<NoteViewModel> notes;
}