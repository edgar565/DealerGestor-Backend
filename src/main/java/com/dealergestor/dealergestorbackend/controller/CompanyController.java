/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.controller;

import com.dealergestor.dealergestorbackend.DealerGestorBackendManager;
import com.dealergestor.dealergestorbackend.controller.ViewModel.CompanyViewModel;
import com.dealergestor.dealergestorbackend.domain.model.Company;
import com.dealergestor.dealergestorbackend.utils.ViewModelMapperUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final DealerGestorBackendManager manager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public CompanyController(DealerGestorBackendManager manager,
                             ViewModelMapperUtil viewModelMapperUtil) {
        this.manager = manager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }


    @PostMapping("/{id}/update/logo")
    public CompanyViewModel uploadLogo(@PathVariable("id") Long companyId, @RequestParam("file") MultipartFile logoFile) {
        Company updated = manager.updateCompanyLogo(companyId, logoFile);
        return viewModelMapperUtil.toViewModel(updated);
    }
}