/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.auth;

import com.dealergestor.dealergestorbackend.auth.dto.AuthRequest;
import com.dealergestor.dealergestorbackend.auth.dto.AuthResponse;
import com.dealergestor.dealergestorbackend.auth.dto.RegisterRequest;
import com.dealergestor.dealergestorbackend.domain.entity.CompanyUserEntity;
import com.dealergestor.dealergestorbackend.domain.repository.CompanyUserRepository;
import com.dealergestor.dealergestorbackend.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

    private final CompanyUserRepository companyUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(CompanyUserRepository companyUserRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.companyUserRepository = companyUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        CompanyUserEntity user = new CompanyUserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setEnabled(true);
        companyUserRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        CompanyUserEntity user = companyUserRepository.findByUsername(request.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}