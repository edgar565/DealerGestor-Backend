/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.CompanyUser;
import com.dealergestor.dealergestorbackend.domain.entity.Note;
import com.dealergestor.dealergestorbackend.domain.entity.Vehicle;
import com.dealergestor.dealergestorbackend.domain.model.NoteModel;
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
    public List<NoteModel> findAll() {
        return noteRepository.findAll().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public NoteModel findById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        return modelMapperUtil.toModel(note);
    }

    @Override
    public NoteModel create(NoteModel model) {
        Vehicle vehicle = vehicleRepository.findById(model.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        CompanyUser user = companyUserRepository.findById(model.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Note note = new Note();
        note.setContent(model.getContent());
        note.setCreatedAt(LocalDateTime.now());
        note.setVehicle(vehicle);
        note.setCreatedBy(user);

        return modelMapperUtil.toModel(noteRepository.save(note));
    }

    @Override
    public void delete(Long id) {
        noteRepository.deleteById(id);
    }
}