package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.*;
import com.dealergestor.dealergestorbackend.domain.model.*;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class ModelMapperUtil {

    public ClientModel toModel(Client client) {
        ClientModel model = new ClientModel();
        model.setClientId(client.getClientId());
        model.setName(client.getName());
        model.setPhone(client.getPhone());
        model.setVehicles(client.getVehicles().stream(
        ).map(vehicle -> new VehicleModel(
                vehicle.getVehicleId(),
                vehicle.getLicensePlate(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getRepair() != null ? toModel(vehicle.getRepair()) : null,
                vehicle.getAppointment() != null ? toModel(vehicle.getAppointment()) : null
        )));
        return model;
    }

    public VehicleModel toModel(Vehicle vehicle) {
        VehicleModel model = new VehicleModel();
        model.setVehicleId(vehicle.getVehicleId());
        model.setLicensePlate(vehicle.getLicensePlate());
        model.setBrand(vehicle.getBrand());
        model.setModel(vehicle.getModel());
        return model;
    }

    public AppointmentModel toModel(Appointment appointment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        AppointmentModel model = new AppointmentModel();
        model.setAppointmentId(appointment.getAppointmentId());
        model.setDateTime(appointment.getDateTime().format(formatter));
        model.setTask(appointment.getTask().name());
        return model;
    }

    public RepairModel toModel(Repair repair) {
        RepairModel model = new RepairModel();
        model.setRepairId(repair.getRepairId());
        model.getOperator(repair.getOperator());

        model.setStatus(repair.getStatus());
        return model;
    }

    public AccidentModel toModel(Accident accident) {
        AccidentModel model = new AccidentModel();
        model.setRepairId(accident.getRepairId());
        model.setStartDate(accident.getStartDate().toString());
        model.setEndDate(accident.getEndDate().toString());
        model.setDescription(accident.getDescription());
        model.setStatus(accident.getStatus());
        model.setVehiclePlate(accident.getVehicle().getLicensePlate());
        model.setPoliceReportNumber(accident.getPoliceReportNumber());
        model.setAtFault(accident.isAtFault());
        return model;
    }

    public PartModel toModel(Part part) {
        PartModel model = new PartModel();
        model.setId(part.getId());
        model.setName(part.getName());
        model.setBrand(part.getBrand());
        model.setPrice(part.getPrice());
        model.setKeychain(part.getKeychain());
        model.setRepairDescription(part.getRepair() != null ? part.getRepair().getDescription() : null);
        return model;
    }

    public NoteModel toModel(Note note) {
        NoteModel model = new NoteModel();
        model.setNoteId(note.getNoteId());
        model.setTitle(note.getTitle());
        model.setContent(note.getContent());
        model.setCreatedAt(note.getCreatedAt().toString());

        return model;
    }
}