/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.Appointment;
import com.dealergestor.dealergestorbackend.domain.entity.Vehicle;
import com.dealergestor.dealergestorbackend.domain.model.AppointmentModel;
import com.dealergestor.dealergestorbackend.domain.repository.AppointmentRepository;
import com.dealergestor.dealergestorbackend.domain.repository.RepairRepository;
import com.dealergestor.dealergestorbackend.domain.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final VehicleRepository vehicleRepository;
    private final RepairRepository repairRepository;
    private final ModelMapperUtil modelMapperUtil;

    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            VehicleRepository vehicleRepository,
            RepairRepository repairRepository,
            ModelMapperUtil modelMapperUtil

    ) {
        this.appointmentRepository = appointmentRepository;
        this.vehicleRepository = vehicleRepository;
        this.repairRepository = repairRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    @Override
    public List<AppointmentModel> findAll() {
        return appointmentRepository.findAll().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentModel findById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        return modelMapperUtil.toModel(appointment);
    }

    @Override
    public AppointmentModel create(AppointmentModel model) {
        Appointment appointmentEntity = appointmentRepository.findById(model.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Vehicle vehicle = vehicleRepository.findById(appointmentEntity.getVehicle().getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        boolean hasAppointment = appointmentRepository.existsByVehicle(vehicle);
        boolean hasRepair = repairRepository.existsByVehicle(vehicle);

        if (hasAppointment || hasRepair) {
            throw new RuntimeException("This vehicle already has an active appointment or repair.");
        }

        Appointment appointment = new Appointment();
        appointment.setDateTime(LocalDateTime.parse(model.getDateTime()));
        appointment.setTask(Appointment.Task.valueOf(model.getTask().toUpperCase()));
        appointment.setVehicle(vehicle);

        return modelMapperUtil.toModel(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentModel update(Long id, AppointmentModel model) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Vehicle vehicle = vehicleRepository.findById(appointment.getVehicle().getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        appointment.setDateTime(LocalDateTime.parse(model.getDateTime()));
        appointment.setTask(Appointment.Task.valueOf(model.getTask().toUpperCase()));
        appointment.setVehicle(vehicle);

        return modelMapperUtil.toModel(appointmentRepository.save(appointment));
    }

    @Override
    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }
}