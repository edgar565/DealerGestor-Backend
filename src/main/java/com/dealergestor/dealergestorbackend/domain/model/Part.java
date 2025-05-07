/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Part {

    private Long partId;
    private String keychain;
    private String numberOrder;
    private String work;
    private String materials;
    private String observations;
    private Boolean lights;
    private Boolean brakeSensors;
    private Boolean cableTension;
    private Boolean tirePressure;
    private Boolean engineOil;
    private Boolean wear;
    private Boolean brakeFluid;
    private Boolean brakePads;
    private Boolean transmissionKit;
    private Boolean steeringCondition;
    private Boolean dynamicTest;
    private LocalDate day1;
    private LocalDate day2;
    private LocalDate day3;
    private LocalDate day4;
    private LocalDate day5;
    private LocalDate day6;
    private LocalTime time1;
    private LocalTime time2;
    private LocalTime time3;
    private LocalTime time4;
    private LocalTime time5;
    private LocalTime time6;
    private LocalTime time7;
    private LocalTime time8;
    private LocalTime time9;
    private LocalTime time10;
    private LocalTime time11;
    private LocalTime time12;
}