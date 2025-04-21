/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.Accident;
import com.dealergestor.dealergestorbackend.domain.entity.Vehicle;
import com.dealergestor.dealergestorbackend.domain.model.AccidentModel;
import com.dealergestor.dealergestorbackend.domain.repository.AccidentRepository;
import com.dealergestor.dealergestorbackend.domain.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccidentServiceImpl implements AccidentService {

    private final AccidentRepository accidentRepository;
    private final VehicleRepository vehicleRepository;
    private final ModelMapperUtil modelMapperUtil;

    public AccidentServiceImpl(AccidentRepository accidentRepository, VehicleRepository vehicleRepository, ModelMapperUtil modelMapperUtil) {
        this.accidentRepository = accidentRepository;
        this.vehicleRepository = vehicleRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    @Override
    public List<AccidentModel> findAll() {
        return accidentRepository.findAll().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public AccidentModel findById(Long id) {
        Accident accident = accidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accident not found"));
        return modelMapperUtil.toModel(accident);
    }

    @Override
    public AccidentModel create(AccidentModel model) {
        Vehicle vehicle = vehicleRepository.findById(model.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        Accident accident = new Accident();
        accident.setStartDate(LocalDateTime.parse(model.getStartDate()));
        accident.setEndDate(LocalDateTime.parse(model.getEndDate()));
        accident.setDescription(model.getDescription());
        accident.setStatus(model.getStatus());
        accident.setVehicle(vehicle);
        accident.setPoliceReportNumber(model.getPoliceReportNumber());
        accident.setAtFault(model.isAtFault());

        return modelMapperUtil.toModel(accidentRepository.save(accident));
    }

    @Override
    public AccidentModel update(Long id, AccidentModel model) {
        Accident accident = accidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accident not found"));

        Vehicle vehicle = vehicleRepository.findById(model.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        accident.setStartDate(LocalDateTime.parse(model.getStartDate()));
        accident.setEndDate(LocalDateTime.parse(model.getEndDate()));
        accident.setDescription(model.getDescription());
        accident.setStatus(model.getStatus());
        accident.setVehicle(vehicle);
        accident.setPoliceReportNumber(model.getPoliceReportNumber());
        accident.setAtFault(model.isAtFault());

        return modelMapperUtil.toModel(accidentRepository.save(accident));
    }

    @Override
    public void delete(Long id) {
        accidentRepository.deleteById(id);
    }
}