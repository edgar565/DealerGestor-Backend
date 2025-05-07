package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.*;
import com.dealergestor.dealergestorbackend.domain.model.*;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class ModelMapperUtil {

    public Client toModel(ClientEntity clientEntity) {
        Client model = new Client();
        model.setClientId(clientEntity.getClientId());
        model.setName(clientEntity.getName());
        model.setPhone(clientEntity.getPhone());
        model.setVehicles(clientEntity.getVehicleEntities().stream(
        ).map(vehicle -> new Vehicle(
                vehicle.getVehicleId(),
                vehicle.getLicensePlate(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getRepairEntity() != null ? toModel(vehicle.getRepairEntity()) : null,
                vehicle.getAppointmentEntity() != null ? toModel(vehicle.getAppointmentEntity()) : null
        )));
        return model;
    }

    public Vehicle toModel(VehicleEntity vehicleEntity) {
        Vehicle model = new Vehicle();
        model.setVehicleId(vehicleEntity.getVehicleId());
        model.setLicensePlate(vehicleEntity.getLicensePlate());
        model.setBrand(vehicleEntity.getBrand());
        model.setModel(vehicleEntity.getModel());
        return model;
    }

    public Appointment toModel(AppointmentEntity appointmentEntity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        Appointment model = new Appointment();
        model.setAppointmentId(appointmentEntity.getAppointmentId());
        model.setDateTime(appointmentEntity.getDateTime().format(formatter));
        model.setTask(appointmentEntity.getTask().name());
        return model;
    }

    public Repair toModel(RepairEntity repairEntity) {
        Repair model = new Repair();
        model.setRepairId(repairEntity.getRepairId());
        model.getOperator(repairEntity.getOperator());

        model.setStatus(repairEntity.getStatus());
        return model;
    }

    public Accident toModel(AccidentEntity accidentEntity) {
        Accident model = new Accident();
        model.setRepairId(accidentEntity.getRepairId());
        model.setStartDate(accidentEntity.getStartDate().toString());
        model.setEndDate(accidentEntity.getEndDate().toString());
        model.setDescription(accidentEntity.getDescription());
        model.setStatus(accidentEntity.getStatus());
        model.setVehiclePlate(accidentEntity.getVehicleEntity().getLicensePlate());
        model.setPoliceReportNumber(accidentEntity.getPoliceReportNumber());
        model.setAtFault(accidentEntity.isAtFault());
        return model;
    }

    public Part toModel(PartEntity partEntity) {
        Part model = new Part();
        model.setId(partEntity.getId());
        model.setName(partEntity.getName());
        model.setBrand(partEntity.getBrand());
        model.setPrice(partEntity.getPrice());
        model.setKeychain(partEntity.getKeychain());
        model.setRepairDescription(partEntity.getRepairEntity() != null ? partEntity.getRepairEntity().getDescription() : null);
        return model;
    }

    public Note toModel(NoteEntity noteEntity) {
        Note model = new Note();
        model.setNoteId(noteEntity.getNoteId());
        model.setTitle(noteEntity.getTitle());
        model.setContent(noteEntity.getContent());
        model.setCreatedAt(noteEntity.getCreatedAt().toString());

        return model;
    }
}