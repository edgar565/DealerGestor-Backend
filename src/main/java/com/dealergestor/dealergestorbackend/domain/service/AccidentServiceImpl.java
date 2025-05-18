/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.*;
import com.dealergestor.dealergestorbackend.domain.model.Accident;
import com.dealergestor.dealergestorbackend.domain.repository.AccidentRepository;
import com.dealergestor.dealergestorbackend.domain.repository.CompanyUserRepository;
import com.dealergestor.dealergestorbackend.domain.repository.PartRepository;
import com.dealergestor.dealergestorbackend.domain.repository.VehicleRepository;
import com.dealergestor.dealergestorbackend.utils.ModelMapperUtil;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccidentServiceImpl implements AccidentService {

    private final AccidentRepository accidentRepository;
    private final VehicleRepository vehicleRepository;
    private final ModelMapperUtil modelMapperUtil;
    private final PartRepository partRepository;
    private final CompanyUserRepository companyUserRepository;

    public AccidentServiceImpl(AccidentRepository accidentRepository, VehicleRepository vehicleRepository, ModelMapperUtil modelMapperUtil, PartRepository partRepository, CompanyUserRepository companyUserRepository) {
        this.accidentRepository = accidentRepository;
        this.vehicleRepository = vehicleRepository;
        this.modelMapperUtil = modelMapperUtil;
        this.partRepository = partRepository;
        this.companyUserRepository = companyUserRepository;
    }

    @Override
    public List<Accident> findAllAccidents() {
        return accidentRepository.findAll().stream()
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Accident> findAllAccidentsActive() {
        return accidentRepository.findAll().stream()
                .filter(AccidentEntity::isActive) // solo activos
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Accident findAccidentById(Long id) {
        AccidentEntity accidentEntity = accidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accident not found"));
        return modelMapperUtil.toModel(accidentEntity);
    }

    @Override
    public Accident saveAccident(Accident model) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(model.getVehicle().getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        CompanyUserEntity companyUserEntity = companyUserRepository.findById(model.getOperator().getCompanyUserId())
                .orElseThrow(() -> new RuntimeException("Company user not found"));

        AccidentEntity accidentEntity = new AccidentEntity();
        accidentEntity.setStatus(RepairEntity.Status.valueOf(model.getStatus()));
        accidentEntity.setDate(model.getDate());
        accidentEntity.setActive(true);
        accidentEntity.setVehicle(vehicleEntity);
        accidentEntity.setOperator(companyUserEntity);
        accidentEntity.setInsuranceCompany(model.getInsuranceCompany());
        accidentEntity.setLocation(model.getLocation());

        return modelMapperUtil.toModel(accidentRepository.save(accidentEntity));
    }

    @Override
    public Accident updateAccident(Long id, Accident model) {
        AccidentEntity accidentEntity = accidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accident not found"));

        VehicleEntity vehicleEntity = vehicleRepository.findById(model.getVehicle().getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        PartEntity partEntity = partRepository.findById(model.getPart().getPartId())
                .orElseThrow(() -> new RuntimeException("Part not found"));

        CompanyUserEntity companyUserEntity = companyUserRepository.findById(model.getOperator().getCompanyUserId())
                .orElseThrow(() -> new RuntimeException("Company user not found"));

        accidentEntity.setStatus(RepairEntity.Status.valueOf(model.getStatus()));
        accidentEntity.setDate(model.getDate());
        accidentEntity.setVehicle(vehicleEntity);
        accidentEntity.setPartEntity(partEntity);
        accidentEntity.setOperator(companyUserEntity);
        accidentEntity.setInsuranceCompany(model.getInsuranceCompany());
        accidentEntity.setLocation(model.getLocation());

        return modelMapperUtil.toModel(accidentRepository.save(accidentEntity));
    }

    @Override
    public void deleteAccident(Long id) {
        AccidentEntity accidentEntity = accidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accident not found"));

        accidentEntity.setActive(false);
        accidentRepository.save(accidentEntity);
    }
}