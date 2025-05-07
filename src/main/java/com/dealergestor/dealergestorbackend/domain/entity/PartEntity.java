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
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "part")
public class PartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id")
    private Long partId;

    @Column(name = "keychain")
    private String keychain;

    @Column(name = "number_order")
    private String numberOrder;

    @Column(name = "work", columnDefinition = "TEXT")
    private String work;

    @Column(name = "materials", columnDefinition = "TEXT")
    private String materials;

    @Column(name = "observations", columnDefinition = "TEXT")
    private String observations;

    @Column(name = "lights")
    private Boolean lights;

    @Column(name = "brake_sensors")
    private Boolean brakeSensors;

    @Column(name = "cable_tension")
    private Boolean cableTension;

    @Column(name = "tire_pressure")
    private Boolean tirePressure;

    @Column(name = "engine_oil")
    private Boolean engineOil;

    @Column(name = "wear")
    private Boolean wear;

    @Column(name = "brake_fluid")
    private Boolean brakeFluid;

    @Column(name = "brake_pads")
    private Boolean brakePads;

    @Column(name = "transmission_kit")
    private Boolean transmissionKit;

    @Column(name = "steering_condition")
    private Boolean steeringCondition;

    @Column(name = "dynamic_test")
    private Boolean dynamicTest;

    @Column(name = "day1")
    private LocalDate day1;
    @Column(name = "day2")
    private LocalDate day2;
    @Column(name = "day3")
    private LocalDate day3;
    @Column(name = "day4")
    private LocalDate day4;
    @Column(name = "day5")
    private LocalDate day5;
    @Column(name = "day6")
    private LocalDate day6;

    @Column(name = "time1")
    private LocalTime time1;
    @Column(name = "time2")
    private LocalTime time2;
    @Column(name = "time3")
    private LocalTime time3;
    @Column(name = "time4")
    private LocalTime time4;
    @Column(name = "time5")
    private LocalTime time5;
    @Column(name = "time6")
    private LocalTime time6;
    @Column(name = "time7")
    private LocalTime time7;
    @Column(name = "time8")
    private LocalTime time8;
    @Column(name = "time9")
    private LocalTime time9;
    @Column(name = "time10")
    private LocalTime time10;
    @Column(name = "time11")
    private LocalTime time11;
    @Column(name = "time12")
    private LocalTime time12;

    @OneToOne
    @JoinColumn(name = "repair_id")
    private RepairEntity repairEntity;
}