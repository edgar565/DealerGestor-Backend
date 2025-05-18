/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.controller;

import com.dealergestor.dealergestorbackend.DealerGestorBackendManager;
import com.dealergestor.dealergestorbackend.controller.ViewModel.CompanyConfigurationPostViewModel;
import com.dealergestor.dealergestorbackend.controller.ViewModel.CompanyConfigurationViewModel;
import com.dealergestor.dealergestorbackend.utils.ViewModelMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/company")
@Tag(name = "Company Configuration", description = "Endpoints for company branding and general settings")
public class CompanyConfigurationController {

    private final DealerGestorBackendManager manager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public CompanyConfigurationController(DealerGestorBackendManager manager,
                                          ViewModelMapperUtil viewModelMapperUtil) {
        this.manager = manager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @Operation(summary = "Get company configuration by ID", description = "Returns the configuration settings for a specific company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configuration found"),
            @ApiResponse(responseCode = "404", description = "Configuration not found")
    })
    @GetMapping("/{id}")
    public CompanyConfigurationViewModel findCompanyConfiguration(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(manager.findCompanyConfiguration(id));
    }

    @Operation(summary = "Update full company configuration", description = "Updates configuration settings and optionally uploads a new logo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configuration updated successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden – insufficient permissions"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PutMapping(value = "/update", consumes = "multipart/form-data")
    public CompanyConfigurationViewModel updateFullConfiguration(
            @RequestPart("data") CompanyConfigurationPostViewModel request,
            @RequestPart(value = "logo", required = false) MultipartFile logoFile) {

        return viewModelMapperUtil.toViewModel(
                manager.updateCompanyConfiguration(viewModelMapperUtil.toModel(request), logoFile)
        );
    }

}