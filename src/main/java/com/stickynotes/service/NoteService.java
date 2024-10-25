package com.stickynotes.service;


import com.stickynotes.dto.NoteDTO;
import com.stickynotes.dto.ResponseDTO;
import com.stickynotes.entity.Note;
import com.stickynotes.entity.User;
import com.stickynotes.exception.BadRequestServiceAlertException;
import com.stickynotes.repository.NoteRepository;
import com.stickynotes.repository.UserRepository;
import com.stickynotes.util.Constants;
import org.springframework.stereotype.Service;



@Service
public class NoteService {
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }



    public ResponseDTO createNote(final NoteDTO noteDTO)
    {

        User user=userRepository.findById(noteDTO.getUser().getId())
                .orElseThrow(()->new BadRequestServiceAlertException(Constants.NOT_FOUND));
        Note note=Note.builder()
                .title(noteDTO.getTitle())
                .colour(noteDTO.getColour())
                .content(noteDTO.getContent())
                .isPinned(noteDTO.getIsPinned())
                .user(user)
                .build();

        noteRepository.save(note);
            return new ResponseDTO(Constants.CREATED, note, 200);
        }
    }



