/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.model.NoteModel;

import java.util.List;

public interface NoteService {

    List<NoteModel> findAll();
    NoteModel findById(Long id);
    NoteModel create(NoteModel request);
    void delete(Long id);
}