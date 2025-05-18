package com.dealergestor.dealergestorbackend.controller;

import com.dealergestor.dealergestorbackend.DealerGestorBackendManager;
import com.dealergestor.dealergestorbackend.controller.ViewModel.NoteViewModel;
import com.dealergestor.dealergestorbackend.utils.ViewModelMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller responsible for managing notes.
 */
@RestController
@RequestMapping("/notes")
@Tag(name = "Notes", description = "Endpoints for managing notes")
public class NoteController {

    private final DealerGestorBackendManager dealerGestorBackendManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public NoteController(DealerGestorBackendManager dealerGestorBackendManager, ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorBackendManager = dealerGestorBackendManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @Operation(summary = "Get all notes", description = "Retrieve a list of all notes")
    @ApiResponse(responseCode = "200", description = "List of notes retrieved successfully")
    @GetMapping
    public List<NoteViewModel> findAllNotes() {
        return dealerGestorBackendManager.findAllNotes()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get a note by ID", description = "Retrieve a specific note by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @GetMapping("/{id}")
    public NoteViewModel findNoteById(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorBackendManager.findNoteById(id));
    }

    @Operation(summary = "Create a new note", description = "Save a new note in the system")
    @ApiResponse(responseCode = "201", description = "Note created successfully")
    @PostMapping("/save")
    public NoteViewModel saveNote(@RequestBody NoteViewModel noteViewModel) {
        return viewModelMapperUtil.toViewModel(
                dealerGestorBackendManager.saveNote(viewModelMapperUtil.toModel(noteViewModel))
        );
    }

    @Operation(summary = "Update a note", description = "Update an existing note by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note updated successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @PutMapping("/update/{id}")
    public NoteViewModel updateNote(@PathVariable Long id, @RequestBody NoteViewModel updatedNote) {
        return viewModelMapperUtil.toViewModel(
                dealerGestorBackendManager.updateNote(id, viewModelMapperUtil.toModel(updatedNote))
        );
    }

    @Operation(summary = "Delete a note", description = "Delete a note by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        dealerGestorBackendManager.deleteNote(id);
        return ResponseEntity.ok().build();
    }
}