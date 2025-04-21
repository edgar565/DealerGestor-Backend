/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.Client;
import com.dealergestor.dealergestorbackend.domain.model.ClientModel;
import com.dealergestor.dealergestorbackend.domain.model.VehicleModel;
import com.dealergestor.dealergestorbackend.domain.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;
    private final RepairServiceImpl repairServiceImpl;
    private final AppointmentServiceImpl appointmentServiceImpl;
    private final ModelMapperUtil modelMapperUtil;

    public ClientServiceImpl(ClientRepository clientRepository, RepairServiceImpl repairServiceImpl, AppointmentServiceImpl appointmentServiceImpl, ModelMapperUtil modelMapperUtil) {
        this.clientRepository = clientRepository;
        this.repairServiceImpl = repairServiceImpl;
        this.appointmentServiceImpl = appointmentServiceImpl;
        this.modelMapperUtil = new ModelMapperUtil();
    }

    @Override
    public List<ClientModel> findAll() {
        return clientRepository.findAll().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public ClientModel findById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return modelMapperUtil.toModel(client);
    }

    @Override
    public ClientModel create(ClientModel model) {
        if (clientRepository.existsByPhone(model.getPhone())) {
            throw new RuntimeException("Phone number already exists");
        }

        Client client = new Client();
        client.setName(model.getName());
        client.setPhone(model.getPhone());
        client.setVehicles(new ArrayList<>());

        return modelMapperUtil.toModel(clientRepository.save(client));
    }

    @Override
    public ClientModel update(Long id, ClientModel model) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        client.setName(model.getName());
        client.setPhone(model.getPhone());
        return modelMapperUtil.toModel(clientRepository.save(client));
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
}