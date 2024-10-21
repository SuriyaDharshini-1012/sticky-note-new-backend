package com.stickynotes.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignInDTO
{

    private String email;
    private String password;

}
