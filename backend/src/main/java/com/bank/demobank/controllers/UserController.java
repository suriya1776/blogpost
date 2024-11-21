package com.bank.demobank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.demobank.dto.LoginDTO;
import com.bank.demobank.dto.UserDTO;
import com.bank.demobank.exceptions.ConfirmPasswordException;
import com.bank.demobank.exceptions.EmailAlreadyTakenException;
import com.bank.demobank.exceptions.UserNotFoundException;
import com.bank.demobank.exceptions.UsernameAlreadyTakenException;
import com.bank.demobank.model.User;
import com.bank.demobank.repositories.UserRepository;
import com.bank.demobank.service.UserService;
import com.bank.demobank.utils.JwtTokenUtil;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    //Confiuring a logger
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
   
    private final ObjectMapper objectMapper = new ObjectMapper(); 

    //password encoder from security config


    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO){
  
    logger.debug("Register api is triggered");
    try{

        logger.info("Route =  api/users/register");
        //String userDTOJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString();
        //logger.info("Request body:\n{}", userDTOJson);
   
        userService.registerUser(userDTO);
        logger.info("User created successfully "+userDTO.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");

    }
    catch(UsernameAlreadyTakenException e){

        return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");

    }

    catch (EmailAlreadyTakenException e){
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
    

    }

    catch(ConfirmPasswordException e){

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Password does not match");
    }
    catch(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }


    
     
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid LoginDTO loginDTO){

    try{

     
     User user =  userRepository.findByUsername(loginDTO.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));

     if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
        throw new BadCredentialsException("Incorrect password");
     }


     String token = jwtTokenUtil.generateToken(user.getUsername());

     return ResponseEntity.ok().body("User logged in Token = "+token);

    }

    catch (UserNotFoundException | BadCredentialsException e){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or password is incorrect");
    }

    catch(Exception e){

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");

    }

    }

    @GetMapping("/test")
    public ResponseEntity<String> testRoute() {
        return ResponseEntity.ok("Token is valid! You have accessed the protected route.");
    }


}
