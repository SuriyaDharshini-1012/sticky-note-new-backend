package com.stickynotes.repository;

import com.stickynotes.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note,String>
{

}
