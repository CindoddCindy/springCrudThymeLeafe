package com.buah.cindodcindy.controller;

import com.buah.cindodcindy.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {
  @Autowired
  JwtUtil jwt;
  @PostMapping("/login")
  public ResponseEntity<Map<String,String>> login(@RequestBody Map<String,String> u){
    if ("admin".equals(u.get("username")) && "admin123".equals(u.get("password"))) {
      return ResponseEntity.ok(Map.of("token", jwt.generateToken(u.get("username"))));
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
}
