package com.stickynotes.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpDTO
{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean termsAccepted; 
}
