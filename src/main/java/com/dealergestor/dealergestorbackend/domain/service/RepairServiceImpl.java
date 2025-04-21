/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.*;
import com.dealergestor.dealergestorbackend.domain.model.RepairModel;
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
    public List<RepairModel> findAll() {
        List<Repair> all = repairRepository.findAll();
        return all.stream()
                .filter(repair -> !(repair instanceof Accident))
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public RepairModel findById(Long id) {
        Repair repair = repairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repair not found"));
        return modelMapperUtil.toModel(repair);
    }

    @Override
    public RepairModel create(RepairModel model) {
        Repair repairEntity = repairRepository.findById(model.getRepairId())
                .orElseThrow(() -> new RuntimeException("Repair not found"));

        Vehicle vehicle = vehicleRepository.findById(repairEntity.getVehicle().getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        boolean hasRepair = repairRepository.existsByVehicle(vehicle);
        boolean hasAppointment = appointmentRepository.existsByVehicle(vehicle);

        if (hasRepair || hasAppointment) {
            throw new RuntimeException("This vehicle already has an active appointment or repair.");
        }

        CompanyUser operator = companyUserRepository.findById(model.getCompanyUserModel().getCompanyUserId())
                .orElseThrow(() -> new RuntimeException("Operator not found"));

        if (!operator.getRole().contains(CompanyUser.Role.MECHANIC)) {
            throw new RuntimeException("Operator must have MECHANIC role");
        }


        Repair repair = new Repair();
        repair.setOperator(model.getOperator());
        repair.setStatus(Repair.Status.valueOf(model.getStatus().toUpperCase()));
        repair.setDate(LocalDate.parse(model.getDate()));
        repair.setVehicle(vehicle);

        if (model.getPartModel() != null) {
            Part part = new Part();
            part.setKeychain(model.getPartModel().getKeychain());
            part.setNumberOrder(model.getPartModel().getNumberOrder());
            part.setWork(model.getPartModel().getWork());
            part.setMaterials(model.getPartModel().getMaterials());
            part.setObservations(model.getPartModel().getObservations());
            part.setLights(model.getPartModel().getLights());
            part.setBrakeSensors(model.getPartModel().getBrakeSensors());
            part.setCableTension(model.getPartModel().getCableTension());
            part.setTirePressure(model.getPartModel().getTirePressure());
            part.setEngineOil(model.getPartModel().getEngineOil());
            part.setWear(model.getPartModel().getWear());
            part.setBrakeFluid(model.getPartModel().getBrakeFluid());
            part.setBrakePads(model.getPartModel().getBrakePads());
            part.setTransmissionKit(model.getPartModel().getTransmissionKit());
            part.setSteeringCondition(model.getPartModel().getSteeringCondition());
            part.setDynamicTest(model.getPartModel().getDynamicTest());

            part.setDay1(model.getPartModel().getDay1());
            part.setDay2(model.getPartModel().getDay2());
            part.setDay3(model.getPartModel().getDay3());
            part.setDay4(model.getPartModel().getDay4());
            part.setDay5(model.getPartModel().getDay5());
            part.setDay6(model.getPartModel().getDay6());

            part.setTime1(model.getPartModel().getTime1());
            part.setTime2(model.getPartModel().getTime2());
            part.setTime3(model.getPartModel().getTime3());
            part.setTime4(model.getPartModel().getTime4());
            part.setTime5(model.getPartModel().getTime5());
            part.setTime6(model.getPartModel().getTime6());
            part.setTime7(model.getPartModel().getTime7());
            part.setTime8(model.getPartModel().getTime8());
            part.setTime9(model.getPartModel().getTime9());
            part.setTime10(model.getPartModel().getTime10());
            part.setTime11(model.getPartModel().getTime11());
            part.setTime12(model.getPartModel().getTime12());
            part.setRepair(repair); // Vincular la pieza con la reparación
            repair.setPart(part);   // Vincular la reparación con la pieza (por si acaso, bidireccional)
        }

        return modelMapperUtil.toModel(repairRepository.save(repair));
    }

    @Override
    public RepairModel update(Long id, RepairModel model) {
        Repair repair = repairRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repair not found"));

        Vehicle vehicle = vehicleRepository.findById(repair.getVehicle().getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        repair.setOperator(model.getOperator());
        repair.setStatus(Repair.Status.valueOf(model.getStatus().toUpperCase()));
        repair.setDate(LocalDate.parse(model.getDate()));
        repair.setVehicle(vehicle);

        return modelMapperUtil.toModel(repairRepository.save(repair));
    }

    @Override
    public void delete(Long id) {
        repairRepository.deleteById(id);
    }
}