/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.controller;

import com.dealergestor.dealergestorbackend.DealerGestorBackendManager;
import com.dealergestor.dealergestorbackend.controller.ViewModel.RepairPostViewModel;
import com.dealergestor.dealergestorbackend.controller.ViewModel.RepairViewModel;
import com.dealergestor.dealergestorbackend.utils.ViewModelMapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/repairs")
public class RepairController {

    private final DealerGestorBackendManager dealerGestorBackendManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public RepairController(DealerGestorBackendManager dealerGestorBackendManager, ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorBackendManager = dealerGestorBackendManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @GetMapping("/all")
    public List<RepairViewModel> findAllRepairs() {
        return dealerGestorBackendManager.findAllRepairs()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<RepairViewModel> findAllRepairsActive() {
        return dealerGestorBackendManager.findAllRepairsActive()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RepairViewModel findRepairById(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.findRepairById(id));
    }

    @PostMapping("/save")
    public RepairViewModel saveRepair(@RequestBody RepairPostViewModel repairPostViewModel) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.saveRepair(viewModelMapperUtil.toModel(repairPostViewModel)));
    }

    @PutMapping("/update/{id}")
    public RepairViewModel updateRepair(@PathVariable Long id, @RequestBody RepairPostViewModel updatedRepair) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.updateRepair(id, viewModelMapperUtil.toModel(updatedRepair)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRepair(@PathVariable Long id) {
        dealerGestorBackendManager.deleteRepair(id);
        return ResponseEntity.ok().build();

    }
}