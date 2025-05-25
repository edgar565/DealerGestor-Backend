/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.controller;

import com.dealergestor.dealergestorbackend.DealerGestorBackendManager;
import com.dealergestor.dealergestorbackend.controller.ViewModel.AppointmentPostViewModel;
import com.dealergestor.dealergestorbackend.controller.ViewModel.AppointmentViewModel;
import com.dealergestor.dealergestorbackend.domain.model.Appointment;
import com.dealergestor.dealergestorbackend.domain.model.Vehicle;
import com.dealergestor.dealergestorbackend.utils.ViewModelMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments")
@Tag(name = "Appointments", description = "Endpoints for managing appointments")
public class AppointmentController {

    private final DealerGestorBackendManager dealerGestorBackendManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public AppointmentController(DealerGestorBackendManager dealerGestorBackendManager, ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorBackendManager = dealerGestorBackendManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @Operation(summary = "Get all appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping
    @ResponseBody
    public List<AppointmentViewModel> findAllAppointments() {
        return dealerGestorBackendManager.findAllAppointments()
                .stream()
                .map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get today's appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Today's appointments retrieved successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/now")
    @ResponseBody
    public List<AppointmentViewModel> findNowAppointments() {
        return dealerGestorBackendManager.findNowAppointments()
                .stream()
                .map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get a appointment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment found successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/{id}")
    @ResponseBody
    public AppointmentViewModel findAppointmentById(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.findAppointmentById(id));
    }

    @Operation(summary = "Get all appointment by LicensePlate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments found successfully"),
            @ApiResponse(responseCode = "404", description = "Appointments not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/vehicle/{licensePlate}")
    @ResponseBody
    public List<AppointmentViewModel> findAppointmentByLicensePlate(@PathVariable String licensePlate) {
        return dealerGestorBackendManager.findAppointmentByLicensePlate(licensePlate).stream().map(viewModelMapperUtil::toViewModel).toList();
    }

    @Operation(summary = "Get all appointment by ClientName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments found successfully"),
            @ApiResponse(responseCode = "404", description = "Appointments not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/client/{clientName}")
    @ResponseBody
    public List<AppointmentViewModel> findAppointmentByClientName(@PathVariable String clientName) {
        return dealerGestorBackendManager.findAppointmentByClientName(clientName).stream().map(viewModelMapperUtil::toViewModel).toList();
    }

    @Operation(summary = "Create a new appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid appointment data")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @PostMapping("/save")
    public AppointmentViewModel saveAppointment(@RequestBody AppointmentPostViewModel appointmentPostViewModel) {

        Vehicle vehicle = dealerGestorBackendManager.findVehicleById(appointmentPostViewModel.getVehicleId());

        Appointment appointment = new Appointment();
        appointment.setTask(appointmentPostViewModel.getTask());
        appointment.setDateTime(appointmentPostViewModel.getDateTime());
        appointment.setVehicle(vehicle);

        return viewModelMapperUtil.toViewModel(
                dealerGestorBackendManager.saveAppointment(appointment)
        );
    }

    @Operation(summary = "Update an existing appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @PutMapping("/update/{id}")
    public AppointmentViewModel updateAppointment(@PathVariable Long id, @RequestBody AppointmentPostViewModel updatedAppointment) {

        Vehicle vehicle = dealerGestorBackendManager.findVehicleById(updatedAppointment.getVehicleId());

        Appointment appointment = new Appointment();
        appointment.setTask(updatedAppointment.getTask());
        appointment.setDateTime(updatedAppointment.getDateTime());
        appointment.setVehicle(vehicle);
        return viewModelMapperUtil.toViewModel(
                dealerGestorBackendManager.updateAppointment(id, appointment)
        );
    }

    @Operation(summary = "Delete an appointment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        dealerGestorBackendManager.deleteAppointment(id);
        return ResponseEntity.ok().build();
    }
}