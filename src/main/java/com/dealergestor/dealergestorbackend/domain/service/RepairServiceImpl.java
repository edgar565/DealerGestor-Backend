/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.*;
import com.dealergestor.dealergestorbackend.domain.model.Repair;
import com.dealergestor.dealergestorbackend.domain.repository.*;
import com.dealergestor.dealergestorbackend.utils.ModelMapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepairServiceImpl implements RepairService{

    private final RepairRepository repairRepository;
    private final VehicleRepository vehicleRepository;
    private final AppointmentRepository appointmentRepository;
    private final CompanyConfigurationUserRepository companyConfigurationUserRepository;
    private final ModelMapperUtil modelMapperUtil;
    private final PartRepository partRepository;

    public RepairServiceImpl(RepairRepository repairRepository, VehicleRepository vehicleRepository, AppointmentRepository appointmentRepository, CompanyConfigurationUserRepository companyConfigurationUserRepository, ModelMapperUtil modelMapperUtil, PartRepository partRepository) {
        this.repairRepository = repairRepository;
        this.vehicleRepository = vehicleRepository;
        this.appointmentRepository = appointmentRepository;
        this.companyConfigurationUserRepository = companyConfigurationUserRepository;
        this.modelMapperUtil = modelMapperUtil;
        this.partRepository = partRepository;
    }

    @Override
    public List<Repair> findAllRepairs() {
        List<RepairEntity> all = repairRepository.findAll();
        return all.stream()
                .filter(repair -> !(repair instanceof AccidentEntity))
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Repair> findAllRepairsActive() {
        return repairRepository.findAll().stream()
                .filter(RepairEntity::isActive) // solo activos
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Repair findRepairById(Long id) {
        RepairEntity repairEntity = repairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repair not found"));
        return modelMapperUtil.toModel(repairEntity);
    }

    @Override
    public Repair saveRepair(Repair model) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(model.getVehicle().getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        boolean hasRepair = repairRepository.existsByVehicle(vehicleEntity);
        boolean hasAppointment = appointmentRepository.existsByVehicle(vehicleEntity);

        if (hasRepair || hasAppointment) {
            throw new RuntimeException("This vehicle already has an active appointment or repair.");
        }

        CompanyUserEntity operator = companyConfigurationUserRepository.findById(model.getOperator().getCompanyUserId())
                .orElseThrow(() -> new RuntimeException("Operator not found"));

        RepairEntity repairEntity = new RepairEntity();
        repairEntity.setStatus(RepairEntity.Status.valueOf(model.getStatus().toUpperCase()));
        repairEntity.setDate(model.getDate());
        repairEntity.setActive(true);
        repairEntity.setVehicle(vehicleEntity);
        repairEntity.setOperator(operator);
        return modelMapperUtil.toModel(repairRepository.save(repairEntity));
    }

    @Override
    public Repair updateRepair(Long id, Repair model) {
        RepairEntity repairEntity = repairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repair not found"));

        VehicleEntity vehicleEntity = vehicleRepository.findById(model.getVehicle().getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        CompanyUserEntity operator = companyConfigurationUserRepository.findById(model.getOperator().getCompanyUserId())
                .orElseThrow(() -> new RuntimeException("Operator not found"));

        PartEntity partEntity = partRepository.findById(model.getPart().getPartId())
                .orElseThrow(() -> new RuntimeException("Part not found"));

        repairEntity.setStatus(RepairEntity.Status.valueOf(model.getStatus().toUpperCase()));
        repairEntity.setDate(model.getDate());
        repairEntity.setVehicle(vehicleEntity);
        repairEntity.setPartEntity(partEntity);
        repairEntity.setOperator(operator);

        return modelMapperUtil.toModel(repairRepository.save(repairEntity));
    }

    @Override
    public void deleteRepair(Long id) {
        RepairEntity repairEntity = repairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repair not found"));

        repairEntity.setActive(false);
        repairRepository.save(repairEntity);
    }
}