package com.dealergestor.dealergestorbackend.controller;

import com.dealergestor.dealergestorbackend.DealerGestorBackendManager;
import com.dealergestor.dealergestorbackend.controller.ViewModel.VehiclePostViewModel;
import com.dealergestor.dealergestorbackend.controller.ViewModel.VehicleViewModel;
import com.dealergestor.dealergestorbackend.domain.model.Client;
import com.dealergestor.dealergestorbackend.domain.model.Vehicle;
import com.dealergestor.dealergestorbackend.utils.ViewModelMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Vehicles", description = "API endpoints for managing vehicles")
@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final DealerGestorBackendManager dealerGestorBackendManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public VehicleController(DealerGestorBackendManager dealerGestorBackendManager,
                             ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorBackendManager = dealerGestorBackendManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @Operation(summary = "Get all vehicles", description = "Returns a list of all vehicles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of vehicles retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicles not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/client/{clientId}")
    public List<VehicleViewModel> findAllVehiclesByClientId(@PathVariable Long clientId) {
        return dealerGestorBackendManager.findAllVehiclesByClientId(clientId)
                .stream()
                .map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get vehicle by ID", description = "Returns a single vehicle by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/{id}")
    public VehicleViewModel findVehicleById(
            @Parameter(description = "ID of the vehicle to retrieve", required = true)
            @PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(
                dealerGestorBackendManager.findVehicleById(id)
        );
    }

    @Operation(summary = "Get vehicle by license plate", description = "Returns a single vehicle by its license plate.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/licensePlate/{licensePlate}")
    public VehicleViewModel findVehicleByLicensePlate(
            @Parameter(description = "License plate of the vehicle to retrieve", required = true)
            @PathVariable String licensePlate) {
        return viewModelMapperUtil.toViewModel(
                dealerGestorBackendManager.findVehicleByLicensePlate(licensePlate)
        );
    }

    @Operation(summary = "Create a new vehicle", description = "Creates a new vehicle record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehicle created successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @PostMapping("/save")
    public ResponseEntity<VehicleViewModel> saveVehicle(
            @Parameter(description = "Datos del vehículo con su cliente", required = true,
                    content = @Content(schema = @Schema(implementation = VehiclePostViewModel.class)))
            @Valid @RequestBody VehiclePostViewModel vehicleViewModel) {

        System.out.println("Datos recibidos: " + vehicleViewModel);

        // Buscar el cliente existente por ID
        Client client = dealerGestorBackendManager.findClientById(vehicleViewModel.getClientViewModelId());

        if (vehicleViewModel == null) {
            System.out.println("vehiclePostViewModel es null");
        } else {
            System.out.println("License Plate: " + vehicleViewModel.getLicensePlate());
            System.out.println("Brand: " + vehicleViewModel.getBrand());
            System.out.println("Model: " + vehicleViewModel.getModel());
            System.out.println("Client: " + client);
        }

        // Crear el vehículo y asociarlo con el cliente
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(vehicleViewModel.getLicensePlate());
        vehicle.setBrand(vehicleViewModel.getBrand());
        vehicle.setModel(vehicleViewModel.getModel());
        vehicle.setClient(client);

        // Guardar el vehículo en la base de datos
        Vehicle savedVehicle = dealerGestorBackendManager.saveVehicle(vehicle);

        return ResponseEntity.ok(viewModelMapperUtil.toViewModel(savedVehicle));
    }


    @Operation(summary = "Update existing vehicle", description = "Updates details of an existing vehicle by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle updated successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @PutMapping("/update/{id}")
    public ResponseEntity<VehicleViewModel> updateVehicle(@PathVariable Long id, @RequestBody VehicleViewModel vehicleViewModel) {
        return ResponseEntity.ok(viewModelMapperUtil.toViewModel(
                dealerGestorBackendManager.updateVehicle(id, viewModelMapperUtil.toModel(vehicleViewModel))));
    }

    @Operation(summary = "Delete a vehicle", description = "Deletes a vehicle by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id) {
        dealerGestorBackendManager.deleteVehicle(id);
        return ResponseEntity.ok().build();
    }
}
