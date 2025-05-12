/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.controller;

import com.dealergestor.dealergestorbackend.DealerGestorBackendManager;
import com.dealergestor.dealergestorbackend.controller.ViewModel.AppointmentPostViewModel;
import com.dealergestor.dealergestorbackend.controller.ViewModel.AppointmentViewModel;
import com.dealergestor.dealergestorbackend.utils.ViewModelMapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {


    private final DealerGestorBackendManager dealerGestorBackendManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public AppointmentController(DealerGestorBackendManager dealerGestorBackendManager, ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorBackendManager = dealerGestorBackendManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }


    @GetMapping
    @ResponseBody
    public List<AppointmentViewModel> findAllAppointments() {
        return dealerGestorBackendManager.findAllAppointments()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/now")
    @ResponseBody
    public List<AppointmentViewModel> findNowAppointments() {
        return dealerGestorBackendManager.findNowAppointments()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public AppointmentViewModel findAppointmentById(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.findAppointmentById(id));
    }

    @PostMapping("/save")
    public AppointmentViewModel saveAppointment(@RequestBody AppointmentPostViewModel appointmentPostViewModel) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.saveAppointment(viewModelMapperUtil.toModel(appointmentPostViewModel)));
    }

    @PutMapping("/update/{id}")
    public AppointmentViewModel updateAppointment(@PathVariable Long id, @RequestBody AppointmentPostViewModel updatedAppointment) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.updateAppointment(id, viewModelMapperUtil.toModel(updatedAppointment)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        dealerGestorBackendManager.deleteAppointment(id);
        return ResponseEntity.ok().build();
    }
}