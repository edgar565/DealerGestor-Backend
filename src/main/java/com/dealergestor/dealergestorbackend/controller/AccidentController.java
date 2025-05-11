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
import com.dealergestor.dealergestorbackend.utils.ViewModelMapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accidents")
public class AccidentController {

    private final DealerGestorBackendManager dealerGestorBackendManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public AccidentController(DealerGestorBackendManager dealerGestorBackendManager, ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorBackendManager = dealerGestorBackendManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @GetMapping("/all")
    public List<AccidentViewModel> findAllAccidents() {
        return dealerGestorBackendManager.findAllAccidents()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<AccidentViewModel> findAllAccidentsActive() {
        return dealerGestorBackendManager.findAllAccidentsActive()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AccidentViewModel findAccidentById(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.findAccidentById(id));
    }

    @PostMapping("/save")
    public AccidentViewModel saveAccident(@RequestBody AccidentPostViewModel accidentPostViewModel) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.saveAccident(viewModelMapperUtil.toModel(accidentPostViewModel)));
    }

    @PutMapping("/update/{id}")
    public AccidentViewModel updateAccident(@PathVariable Long id, @RequestBody AccidentPostViewModel updatedAccident) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.updateAccident(id, viewModelMapperUtil.toModel(updatedAccident)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccident(@PathVariable Long id) {
        dealerGestorBackendManager.deleteAccident(id);
        return ResponseEntity.ok().build();
    }
}