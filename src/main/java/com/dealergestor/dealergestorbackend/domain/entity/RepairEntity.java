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
public class RepairEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repair_id")
    private Long repairId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public enum Status {
        EN_REPARACION {
            @Override
            public String toString() {
                return "En reparación";
            }
        },
        PEDIDO_RECAMBIO {
            @Override
            public String toString() {
                return "Pedido recambio";
            }
        },
        FINALIZADA_CONTACTO {
            @Override
            public String toString() {
                return "Finalizada con contacto";
            }
        },
        FINALIZADA {
            @Override
            public String toString() {
                return "Finalizada";
            }
        },
        CIERRE {
            @Override
            public String toString() {
                return "Cierre";
            }
        }
    }

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "isActive")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle;

    @OneToOne(mappedBy = "repairEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private PartEntity partEntity;

    @OneToOne
    @JoinColumn(name = "operator_id")
    private CompanyUserEntity operator;
}