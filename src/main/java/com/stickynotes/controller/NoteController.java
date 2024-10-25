package com.stickynotes.controller;

import com.stickynotes.dto.NoteDTO;
import com.stickynotes.dto.ResponseDTO;
import com.stickynotes.service.NoteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/create-note")
    public ResponseDTO createNote(@RequestBody NoteDTO noteDTO) {
        return this.noteService.createNote(noteDTO);
    }
}
