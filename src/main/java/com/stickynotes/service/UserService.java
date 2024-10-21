package com.stickynotes.service;
import com.stickynotes.dto.ResponseDTO;
import com.stickynotes.dto.SignUpDTO;
import com.stickynotes.dto.UserDTO;
import com.stickynotes.entity.User;
import com.stickynotes.repository.UserRepository;
import com.stickynotes.util.Constants;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UserRepository userRepository;
//    private final AuthenticationManager authenticationManager;
//    private final TokenProvider tokenProvider;
//    private final PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository)
    {
        this.userRepository=userRepository;
//        this.authenticationManager=authenticationManger;
//        this.tokenProvider=tokenProvider;
//        this.passwordEncoder=passwordEncoder;

    }

    public ResponseDTO createUser(final UserDTO userDTO)
    {
        User user=User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .confirmPassword(userDTO.getConfirmPassword())
                .termsAccepted(userDTO.getTermsAccepted())
                .build();
          user=this.userRepository.save(user);
          return ResponseDTO.builder().statusCode(200)
                .data(user)
                .message(Constants.CREATED).build();
    }

//    public ResponseDTO signUp(SignUpDTO signUpDTO)
//    {
////        String encodedPassword=new ByCryptPasswordEncoder().encode(signUpDTO.getPassword())
//
//        if(!emailValidation(signUpDTO.getEmail())) {
//            throw new EmailNotFormattedException("Email not formatted");
//        }
//
//        if(!passwordValidation(signUpDTO.getPassword())) {
//            throw new InvaildPasswordException("password should contain more then 8 charactors ");
//        }
//        if(!signUpDTO.getPassword().equals(signUpDTO.getConfirmationPassword())){
//            throw new PasswordNotMatchException("Password and Confirmation password doesn't match");
//        }
//        User user = User.builder()
//                .email(signUpDTO.getEmail())
//                .password(new BCryptPasswordEncoder().encode(signUpDTO.getPassword()))
//                .confirmationPassword(signUpDTO.getConfirmationPassword())
//                .build();
//
//        return  ResponseDTO.builder().statusCode(200).data(userRepository.save(user)).message(Constants.CREATED).build();
//    }
}
