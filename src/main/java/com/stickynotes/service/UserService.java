package com.stickynotes.service;
import com.stickynotes.config.TokenProvider;
import com.stickynotes.dto.*;
import com.stickynotes.entity.User;
import com.stickynotes.exception.EmailNotFormattedException;
import com.stickynotes.exception.InvalidPasswordException;
import com.stickynotes.exception.PasswordNotMatchException;
import com.stickynotes.repository.UserRepository;
import com.stickynotes.util.Constants;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class   UserService implements UserDetailsService
{
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @Lazy
    public UserService(UserRepository userRepository, TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }


    public ResponseDTO signUp(SignUpDTO signUpDTO) {
        if (!emailValidation(signUpDTO.getEmail())) {
            throw new EmailNotFormattedException("Email not formatted");
        }

        if (!passwordValidation(signUpDTO.getPassword())) {
            throw new InvalidPasswordException("Password should contain more than 8 characters");
        }



        if (signUpDTO.getTermsAccepted() == null || !signUpDTO.getTermsAccepted()) {
            throw new IllegalArgumentException("You must accept the terms and conditions");
        }



        User user = User.builder()
                .firstName(signUpDTO.getFirstName())
                .lastName(signUpDTO.getLastName())
                .email(signUpDTO.getEmail())
                .password(new BCryptPasswordEncoder().encode(signUpDTO.getPassword()))
                .termsAccepted(signUpDTO.getTermsAccepted())
                .build();
        //System.out.println("Terms Accepted: " + signUpDTO.getIsTermsAccepted());


        return ResponseDTO.builder()
                .statusCode(200)
                .data(userRepository.save(user))
                .message(Constants.CREATED)
                .build();
    }

    public boolean emailValidation(String email) {

        if (email == null) {
            return false;
        }
        return Pattern.compile("^[a-z0-9+_.-]+@(gmail|yahoo|outlook|zoho)\\.com$").matcher(email).matches();
    }
    private boolean passwordValidation(String password) {
        String pass = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        return Pattern.compile(pass).matcher(password).matches();
    }
    public ResponseDTO refreshAccessToken(RefreshTokenDTO request) {
        try {
            String newAccessToken = tokenProvider.refreshAccessToken(request.getRefreshToken());
            String refreshToken = request.setRefreshToken(newAccessToken);
            return ResponseDTO.builder().message(Constants.CREATED).data(refreshToken).statusCode(200).build();
        } catch (Exception e) {

            return ResponseDTO.builder().message(Constants.NOT_FOUND).data("Invalid refresh token").statusCode(401).build();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public ResponseDTO signIn(SignInDTO signInDTO) throws AuthenticationException {

        UserDetails user = userRepository.findByEmail(signInDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email doesn't exist, please sign up"));

        var userNamePassword = new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword());

        var authorizedUser = authenticationManager.authenticate(userNamePassword);

        var accessToken = tokenProvider.generateAccessToken((User) authorizedUser.getPrincipal());
        var refreshToken = tokenProvider.generateRefreshToken((User) authorizedUser.getPrincipal());
        return ResponseDTO.builder()
                .message(Constants.RETRIVED)
                .data(new JwtDTO(accessToken, refreshToken))
                .statusCode(200)
                .build();
    }

    public ResponseDTO getUserDetail(String id) {
        return ResponseDTO.builder()
                .message(Constants.RETRIVED)
                .data(this.userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("USER ID NOT EXIST")))
                .statusCode(200)
                .build();
    }

}
