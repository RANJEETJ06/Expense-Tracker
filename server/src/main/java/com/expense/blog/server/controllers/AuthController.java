package com.expense.blog.server.controllers;

import com.expense.blog.server.payloads.ApiResponse;
import com.expense.blog.server.payloads.LoginDTO;
import com.expense.blog.server.payloads.UserDto;
import com.expense.blog.server.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@EnableMethodSecurity
public class AuthController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @PostMapping("/login/")
    @PreAuthorize("hasAuthority('PUBLIC')")
    public ResponseEntity<ApiResponse> signIn( @RequestBody LoginDTO loginDTO) {
        String username = loginDTO.getEmail();
        String password = loginDTO.getPassword();
        UserDto user= this.modelMapper.map(this.userRepo.findByEmail(username),UserDto.class);
        if (user != null) {
            if (this.passwordEncoder.matches(password,user.getPassword())) {
                return new ResponseEntity<>(new ApiResponse("Login successful", true,user.getUserId()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse("Incorrect password", false,null), HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse("User not found",false,null), HttpStatus.FORBIDDEN);
        }
    }
}
