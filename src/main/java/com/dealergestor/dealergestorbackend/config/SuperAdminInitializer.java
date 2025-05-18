package com.dealergestor.dealergestorbackend.config;

import com.dealergestor.dealergestorbackend.domain.entity.CompanyUserEntity;
import com.dealergestor.dealergestorbackend.domain.repository.CompanyUserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SuperAdminInitializer {

    private final CompanyUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SuperAdminInitializer(CompanyUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initSuperAdmin() {
        if (userRepository.findByUsername("dealergestor") == null) {
            CompanyUserEntity superAdmin = new CompanyUserEntity();
            superAdmin.setUsername("dealergestor");
            superAdmin.setPassword(passwordEncoder.encode("supersecurepassword"));
            superAdmin.setRole(CompanyUserEntity.Role.SUPER_ADMIN);
            superAdmin.setEnabled(true);
            userRepository.save(superAdmin);
            System.out.println("✅ SuperAdmin created in @PostConstruct");
        }
    }
}