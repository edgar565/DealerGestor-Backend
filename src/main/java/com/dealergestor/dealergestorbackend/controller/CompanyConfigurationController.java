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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/company")
public class CompanyConfigurationController {

    private final DealerGestorBackendManager manager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public CompanyConfigurationController(DealerGestorBackendManager manager,
                             ViewModelMapperUtil viewModelMapperUtil) {
        this.manager = manager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @GetMapping("/{id}")
    public CompanyConfigurationViewModel findCompanyConfiguration(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(manager.findCompanyConfiguration(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PutMapping(value = "/update", consumes = "multipart/form-data")
    public CompanyConfigurationViewModel updateFullConfiguration(@RequestPart("data") CompanyConfigurationPostViewModel request, @RequestPart(value = "logo", required = false) MultipartFile logoFile) {
        return viewModelMapperUtil.toViewModel(
                manager.updateCompanyConfiguration(viewModelMapperUtil.toModel(request), logoFile)
        );
    }

}