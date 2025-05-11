/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.controller;

import com.dealergestor.dealergestorbackend.DealerGestorBackendManager;
import com.dealergestor.dealergestorbackend.controller.ViewModel.ClientViewModel;
import com.dealergestor.dealergestorbackend.utils.ViewModelMapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final DealerGestorBackendManager dealerGestorBackendManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public ClientController(DealerGestorBackendManager dealerGestorBackendManager, ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorBackendManager = dealerGestorBackendManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @GetMapping
    @ResponseBody
    public List<ClientViewModel> findAllClients() {
        return dealerGestorBackendManager.findAllClients()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ClientViewModel findClientById(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.findClientById(id));
    }

    @PostMapping("/save")
    public ClientViewModel saveClient(@RequestBody ClientViewModel clientViewModel) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.saveClient(viewModelMapperUtil.toModel(clientViewModel)));
    }

    @PutMapping("/update/{id}")
    public ClientViewModel updateClient(@PathVariable Long id, @RequestBody ClientViewModel updatedClient) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.updateClient(id, viewModelMapperUtil.toModel(updatedClient)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        dealerGestorBackendManager.deleteClient(id);
        return ResponseEntity.ok().build();
    }
}