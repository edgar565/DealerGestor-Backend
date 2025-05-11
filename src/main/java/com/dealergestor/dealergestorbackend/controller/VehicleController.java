/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.controller;

import com.dealergestor.dealergestorbackend.DealerGestorBackendManager;
import com.dealergestor.dealergestorbackend.controller.ViewModel.VehicleViewModel;
import com.dealergestor.dealergestorbackend.utils.ViewModelMapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController("/Vehicles")
public class VehicleController {

    private final DealerGestorBackendManager dealerGestorBackendManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public VehicleController(DealerGestorBackendManager dealerGestorBackendManager, ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorBackendManager = dealerGestorBackendManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @GetMapping
    @ResponseBody
    public List<VehicleViewModel> findAllVehicles() {
        return dealerGestorBackendManager.findAllVehicles()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public VehicleViewModel findVehicleById(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.findVehicleById(id));
    }

    @GetMapping("licensePlate/{licensePlate}")
    @ResponseBody
    public VehicleViewModel findVehicleByLicensePlate(@PathVariable String licensePlate) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.findVehicleByLicensePlate(licensePlate));
    }

//    @GetMapping("/repair/keychain/{keychain}")
//    @ResponseBody
//    public VehicleViewModel getMotorcyclesByKeychain(@PathVariable String keychain) {
//        return dealerGestorBackendManager.getMotorcyclesByKeychain(keychain);
//    }

    @PostMapping("/save")
    public VehicleViewModel saveVehicle(@RequestBody VehicleViewModel vehicleViewModel) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.saveVehicle(viewModelMapperUtil.toModel(vehicleViewModel)));
    }

    @PutMapping("/update/{id}")
    public VehicleViewModel updateVehicle(@PathVariable Long id, @RequestBody VehicleViewModel vehicleViewModel) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.updateVehicle(id, viewModelMapperUtil.toModel(vehicleViewModel)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id) {
        dealerGestorBackendManager.deleteVehicle(id);
        return ResponseEntity.ok().build();
    }
}