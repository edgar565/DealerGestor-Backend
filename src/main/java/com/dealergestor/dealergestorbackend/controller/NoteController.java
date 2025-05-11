/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.controller;

import com.dealergestor.dealergestorbackend.DealerGestorBackendManager;
import com.dealergestor.dealergestorbackend.controller.ViewModel.NoteViewModel;
import com.dealergestor.dealergestorbackend.utils.ViewModelMapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final DealerGestorBackendManager dealerGestorBackendManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public NoteController(DealerGestorBackendManager dealerGestorBackendManager, ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorBackendManager = dealerGestorBackendManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @GetMapping
    @ResponseBody
    public List<NoteViewModel> findAllNotes() {
        return dealerGestorBackendManager.findAllNotes()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public NoteViewModel findNoteById(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.findNoteById(id));
    }

    @PostMapping("/save")
    public NoteViewModel saveNote(@RequestBody NoteViewModel noteViewModel) {
        return viewModelMapperUtil.toViewModel(
                dealerGestorBackendManager.saveNote(viewModelMapperUtil.toModel(noteViewModel))
        );
    }

    @PutMapping("/update/{id}")
    public NoteViewModel updateNote(@PathVariable Long id, @RequestBody NoteViewModel updatedNote) {
        return viewModelMapperUtil.toViewModel(
                dealerGestorBackendManager.updateNote(id, viewModelMapperUtil.toModel(updatedNote))
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        dealerGestorBackendManager.deleteNote(id);
        return ResponseEntity.ok().build();
    }
}