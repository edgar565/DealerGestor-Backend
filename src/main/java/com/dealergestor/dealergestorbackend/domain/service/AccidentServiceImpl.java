/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.AccidentEntity;
import com.dealergestor.dealergestorbackend.domain.entity.VehicleEntity;
import com.dealergestor.dealergestorbackend.domain.model.Accident;
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
    public List<Accident> findAll() {
        return accidentRepository.findAll().stream()
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Accident findById(Long id) {
        AccidentEntity accidentEntity = accidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accident not found"));
        return modelMapperUtil.toModel(accidentEntity);
    }

    @Override
    public Accident create(Accident model) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(model.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        AccidentEntity accidentEntity = new AccidentEntity();
        accidentEntity.setStartDate(LocalDateTime.parse(model.getStartDate()));
        accidentEntity.setEndDate(LocalDateTime.parse(model.getEndDate()));
        accidentEntity.setDescription(model.getDescription());
        accidentEntity.setStatus(model.getStatus());
        accidentEntity.setVehicleEntity(vehicleEntity);
        accidentEntity.setPoliceReportNumber(model.getPoliceReportNumber());
        accidentEntity.setAtFault(model.isAtFault());

        return modelMapperUtil.toModel(accidentRepository.save(accidentEntity));
    }

    @Override
    public Accident update(Long id, Accident model) {
        AccidentEntity accidentEntity = accidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accident not found"));

        VehicleEntity vehicleEntity = vehicleRepository.findById(model.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        accidentEntity.setDescription(model.getDescription());
        accidentEntity.setStatus(model.getStatus());
        accidentEntity.setVehicleEntity(vehicleEntity);
        accidentEntity.setPoliceReportNumber(model.getPoliceReportNumber());
        accidentEntity.setAtFault(model.isAtFault());

        return modelMapperUtil.toModel(accidentRepository.save(accidentEntity));
    }

    @Override
    public void delete(Long id) {
        accidentRepository.deleteById(id);
    }
}