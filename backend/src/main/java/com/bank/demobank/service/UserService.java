package com.bank.demobank.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.bank.demobank.dto.UserDTO;
import com.bank.demobank.enums.Role;
import com.bank.demobank.exceptions.ConfirmPasswordException;
import com.bank.demobank.exceptions.EmailAlreadyTakenException;
import com.bank.demobank.exceptions.UsernameAlreadyTakenException;
import com.bank.demobank.model.User;
import com.bank.demobank.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private  PasswordEncoder passwordEncoder;




    public User registerUser(UserDTO userDTO) throws Exception{
      

          // Validate role input
        
        Optional<User> existingUser = userRepository.findByUsername(userDTO.getUsername());
        if(existingUser.isPresent()){

            throw  new UsernameAlreadyTakenException("user already exists");

        }

        Optional<User> existingEmail = userRepository.findByEmail(userDTO.getEmail());



        if(existingEmail.isPresent()){
            throw new EmailAlreadyTakenException("Email address already taken");
        }




        if(!userDTO.getPassword().equals(userDTO.getConfirmpassword())){
          throw new ConfirmPasswordException("Password does not match");
        }

        
        // Convert role string to enum (case insensitive)
        Role role = userDTO.getRole();  // toUpperCase on String

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRole(role);  // Set the validated role

        return userRepository.save(user);


    }


    
}
