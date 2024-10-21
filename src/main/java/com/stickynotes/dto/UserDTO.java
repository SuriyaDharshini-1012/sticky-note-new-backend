package com.stickynotes.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO
{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private Boolean termsAccepted;
    private Long phoneNumber;
}
