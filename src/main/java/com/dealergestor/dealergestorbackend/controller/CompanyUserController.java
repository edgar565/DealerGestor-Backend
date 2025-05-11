/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.controller;

import com.dealergestor.dealergestorbackend.DealerGestorBackendManager;
import com.dealergestor.dealergestorbackend.controller.ViewModel.CompanyUserPostViewModel;
import com.dealergestor.dealergestorbackend.controller.ViewModel.CompanyUserViewModel;
import com.dealergestor.dealergestorbackend.utils.ViewModelMapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/company-users")
public class CompanyUserController {

    private final DealerGestorBackendManager dealerGestorBackendManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public CompanyUserController(DealerGestorBackendManager dealerGestorBackendManager,
                                 ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorBackendManager = dealerGestorBackendManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @GetMapping
    public List<CompanyUserViewModel> findAllCompanyUsers() {
        return dealerGestorBackendManager.findAllCompanyUsers().stream()
                .map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CompanyUserViewModel findCompanyUserById(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.findCompanyUserById(id));
    }

    @PostMapping("/register")
    public CompanyUserViewModel saveCompanyUser(@RequestBody CompanyUserPostViewModel companyUserPostViewModel) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.saveCompanyUser(viewModelMapperUtil.toModel(companyUserPostViewModel)));
    }

    @PutMapping("/update/{id}")
    public CompanyUserViewModel updateCompanyUser(@PathVariable Long id, @RequestBody CompanyUserPostViewModel companyUserPostViewModel) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.updateCompanyUser(id, viewModelMapperUtil.toModel(companyUserPostViewModel)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCompanyUser(@PathVariable Long id) {
        dealerGestorBackendManager.deleteCompanyUser(id);
        return ResponseEntity.ok().build();
    }
}