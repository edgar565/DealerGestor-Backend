/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@DiscriminatorValue("ACCIDENT")
public class AccidentEntity extends RepairEntity {

    @Column(name = "insurance_company")
    private String insuranceCompany;

    @Column(name = "location")
    private String location;
}