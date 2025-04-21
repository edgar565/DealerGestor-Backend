/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.Vehicle;
import com.dealergestor.dealergestorbackend.domain.model.VehicleModel;
import com.dealergestor.dealergestorbackend.domain.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService{

    private final VehicleRepository vehicleRepository;
    private final ModelMapperUtil modelMapperUtil;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, ModelMapperUtil modelMapperUtil) {
        this.vehicleRepository = vehicleRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    @Override
    public List<VehicleModel> findAll() {
        return vehicleRepository.findAll().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleModel findById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        return modelMapperUtil.toModel(vehicle);
    }

    @Override
    public VehicleModel create(VehicleModel model) {
        if (vehicleRepository.existsByLicensePlate(model.getLicensePlate())) {
            throw new RuntimeException("License plate already exists");
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(model.getLicensePlate());
        vehicle.setBrand(model.getBrand());
        vehicle.setModel(model.getModel());
        return modelMapperUtil.toModel(vehicleRepository.save(vehicle));
    }

    @Override
    public VehicleModel update(Long id, VehicleModel model) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        vehicle.setLicensePlate(model.getLicensePlate());
        vehicle.setBrand(model.getBrand());
        vehicle.setModel(model.getModel());
        return modelMapperUtil.toModel(vehicleRepository.save(vehicle));
    }

    @Override
    public void delete(Long id) {
        vehicleRepository.deleteById(id);
    }
}