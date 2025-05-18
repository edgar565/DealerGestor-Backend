/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.CompanyUserEntity;
import com.dealergestor.dealergestorbackend.domain.entity.NoteEntity;
import com.dealergestor.dealergestorbackend.domain.model.Note;
import com.dealergestor.dealergestorbackend.domain.repository.CompanyUserRepository;
import com.dealergestor.dealergestorbackend.domain.repository.NoteRepository;
import com.dealergestor.dealergestorbackend.utils.ModelMapperUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService{

    private final NoteRepository noteRepository;
    private final CompanyUserRepository companyUserRepository;
    private final ModelMapperUtil modelMapperUtil;

    public NoteServiceImpl(NoteRepository noteRepository, CompanyUserRepository companyUserRepository, ModelMapperUtil modelMapperUtil) {
        this.noteRepository = noteRepository;
        this.companyUserRepository = companyUserRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    @Override
    public List<Note> findAllNotes() {
        return noteRepository.findAll().stream()
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Note findNoteById(Long id) {
        NoteEntity noteEntity = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        return modelMapperUtil.toModel(noteEntity);
    }

    @Override
    public Note saveNote(Note model) {
        CompanyUserEntity user = companyUserRepository.findById(model.getCompanyUser().getCompanyUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setTitle(model.getTitle());
        noteEntity.setContent(model.getContent());
        noteEntity.setCreatedAt(LocalDateTime.now());
        noteEntity.setCompanyUserEntity(user);

        return modelMapperUtil.toModel(noteRepository.save(noteEntity));
    }

    @Override
    public Note updateNote(Long id, Note updatedNote) {
        NoteEntity existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with ID: " + id));

        existingNote.setTitle(updatedNote.getTitle());
        existingNote.setContent(updatedNote.getContent());

        NoteEntity saved = noteRepository.save(existingNote);
        return modelMapperUtil.toModel(saved);
    }

    @Override
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
}