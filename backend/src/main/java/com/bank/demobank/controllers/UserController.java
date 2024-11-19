package com.bank.demobank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.demobank.dto.UserDTO;
import com.bank.demobank.exceptions.EmailAlreadyTakenException;
import com.bank.demobank.exceptions.UsernameAlreadyTakenException;
import com.bank.demobank.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;


    //Confiuring a logger
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
   
    private final ObjectMapper objectMapper = new ObjectMapper(); 

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
    catch(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }


    
     
    }


}
