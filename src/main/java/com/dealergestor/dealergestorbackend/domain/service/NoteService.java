/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.model.Note;

import java.util.List;

public interface NoteService {

    List<Note> findAll();
    Note findById(Long id);
    Note create(Note request);
    void delete(Long id);
}