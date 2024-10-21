package com.stickynotes.dto;

import lombok.Data;

@Data
public class SignUpDTO
{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
    private Boolean isTermsAccepted;
}
