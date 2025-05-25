/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.controller;

import com.dealergestor.dealergestorbackend.DealerGestorBackendManager;
import com.dealergestor.dealergestorbackend.controller.ViewModel.AccidentPostViewModel;
import com.dealergestor.dealergestorbackend.controller.ViewModel.AccidentViewModel;
import com.dealergestor.dealergestorbackend.controller.ViewModel.RepairViewModel;
import com.dealergestor.dealergestorbackend.domain.model.Accident;
import com.dealergestor.dealergestorbackend.domain.model.CompanyUser;
import com.dealergestor.dealergestorbackend.domain.model.Vehicle;
import com.dealergestor.dealergestorbackend.utils.ViewModelMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accidents")
@Tag(name = "Accidents", description = "Endpoints for managing accidents")
public class AccidentController {

    private final DealerGestorBackendManager dealerGestorBackendManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public AccidentController(DealerGestorBackendManager dealerGestorBackendManager, ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorBackendManager = dealerGestorBackendManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @Operation(summary = "Get all accidents (active and inactive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All accidents retrieved successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/all")
    @ResponseBody
    public List<AccidentViewModel> findAllAccidents() {
        return dealerGestorBackendManager.findAllAccidents()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get all active accidents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Active accidents retrieved successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping
    @ResponseBody
    public List<AccidentViewModel> findAllAccidentsActive() {
        return dealerGestorBackendManager.findAllAccidentsActive()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Find accident by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accident retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Accident not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/{id}")
    @ResponseBody
    public AccidentViewModel findAccidentById(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.findAccidentById(id));
    }

    @Operation(summary = "Get all repairs by LicensePlate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repairs found successfully"),
            @ApiResponse(responseCode = "404", description = "Repairs not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/vehicle/{licensePlate}")
    @ResponseBody
    public List<AccidentViewModel> findAccidentByLicensePlate(@PathVariable String licensePlate) {
        return dealerGestorBackendManager.findAccidentByLicensePlate(licensePlate).stream().map(viewModelMapperUtil::toViewModel).toList();
    }

    @Operation(summary = "Get all repairs by Keychain")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repairs found successfully"),
            @ApiResponse(responseCode = "404", description = "Repairs not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/keychain/{keychain}")
    @ResponseBody
    public List<AccidentViewModel> findAccidentByKeychain(@PathVariable String keychain) {
        return dealerGestorBackendManager.findAccidentByKeychain(keychain).stream().map(viewModelMapperUtil::toViewModel).toList();
    }

    @Operation(summary = "Get all repairs by Keychain")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repairs found successfully"),
            @ApiResponse(responseCode = "404", description = "Repairs not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/insuranceCompany/{insuranceCompany}")
    @ResponseBody
    public List<AccidentViewModel> findAccidentByInsuranceCompany(@PathVariable String insuranceCompany) {
        return dealerGestorBackendManager.findAccidentByInsuranceCompany(insuranceCompany).stream().map(viewModelMapperUtil::toViewModel).toList();
    }

    @Operation(summary = "Create a new accident")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accident created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @PostMapping("/save")
    public AccidentViewModel saveAccident(@RequestBody AccidentPostViewModel accidentPostViewModel) {

        CompanyUser companyUser = dealerGestorBackendManager.findCompanyUserById(accidentPostViewModel.getOperatorId());
        Vehicle vehicle = dealerGestorBackendManager.findVehicleById(accidentPostViewModel.getVehicleId());

        Accident accident = new Accident();
        accident.setStatus(accidentPostViewModel.getStatus());
        accident.setDate(LocalDate.now());
        accident.setOperator(companyUser);
        accident.setVehicle(vehicle);
        accident.setActive(true);
        accident.setLocation(accidentPostViewModel.getLocation());
        accident.setInsuranceCompany(accidentPostViewModel.getInsuranceCompany());
        return viewModelMapperUtil.toViewModel(
                dealerGestorBackendManager.saveAccident(accident)
        );
    }

    @Operation(summary = "Update an existing accident by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accident updated successfully"),
            @ApiResponse(responseCode = "404", description = "Accident not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @PutMapping("/update/{id}")
    public AccidentViewModel updateAccident(@PathVariable Long id, @RequestBody AccidentPostViewModel updatedAccident) {
        return viewModelMapperUtil.toViewModel(
                dealerGestorBackendManager.updateAccident(id, viewModelMapperUtil.toModel(updatedAccident))
        );
    }

    @Operation(summary = "Delete an accident by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accident deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Accident not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccident(@PathVariable Long id) {
        dealerGestorBackendManager.deleteAccident(id);
        return ResponseEntity.ok().build();
    }
}