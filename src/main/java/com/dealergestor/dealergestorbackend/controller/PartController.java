/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.controller;

import com.dealergestor.dealergestorbackend.DealerGestorBackendManager;
import com.dealergestor.dealergestorbackend.controller.ViewModel.PartViewModel;
import com.dealergestor.dealergestorbackend.utils.ViewModelMapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parts")
public class PartController {

    private final DealerGestorBackendManager dealerGestorBackendManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public PartController(DealerGestorBackendManager dealerGestorBackendManager, ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorBackendManager = dealerGestorBackendManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @GetMapping
    @ResponseBody
    public List<PartViewModel> findAllParts() {
        return dealerGestorBackendManager.findAllParts()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public PartViewModel findPartById(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.findPartById(id));
    }

    @PostMapping("/save")
    public PartViewModel savePart(@RequestBody PartViewModel part) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.savePart(viewModelMapperUtil.toModel(part)));
    }

    @PutMapping("/updateRepair/{id}")
    public PartViewModel updatePart(@PathVariable Long id, @RequestBody PartViewModel updatedPart) {
      return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.updatePart(id, viewModelMapperUtil.toModel(updatedPart)));
    }

    @DeleteMapping("/deleteRepair/{id}")
    public ResponseEntity<?> deletePart(@PathVariable Long id) {
        dealerGestorBackendManager.deletePart(id);
        return ResponseEntity.ok().build();
    }
}