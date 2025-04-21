/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "repair")
public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repair_id")
    private Long repairId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public enum Status {
        EN_REPARACIÓN,
        PEDIDO_RECAMBIO,
        FINALIZADA_CONTACTO,
        FINALIZADA,
        CIERRE
    }

    @Column(name = "date")
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @OneToOne(mappedBy = "repair", cascade = CascadeType.ALL, orphanRemoval = true)
    private Part part;

    @OneToOne
    @JoinColumn(name = "operator_id")
    private CompanyUser operator;
}