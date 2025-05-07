/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.CompanyUserEntity;
import com.dealergestor.dealergestorbackend.domain.entity.NoteEntity;
import com.dealergestor.dealergestorbackend.domain.entity.VehicleEntity;
import com.dealergestor.dealergestorbackend.domain.model.Note;
import com.dealergestor.dealergestorbackend.domain.repository.CompanyUserRepository;
import com.dealergestor.dealergestorbackend.domain.repository.NoteRepository;
import com.dealergestor.dealergestorbackend.domain.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService{

    private final NoteRepository noteRepository;
    private final VehicleRepository vehicleRepository;
    private final CompanyUserRepository companyUserRepository;
    private final ModelMapperUtil modelMapperUtil;

    public NoteServiceImpl(NoteRepository noteRepository, VehicleRepository vehicleRepository, CompanyUserRepository companyUserRepository, ModelMapperUtil modelMapperUtil) {
        this.noteRepository = noteRepository;
        this.vehicleRepository = vehicleRepository;
        this.companyUserRepository = companyUserRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    @Override
    public List<Note> findAll() {
        return noteRepository.findAll().stream()
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Note findById(Long id) {
        NoteEntity noteEntity = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        return modelMapperUtil.toModel(noteEntity);
    }

    @Override
    public Note create(Note model) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(model.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        CompanyUserEntity user = companyUserRepository.findById(model.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setContent(model.getContent());
        noteEntity.setCreatedAt(LocalDateTime.now());
        noteEntity.setVehicle(vehicleEntity);
        noteEntity.setCreatedBy(user);

        return modelMapperUtil.toModel(noteRepository.save(noteEntity));
    }

    @Override
    public void delete(Long id) {
        noteRepository.deleteById(id);
    }
}