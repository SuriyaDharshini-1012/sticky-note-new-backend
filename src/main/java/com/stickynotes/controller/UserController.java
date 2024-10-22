package com.stickynotes.controller;

import com.stickynotes.dto.ResponseDTO;
import com.stickynotes.dto.SignInDTO;
import com.stickynotes.dto.SignUpDTO;
import com.stickynotes.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
public class UserController
{

    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService=userService;
    }



    @PostMapping("/sign-up")
    public ResponseDTO signUp(@RequestBody SignUpDTO signupDto){
        return this.userService.signUp(signupDto);
    }

    @PostMapping("/sign-in")
    public ResponseDTO signIn(@RequestBody SignInDTO signInDTO){
        return this.userService.signIn(signInDTO);
    }


}
