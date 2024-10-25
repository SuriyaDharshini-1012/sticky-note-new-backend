package com.stickynotes.dto;

import com.stickynotes.entity.User;
import lombok.*;

@Builder
@Data
public class NoteDTO {
    private String title;
    private String content;
    private String colour;
    private Boolean isPinned;
    private User user;
}