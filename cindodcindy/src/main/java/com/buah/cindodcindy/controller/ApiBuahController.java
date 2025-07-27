package com.buah.cindodcindy.controller;

import com.buah.cindodcindy.model.Buah;
import com.buah.cindodcindy.model.User;
import com.buah.cindodcindy.security.JwtUtil;
import com.buah.cindodcindy.service.BuahService;
import com.buah.cindodcindy.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/buah")
public class ApiBuahController {
  @Autowired
  private BuahService buahService;
  @Autowired private UserService userService;
  @Autowired private JwtUtil jwtUtil;

  private User getAuthenticatedUser(HttpServletRequest request) {
    String token = request.getHeader("Authorization").substring(7);
    String username = jwtUtil.extractUsername(token);
    return userService.findByUsername(username).orElse(null);
  }

  @PostMapping
  public ResponseEntity<?> addBuah(@RequestBody Buah buah, HttpServletRequest request) {
    User user = getAuthenticatedUser(request);
    buah.setUser(user);
    return ResponseEntity.ok(buahService.save(buah));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateBuah(@PathVariable Long id, @RequestBody Buah update, HttpServletRequest request) {
    User user = getAuthenticatedUser(request);
    Buah existing = buahService.findById(id).orElse(null);
    if (existing == null || !existing.getUser().getId().equals(user.getId())) {
      return ResponseEntity.status(403).build();
    }
    existing.setNama(update.getNama());
    return ResponseEntity.ok(buahService.save(existing));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteBuah(@PathVariable Long id, HttpServletRequest request) {
    User user = getAuthenticatedUser(request);
    Buah buah = buahService.findById(id).orElse(null);
    if (buah == null || !buah.getUser().getId().equals(user.getId())) {
      return ResponseEntity.status(403).build();
    }
    buahService.softDelete(buah);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<?> listBuah(HttpServletRequest request) {
    User user = getAuthenticatedUser(request);
    return ResponseEntity.ok(buahService.findByUser(user));
  }
}
