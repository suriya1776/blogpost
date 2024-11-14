package com.org.blogpost.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.blogpost.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);

	List<User> findByIsUserLocked(Boolean isUserLocked);

	List<User> findByPasswordExpiryDateBefore(LocalDateTime expiryDate);
}
