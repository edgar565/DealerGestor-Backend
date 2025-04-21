/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.auth;

import com.dealergestor.dealergestorbackend.DealerGestorBackendManager;
import com.dealergestor.dealergestorbackend.auth.dto.AuthRequest;
import com.dealergestor.dealergestorbackend.auth.dto.AuthResponse;
import com.dealergestor.dealergestorbackend.auth.dto.RegisterRequest;
import com.dealergestor.dealergestorbackend.domain.entity.CompanyUser;
import com.dealergestor.dealergestorbackend.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final DealerGestorBackendManager dealerGestorBackendManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            DealerGestorBackendManager dealerGestorBackendManager,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.dealerGestorBackendManager = dealerGestorBackendManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // LOGIN
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        CompanyUser user = dealerGestorBackendManager.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken);
    }

    // REGISTRO DEL PRIMER ADMIN
    public AuthResponse registerAdmin(RegisterRequest request) {
        Optional<CompanyUser> existingUser = dealerGestorBackendManager.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        CompanyUser admin = new CompanyUser();
        admin.setUsername(request.getUsername());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setRole("ADMIN");
        admin.setEnabled(true);

        dealerGestorBackendManager.saveUser(admin);

        String jwtToken = jwtService.generateToken(admin);

        return new AuthResponse(jwtToken);
    }
}