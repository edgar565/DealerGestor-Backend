/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.auth.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {

    private String username;
    private String password;
}