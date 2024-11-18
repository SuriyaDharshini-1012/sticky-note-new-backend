package com.stickynotes.service;

import com.stickynotes.dto.NoteDTO;
import com.stickynotes.dto.ResponseDTO;
import com.stickynotes.entity.Note;
import com.stickynotes.entity.User;
import com.stickynotes.exception.BadRequestServiceAlertException;
import com.stickynotes.repository.NoteRepository;
import com.stickynotes.repository.UserRepository;
import com.stickynotes.util.Constants;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class NoteService {
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public void deleteNote(final String id) {
        final Optional<Note> note = this.noteRepository.findById(id);
        System.out.println(id); // Make sure this prints the correct id

        if (note.isEmpty()) {
            throw new BadRequestServiceAlertException(Constants.NOT_FOUND);
        }

        this.noteRepository.deleteById(id);
    }


    public Note updateNote(String id, final NoteDTO noteDTO)
    {
        final Optional<Note> optionalNote = this.noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            if (noteDTO.getTitle() != null) {
                note.setTitle(noteDTO.getTitle());
            }
            if (noteDTO.getContent() != null) {
                note.setContent(noteDTO.getContent());
            }
            if(noteDTO.getColour() !=null)
            {
                note.setColour(noteDTO.getColour());
            }
            if(noteDTO.getIsPinned() !=null)
            {
                note.setIsPinned(noteDTO.getIsPinned());
            }
            return this.noteRepository.save(note);
        }
        else
        {
            throw new BadRequestServiceAlertException("Note with id"+ id + "not found");
        }
    }

    public ResponseDTO createNote(String title, String content, String colour, Boolean isPinned, String userId) {
        System.err.println("userId = "+userId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            Note note = Note.builder()
                    .title(title)
                    .content(content)
                    .colour(colour)
                    .isPinned(isPinned)
                    .user(user)
                    .build();


            return new ResponseDTO(Constants.CREATED,noteRepository.save(note),200);

            }
        return new ResponseDTO(Constants.NOT_FOUND,null,200);

      }

    public ResponseDTO retrieveNoteById(String id) {
        Optional<Note> noteOptional = noteRepository.findById(id);
        if (noteOptional.isPresent()) {
            return new ResponseDTO(Constants.RETRIVED, noteOptional.get(),200);
        } else {
            return new ResponseDTO(Constants.NOT_FOUND, null,400);
        }
    }



}



