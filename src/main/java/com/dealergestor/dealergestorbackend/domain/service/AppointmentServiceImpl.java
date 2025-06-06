/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.AppointmentEntity;
import com.dealergestor.dealergestorbackend.domain.entity.VehicleEntity;
import com.dealergestor.dealergestorbackend.domain.model.Appointment;
import com.dealergestor.dealergestorbackend.domain.repository.AccidentRepository;
import com.dealergestor.dealergestorbackend.domain.repository.AppointmentRepository;
import com.dealergestor.dealergestorbackend.domain.repository.RepairRepository;
import com.dealergestor.dealergestorbackend.domain.repository.VehicleRepository;
import com.dealergestor.dealergestorbackend.utils.ModelMapperUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final VehicleRepository vehicleRepository;
    private final RepairRepository repairRepository;
    private final ModelMapperUtil modelMapperUtil;
    private final AccidentRepository accidentRepository;


    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            VehicleRepository vehicleRepository,
            RepairRepository repairRepository,
            ModelMapperUtil modelMapperUtil,
            AccidentRepository accidentRepository

    ) {
        this.appointmentRepository = appointmentRepository;
        this.vehicleRepository = vehicleRepository;
        this.repairRepository = repairRepository;
        this.modelMapperUtil = modelMapperUtil;
        this.accidentRepository = accidentRepository;
    }

    @Override
    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findNowAppointments() {
        return appointmentRepository.findAll().stream()
                .filter(a -> a.getDateTime().toLocalDate().isEqual(LocalDate.now()))
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Appointment findAppointmentById(Long id) {
        AppointmentEntity appointmentEntity = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        return modelMapperUtil.toModel(appointmentEntity);
    }

    @Override
    public List<Appointment> findAppointmentByLicensePlate(String licensePlate) {
        return appointmentRepository.findAll().stream()
                .filter(a -> Objects.equals(a.getVehicle().getLicensePlate(), licensePlate))
                .map(modelMapperUtil::toModel)
                .toList();
    }

    @Override
    public List<Appointment> findAppointmentByClientName(String name) {
        return appointmentRepository.findAll().stream()
                .filter(a -> Objects.equals(a.getVehicle().getClient().getName(), name))
                .map(modelMapperUtil::toModel)
                .toList();
    }

    @Override
    public Appointment saveAppointment(Appointment model) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(model.getVehicle().getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        boolean hasRepair = repairRepository.existsByVehicle(vehicleEntity);
        boolean hasAppointment = appointmentRepository.existsByVehicle(vehicleEntity);
        boolean hasAccident = accidentRepository.existsByVehicle(vehicleEntity);


        if (hasRepair || hasAppointment || hasAccident) {
            throw new RuntimeException("This vehicle already has an active appointment, repair, or accident.");
        }

        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setDateTime(model.getDateTime());
        appointment.setTask(AppointmentEntity.Task.valueOf(model.getTask().toUpperCase()));
        appointment.setVehicle(vehicleEntity);

        return modelMapperUtil.toModel(appointmentRepository.save(appointment));
    }

    @Override
    public Appointment updateAppointment(Long id, Appointment model) {
        AppointmentEntity appointmentEntity = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        VehicleEntity vehicleEntity = vehicleRepository.findById(model.getVehicle().getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        appointmentEntity.setDateTime(model.getDateTime());
        appointmentEntity.setTask(AppointmentEntity.Task.valueOf(model.getTask().toUpperCase()));
        appointmentEntity.setVehicle(vehicleEntity);

        return modelMapperUtil.toModel(appointmentRepository.save(appointmentEntity));
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }


}