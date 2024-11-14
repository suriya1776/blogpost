package com.org.blogpost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.blogpost.dto.LoginDTO;
import com.org.blogpost.dto.UserDTO;
import com.org.blogpost.exception.UserLockedException;
import com.org.blogpost.repository.UserRepository;
import com.org.blogpost.security.JWTUtil;
import com.org.blogpost.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private JWTUtil jwtUtil;

	@PostMapping("/signup")
	public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
		try {
			userService.registerUser(userDTO.getUsername(), userDTO.getPassword());
			return ResponseEntity.ok("User registered successfully");
		} catch (Exception e) {
			// Return a bad request with the error message
			return ResponseEntity.badRequest().body("Error: " + e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO) {
		try {
			boolean isAuthenticated = userService.authenticateUser(loginDTO.getUsername(), loginDTO.getPassword());
			if (isAuthenticated) {
				// Generate a JWT token with 2-minute expiration
				String token = jwtUtil.generateToken(loginDTO.getUsername());
				return ResponseEntity.ok("Login successful. Token: " + token);
			} else {
				return ResponseEntity.badRequest().body("Invalid username or password.");
			}
		} catch (UserLockedException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account locked. Please contact support.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
		}
	}

	@PostMapping("/unlockuser")
	public ResponseEntity<String> unlockUser(@RequestParam String username) {
		try {
			boolean isUnlocked = userService.unlockUser(username);
			if (isUnlocked) {
				return ResponseEntity.ok("User account unlocked successfully.");
			} else {
				return ResponseEntity.badRequest().body("User not found or account is already unlocked.");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while unlocking the user.");
		}
	}

}
