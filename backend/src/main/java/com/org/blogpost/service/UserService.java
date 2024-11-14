package com.org.blogpost.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.org.blogpost.exception.UserLockedException;
import com.org.blogpost.model.User;
import com.org.blogpost.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User registerUser(String username, String rawPassword) throws Exception {


		// Validate the username
		if (!isValidUsername(username)) {
			throw new Exception(
					"Username must be at least 10 characters long, cannot contain spaces or special characters.");
		}

		// Check if the username already exists in the database
		if (userRepository.existsByUsername(username)) {
			throw new Exception("Username already exists");
		}

		if (!isValidPassword(rawPassword)) {
			throw new IllegalArgumentException(
					"Password must be between 6 and 15 characters and contain at least one uppercase letter, one digit, and one special character.");
		}

		// Create the new user
		User user = new User();
		user.setUsername(username);

		// Encrypt the raw password before saving
		user.setPassword(passwordEncoder.encode(rawPassword));
		user.setDateCreated(LocalDateTime.now());
		user.setPasswordExpiryDate(LocalDateTime.now().plusMonths(3));

		// Save the user to the repository
		return userRepository.save(user);
	}

	// Helper method to validate the username
	private boolean isValidUsername(String username) {
		// Check if username length is between 4 and 15 characters
		if (username.length() < 4 || username.length() > 15) {
			return false;
		}
		// Regular expression to allow only alphanumeric characters (no spaces or
		// special characters)
		return username.matches("^[a-zA-Z0-9]+$"); // Must be alphanumeric (no spaces or special characters)
	}

	// Helper method to validate the password
	private boolean isValidPassword(String password) {
		// Check if password length is between 6 and 15 characters
		if (password.length() < 6 || password.length() > 15) {
			return false;
		}
		// Regex to ensure password contains at least one uppercase letter, one digit,
		// and one special character
		String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,15}$";
		return password.matches(regex);
	}

	// Lock a user account
	public void lockUser(String username) {
		Optional<User> userOptional = userRepository.findByUsername(username);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			user.setIsUserLocked(true); // Lock the account
			userRepository.save(user); // Persist the change
		}
	}


	public boolean authenticateUser(String username, String rawPassword) {
		// Fetch the user from the database by username
		Optional<User> userOptional = userRepository.findByUsername(username);
		if (userOptional.isEmpty()) {
			return false; // User not found
		}

		User user = userOptional.get();

		// 1. Check if the user account is locked
		if (user.getIsUserLocked()) {
			throw new UserLockedException("User account is locked due to too many failed login attempts.");
		}

		// 2. Check if the user's password has expired
		if (user.getPasswordExpiryDate().isBefore(LocalDateTime.now())) {
			return false; // Password has expired
		}

		// 3. Check if the user has failed too many login attempts
		if (user.getFailedLoginAttempts() >= 5) {
			System.out.println("User locked");
			user.setIsUserLocked(true); // Lock the user after 5 failed attempts
			userRepository.save(user); // Persist the lock status in the database
			throw new UserLockedException("User account is locked due to too many failed login attempts.");
		}

		// 4. Check if the password matches
		if (passwordEncoder.matches(rawPassword, user.getPassword())) {
			// Reset failed login attempts if login is successful
			user.setFailedLoginAttempts(0);
			userRepository.save(user); // Persist the updated failed login attempts
			return true; // Successful login
		}

		// 5. Increment failed login attempts on invalid password
		user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
		userRepository.save(user); // Persist the updated failed login attempts

		return false; // Invalid password
	}



	public boolean unlockUser(String username) {
		// Fetch the user from the database by username
		Optional<User> userOptional = userRepository.findByUsername(username);
		if (userOptional.isEmpty()) {
			return false; // User not found
		}

		User user = userOptional.get();

		// Reset the isUserLocked flag and failedLoginAttempts
		user.setIsUserLocked(false);
		user.setFailedLoginAttempts(0);

		// Save the updated user details back to the database
		userRepository.save(user);

		return true; // Account unlocked successfully
	}



}
