/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.controller;

import com.dealergestor.dealergestorbackend.DealerGestorBackendManager;
import com.dealergestor.dealergestorbackend.controller.ViewModel.ClientPostViewModel;
import com.dealergestor.dealergestorbackend.controller.ViewModel.ClientViewModel;
import com.dealergestor.dealergestorbackend.utils.ViewModelMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
@Tag(name = "Clients", description = "Endpoints for managing clients")
public class ClientController {

    private final DealerGestorBackendManager dealerGestorBackendManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public ClientController(DealerGestorBackendManager dealerGestorBackendManager, ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorBackendManager = dealerGestorBackendManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @Operation(summary = "Get all clients", description = "Returns a list of all clients in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clients found"),
            @ApiResponse(responseCode = "404", description = "Clients not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping
    @ResponseBody
    public List<ClientViewModel> findAllClients() {
        return dealerGestorBackendManager.findAllClients()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get a client by ID", description = "Returns the client with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client found"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/{id}")
    @ResponseBody
    public ClientViewModel findClientById(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.findClientById(id));
    }

    @Operation(summary = "Get a client by name", description = "Returns the client with the specified name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client found"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/{name}")
    @ResponseBody
    public ClientViewModel findClientByName(@PathVariable String name) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.findClientByName(name));
    }

    @Operation(summary = "Create a new client", description = "Registers a new client in the system")
    @ApiResponse(responseCode = "201", description = "Client created successfully")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @PostMapping("/save")
    public ResponseEntity<ClientViewModel> saveClient(@RequestBody ClientPostViewModel clientViewModel) {
        return ResponseEntity.ok(viewModelMapperUtil.toViewModel(dealerGestorBackendManager.saveClient(viewModelMapperUtil.toModel(clientViewModel))));
    }

    @Operation(summary = "Update an existing client", description = "Modifies the data of an existing client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client updated successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ClientViewModel> updateClient(@PathVariable Long id, @RequestBody ClientPostViewModel updatedClient) {
        return ResponseEntity.ok(viewModelMapperUtil.toViewModel(dealerGestorBackendManager.updateClient(id, viewModelMapperUtil.toModel(updatedClient))));
    }

    @Operation(summary = "Delete a client", description = "Deletes a client with the specified ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        dealerGestorBackendManager.deleteClient(id);
        return ResponseEntity.ok().build();
    }
}