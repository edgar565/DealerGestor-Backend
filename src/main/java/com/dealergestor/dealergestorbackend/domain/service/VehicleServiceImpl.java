/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.VehicleEntity;
import com.dealergestor.dealergestorbackend.domain.model.Vehicle;
import com.dealergestor.dealergestorbackend.domain.repository.VehicleRepository;
import com.dealergestor.dealergestorbackend.utils.EntityMapperUtil;
import com.dealergestor.dealergestorbackend.utils.ModelMapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService{

    private final VehicleRepository vehicleRepository;
    private final ModelMapperUtil modelMapperUtil;
    private final EntityMapperUtil entityMapperUtil;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, ModelMapperUtil modelMapperUtil, EntityMapperUtil entityMapperUtil) {
        this.vehicleRepository = vehicleRepository;
        this.modelMapperUtil = modelMapperUtil;
        this.entityMapperUtil = entityMapperUtil;
    }

    @Override
    public List<Vehicle> findAllVehiclesByClientId(Long id) {
        return vehicleRepository.findAll().stream()
                .filter(vehicleEntity -> vehicleEntity.getClient().getClientId().equals(id))
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Vehicle findVehicleById(Long id) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        return modelMapperUtil.toModel(vehicleEntity);
    }

    @Override
    public Vehicle saveVehicle(Vehicle model) {
        if (vehicleRepository.existsByLicensePlate(model.getLicensePlate())) {
            throw new RuntimeException("License plate already exists");
        }
        return modelMapperUtil.toModel(vehicleRepository.save(entityMapperUtil.toEntity(model)));
    }

    @Override
    public Vehicle updateVehicle(Long id, Vehicle model) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        vehicleEntity.setLicensePlate(model.getLicensePlate());
        vehicleEntity.setBrand(model.getBrand());
        vehicleEntity.setModel(model.getModel());
        return modelMapperUtil.toModel(vehicleRepository.save(vehicleEntity));
    }

    @Override
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public Vehicle findVehicleByLicensePlate(String licensePlate) {
        return modelMapperUtil.toModel(vehicleRepository.findVehicleByLicensePlate(licensePlate));
    }
}