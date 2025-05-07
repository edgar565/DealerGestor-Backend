/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.ClientEntity;
import com.dealergestor.dealergestorbackend.domain.model.Client;
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
    public List<Client> findAll() {
        return clientRepository.findAll().stream()
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Client findById(Long id) {
        ClientEntity clientEntity = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return modelMapperUtil.toModel(clientEntity);
    }

    @Override
    public Client create(Client model) {
        if (clientRepository.existsByPhone(model.getPhone())) {
            throw new RuntimeException("Phone number already exists");
        }

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName(model.getName());
        clientEntity.setPhone(model.getPhone());
        clientEntity.setVehicleEntities(new ArrayList<>());

        return modelMapperUtil.toModel(clientRepository.save(clientEntity));
    }

    @Override
    public Client update(Long id, Client model) {
        ClientEntity clientEntity = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        clientEntity.setName(model.getName());
        clientEntity.setPhone(model.getPhone());
        return modelMapperUtil.toModel(clientRepository.save(clientEntity));
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
}