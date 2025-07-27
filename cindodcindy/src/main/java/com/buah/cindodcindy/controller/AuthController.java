package com.buah.cindodcindy.controller;

import com.buah.cindodcindy.model.User;
import com.buah.cindodcindy.repository.UserRepository;
import com.buah.cindodcindy.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {
  @Autowired
  JwtUtil jwt;

  @Autowired
  private UserRepository userRepository;

  // ✅ REGISTER
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody Map<String, String> request) {
    String username = request.get("username");
    String password = request.get("password");

    if (userRepository.findByUsername(username).isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Username already exists");
    }

    User user = new User();
    user.setUsername(username);
    user.setPassword(password); // ⚠ In production, use BCrypt hashing
    userRepository.save(user);

    return ResponseEntity.ok("User registered successfully");
  }

  // ✅ LOGIN (check from DB)
  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> u) {
    String username = u.get("username");
    String password = u.get("password");

    Optional<User> userOpt = userRepository.findByUsername(username);

    if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
      String token = jwt.generateToken(username);
      return ResponseEntity.ok(Map.of("token", token));
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
}
