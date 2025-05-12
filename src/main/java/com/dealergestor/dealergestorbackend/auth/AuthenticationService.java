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
import com.dealergestor.dealergestorbackend.domain.repository.CompanyConfigurationUserRepository;
import com.dealergestor.dealergestorbackend.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

    private final CompanyConfigurationUserRepository companyConfigurationUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(CompanyConfigurationUserRepository companyConfigurationUserRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.companyConfigurationUserRepository = companyConfigurationUserRepository;
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
        companyConfigurationUserRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        CompanyUserEntity user = companyConfigurationUserRepository.findByUsername(request.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}