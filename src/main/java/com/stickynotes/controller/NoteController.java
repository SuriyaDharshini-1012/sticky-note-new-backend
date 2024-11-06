package com.stickynotes.controller;

import com.stickynotes.config.TokenProvider;
import com.stickynotes.dto.NoteDTO;
import com.stickynotes.dto.ResponseDTO;
import com.stickynotes.entity.Note;
import com.stickynotes.exception.BadRequestServiceAlertException;
import com.stickynotes.service.NoteService;
import com.stickynotes.util.Constants;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
public class NoteController {
    private final NoteService noteService;
    private final TokenProvider tokenProvider;

    public NoteController(NoteService noteService,TokenProvider tokenProvider) {
        this.noteService = noteService;
        this.tokenProvider=tokenProvider;
    }

    @PostMapping("/create-note")
    public ResponseDTO createNote(@RequestParam String title,
                                  @RequestParam String content,
                                  @RequestParam String colour,
                                  @RequestParam Boolean isPinned,
                                  @RequestHeader("Authorization") String authorizationHeader) {


        System.err.println("Authorization Header: " + authorizationHeader);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization header format.");
        }


        String token = authorizationHeader.substring(7);

        String userId = tokenProvider.getUserIdFromToken(token);


        return this.noteService.createNote(title, content, colour, isPinned, userId);
    }


    //    @PostMapping("/create-note")
//    public ResponseDTO createNote(@RequestBody NoteDTO noteDTO) {
//        return this.noteService.createNote(noteDTO);
//    }
    @GetMapping("/notes")
    public List<Note> getAllNotes() {
        return this.noteService.getAllNotes();
    }

    @DeleteMapping("delete-note/{id}")
    public ResponseDTO deleteNote(@PathVariable final String id) {
        try {
            noteService.deleteNote(id); // Call the service to delete the note
            return new ResponseDTO(Constants.REMOVED, null, 200);
        } catch (BadRequestServiceAlertException e) {
            return new ResponseDTO(e.getMessage(), null, 400); // Handle errors
        }
    }

    @PutMapping("update-note/{id}")
    public ResponseDTO updateNote(@PathVariable String id, @RequestBody NoteDTO noteDTO) {
       return new ResponseDTO(Constants.UPDATED,this.noteService.updateNote(id,noteDTO),200);
    }
}
