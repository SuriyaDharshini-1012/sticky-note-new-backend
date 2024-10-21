package com.stickynotes.controller;

import com.stickynotes.dto.ResponseDTO;
import com.stickynotes.dto.SignUpDTO;
import com.stickynotes.dto.UserDTO;
import com.stickynotes.service.UserService;
import com.stickynotes.util.Constants;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController
{

    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService=userService;
    }

    @PostMapping("/create-user")
    public ResponseDTO createUser(@RequestBody UserDTO userDTO)
    {
        return new ResponseDTO(Constants.CREATED,this.userService.createUser(userDTO),201);
    }



}
