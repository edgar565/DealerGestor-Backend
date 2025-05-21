package com.dealergestor.dealergestorbackend.controller;

import com.dealergestor.dealergestorbackend.DealerGestorBackendManager;
import com.dealergestor.dealergestorbackend.controller.ViewModel.RepairPostViewModel;
import com.dealergestor.dealergestorbackend.controller.ViewModel.RepairViewModel;
import com.dealergestor.dealergestorbackend.domain.model.CompanyUser;
import com.dealergestor.dealergestorbackend.domain.model.Repair;
import com.dealergestor.dealergestorbackend.domain.model.Vehicle;
import com.dealergestor.dealergestorbackend.utils.ViewModelMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Repairs", description = "API endpoints for managing repairs")
@RestController
@RequestMapping("/repairs")
public class RepairController {

    private final DealerGestorBackendManager dealerGestorBackendManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public RepairController(DealerGestorBackendManager dealerGestorBackendManager, ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorBackendManager = dealerGestorBackendManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @Operation(summary = "Get all repairs (including inactive ones)", description = "Returns a list of all repairs, both active and inactive.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of repairs retrieved successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/all")
    public List<RepairViewModel> findAllRepairs() {
        return dealerGestorBackendManager.findAllRepairs()
                .stream()
                .map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get active repairs", description = "Returns a list of all active repairs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of active repairs retrieved successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping
    public List<RepairViewModel> findAllRepairsActive() {
        return dealerGestorBackendManager.findAllRepairsActive()
                .stream()
                .map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get repair by ID", description = "Returns a single repair by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repair retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Repair not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/{id}")
    public RepairViewModel findRepairById(
            @Parameter(description = "ID of the repair to retrieve", required = true)
            @PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.findRepairById(id));
    }

    @Operation(summary = "Create a new repair", description = "Creates a new repair record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Repair created successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @PostMapping("/save")
    public ResponseEntity<RepairViewModel> saveRepair(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Repair data to create", required = true)
            @RequestBody RepairPostViewModel repairPostViewModel) {

        CompanyUser companyUser = dealerGestorBackendManager.findCompanyUserById(repairPostViewModel.getOperatorId());
        Vehicle vehicle = dealerGestorBackendManager.findVehicleById(repairPostViewModel.getVehicleId());

        Repair repair = new Repair();
        repair.setStatus(repairPostViewModel.getStatus());
        repair.setDate(LocalDate.now());
        repair.setOperator(companyUser);
        repair.setVehicle(vehicle);
        repair.setActive(true);

        RepairViewModel result = viewModelMapperUtil.toViewModel(
                dealerGestorBackendManager.saveRepair(repair)
        );
        return ResponseEntity.status(201).body(result);
    }

    @Operation(summary = "Update existing repair", description = "Updates details of an existing repair by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repair updated successfully"),
            @ApiResponse(responseCode = "404", description = "Repair not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @PutMapping("/update/{id}")
    public RepairViewModel updateRepair(
            @Parameter(description = "ID of the repair to update", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated repair data", required = true)
            @RequestBody RepairPostViewModel updatedRepair) {

        CompanyUser companyUser = dealerGestorBackendManager.findCompanyUserById(updatedRepair.getOperatorId());


        Repair repair = new Repair();
        repair.setStatus(updatedRepair.getStatus());
        repair.setDate(LocalDate.now());
        repair.setOperator(companyUser);

        return viewModelMapperUtil.toViewModel(
                dealerGestorBackendManager.updateRepair(id, repair)
        );
    }

    @Operation(summary = "Delete a repair", description = "Deletes a repair by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repair deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Repair not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRepair(
            @Parameter(description = "ID of the repair to delete", required = true)
            @PathVariable Long id) {
        dealerGestorBackendManager.deleteRepair(id);
        return ResponseEntity.ok().build();
    }
}