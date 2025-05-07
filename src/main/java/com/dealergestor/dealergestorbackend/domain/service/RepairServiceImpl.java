/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.*;
import com.dealergestor.dealergestorbackend.domain.model.Repair;
import com.dealergestor.dealergestorbackend.domain.repository.AppointmentRepository;
import com.dealergestor.dealergestorbackend.domain.repository.CompanyUserRepository;
import com.dealergestor.dealergestorbackend.domain.repository.RepairRepository;
import com.dealergestor.dealergestorbackend.domain.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepairServiceImpl implements RepairService{

    private final RepairRepository repairRepository;
    private final VehicleRepository vehicleRepository;
    private final AppointmentRepository appointmentRepository;
    private final CompanyUserRepository companyUserRepository;
    private final ModelMapperUtil modelMapperUtil;

    public RepairServiceImpl(RepairRepository repairRepository, VehicleRepository vehicleRepository, AppointmentRepository appointmentRepository, CompanyUserRepository companyUserRepository, ModelMapperUtil modelMapperUtil) {
        this.repairRepository = repairRepository;
        this.vehicleRepository = vehicleRepository;
        this.appointmentRepository = appointmentRepository;
        this.companyUserRepository = companyUserRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    @Override
    public List<Repair> findAll() {
        List<RepairEntity> all = repairRepository.findAll();
        return all.stream()
                .filter(repair -> !(repair instanceof AccidentEntity))
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Repair findById(Long id) {
        RepairEntity repairEntity = repairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repair not found"));
        return modelMapperUtil.toModel(repairEntity);
    }

    @Override
    public Repair create(Repair model) {
        RepairEntity repairEntity = repairRepository.findById(model.getRepairId())
                .orElseThrow(() -> new RuntimeException("Repair not found"));

        VehicleEntity vehicleEntity = vehicleRepository.findById(repairEntity.getVehicleEntity().getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        boolean hasRepair = repairRepository.existsByVehicle(vehicleEntity);
        boolean hasAppointment = appointmentRepository.existsByVehicle(vehicleEntity);

        if (hasRepair || hasAppointment) {
            throw new RuntimeException("This vehicle already has an active appointment or repair.");
        }

        CompanyUserEntity operator = companyUserRepository.findById(model.getCompanyUser().getCompanyUserId())
                .orElseThrow(() -> new RuntimeException("Operator not found"));

        if (!operator.getRole().contains(CompanyUserEntity.Role.MECHANIC)) {
            throw new RuntimeException("Operator must have MECHANIC role");
        }


        RepairEntity repair = new RepairEntity();
        repair.setOperator(model.getOperator());
        repair.setStatus(RepairEntity.Status.valueOf(model.getStatus().toUpperCase()));
        repair.setDate(LocalDate.parse(model.getDate()));
        repair.setVehicleEntity(vehicleEntity);

        if (model.getPart() != null) {
            PartEntity partEntity = new PartEntity();
            partEntity.setKeychain(model.getPart().getKeychain());
            partEntity.setNumberOrder(model.getPart().getNumberOrder());
            partEntity.setWork(model.getPart().getWork());
            partEntity.setMaterials(model.getPart().getMaterials());
            partEntity.setObservations(model.getPart().getObservations());
            partEntity.setLights(model.getPart().getLights());
            partEntity.setBrakeSensors(model.getPart().getBrakeSensors());
            partEntity.setCableTension(model.getPart().getCableTension());
            partEntity.setTirePressure(model.getPart().getTirePressure());
            partEntity.setEngineOil(model.getPart().getEngineOil());
            partEntity.setWear(model.getPart().getWear());
            partEntity.setBrakeFluid(model.getPart().getBrakeFluid());
            partEntity.setBrakePads(model.getPart().getBrakePads());
            partEntity.setTransmissionKit(model.getPart().getTransmissionKit());
            partEntity.setSteeringCondition(model.getPart().getSteeringCondition());
            partEntity.setDynamicTest(model.getPart().getDynamicTest());

            partEntity.setDay1(model.getPart().getDay1());
            partEntity.setDay2(model.getPart().getDay2());
            partEntity.setDay3(model.getPart().getDay3());
            partEntity.setDay4(model.getPart().getDay4());
            partEntity.setDay5(model.getPart().getDay5());
            partEntity.setDay6(model.getPart().getDay6());

            partEntity.setTime1(model.getPart().getTime1());
            partEntity.setTime2(model.getPart().getTime2());
            partEntity.setTime3(model.getPart().getTime3());
            partEntity.setTime4(model.getPart().getTime4());
            partEntity.setTime5(model.getPart().getTime5());
            partEntity.setTime6(model.getPart().getTime6());
            partEntity.setTime7(model.getPart().getTime7());
            partEntity.setTime8(model.getPart().getTime8());
            partEntity.setTime9(model.getPart().getTime9());
            partEntity.setTime10(model.getPart().getTime10());
            partEntity.setTime11(model.getPart().getTime11());
            partEntity.setTime12(model.getPart().getTime12());
            partEntity.setRepairEntity(repair); // Vincular la pieza con la reparación
            repair.setPartEntity(partEntity);   // Vincular la reparación con la pieza (por si acaso, bidireccional)
        }

        return modelMapperUtil.toModel(repairRepository.save(repair));
    }

    @Override
    public Repair update(Long id, Repair model) {
        RepairEntity repairEntity = repairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repair not found"));

        VehicleEntity vehicleEntity = vehicleRepository.findById(repairEntity.getVehicleEntity().getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        repairEntity.setOperator(model.getOperator());
        repairEntity.setStatus(RepairEntity.Status.valueOf(model.getStatus().toUpperCase()));
        repairEntity.setDate(LocalDate.parse(model.getDate()));
        repairEntity.setVehicleEntity(vehicleEntity);

        return modelMapperUtil.toModel(repairRepository.save(repairEntity));
    }

    @Override
    public void delete(Long id) {
        repairRepository.deleteById(id);
    }
}