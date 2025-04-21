/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.auth;

import com.dealergestor.dealergestorbackend.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public AuthenticationController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        // Aquí se valida el usuario (en UserService o un repositorio)
        if (userService.isValidUser(loginRequest.getUsername(), loginRequest.getPassword())) {
            return jwtService.generateToken(loginRequest.getUsername());
        }
        throw new UnauthorizedException("Invalid credentials");
    }
}