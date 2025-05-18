package com.dealergestor.dealergestorbackend.controller;

import com.dealergestor.dealergestorbackend.DealerGestorBackendManager;
import com.dealergestor.dealergestorbackend.controller.ViewModel.PartViewModel;
import com.dealergestor.dealergestorbackend.utils.ViewModelMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parts")
@Tag(name = "Parts", description = "Endpoints for managing parts")
public class PartController {

    private final DealerGestorBackendManager dealerGestorBackendManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public PartController(DealerGestorBackendManager dealerGestorBackendManager, ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorBackendManager = dealerGestorBackendManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @Operation(summary = "Get all parts", description = "Retrieve a list of all parts")
    @ApiResponse(responseCode = "200", description = "Parts retrieved successfully")
    @GetMapping
    public List<PartViewModel> findAllParts() {
        return dealerGestorBackendManager.findAllParts()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get a part by ID", description = "Retrieve a specific part by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Part retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Part not found")
    })
    @GetMapping("/{id}")
    public PartViewModel findPartById(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.findPartById(id));
    }

    @Operation(summary = "Create a new part", description = "Save a new part in the system")
    @ApiResponse(responseCode = "201", description = "Part created successfully")
    @PostMapping("/save")
    public PartViewModel savePart(@RequestBody PartViewModel part) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.savePart(viewModelMapperUtil.toModel(part)));
    }

    @Operation(summary = "Update a part", description = "Update an existing part by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Part updated successfully"),
            @ApiResponse(responseCode = "404", description = "Part not found")
    })
    @PutMapping("/updateRepair/{id}")
    public PartViewModel updatePart(@PathVariable Long id, @RequestBody PartViewModel updatedPart) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.updatePart(id, viewModelMapperUtil.toModel(updatedPart)));
    }

    @Operation(summary = "Delete a part", description = "Delete a part by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Part deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Part not found")
    })
    @DeleteMapping("/deleteRepair/{id}")
    public ResponseEntity<?> deletePart(@PathVariable Long id) {
        dealerGestorBackendManager.deletePart(id);
        return ResponseEntity.ok().build();
    }
}