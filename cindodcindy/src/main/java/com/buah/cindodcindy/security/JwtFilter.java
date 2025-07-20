package com.buah.cindodcindy.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
  @Autowired
  JwtUtil jwt;
  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws ServletException, IOException {
    String h = req.getHeader("Authorization");
    if (h!=null && h.startsWith("Bearer ")) {
      String t = h.substring(7);
      if (jwt.validateToken(t)) {
        String u = jwt.extractUsername(t);
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(u, null, List.of())
        );
      }
    }
    chain.doFilter(req, res);
  }
}
