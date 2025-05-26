package com.dealergestor.dealergestorbackend.config;

import com.dealergestor.dealergestorbackend.domain.entity.CompanyConfigurationEntity;
import com.dealergestor.dealergestorbackend.domain.entity.CompanyUserEntity;
import com.dealergestor.dealergestorbackend.domain.repository.CompanyConfigurationRepository;
import com.dealergestor.dealergestorbackend.domain.repository.CompanyUserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Initializer {

    private final CompanyUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyConfigurationRepository configurationRepository;

    public Initializer(CompanyUserRepository userRepository, PasswordEncoder passwordEncoder, CompanyConfigurationRepository companyConfigurationRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.configurationRepository = companyConfigurationRepository;
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

    @PostConstruct
    public void initDefaultConfiguration() {
        if (configurationRepository.count() == 0) {
            CompanyConfigurationEntity defaultConfig = new CompanyConfigurationEntity();
            defaultConfig.setNameCompany("DealerGestor");
            defaultConfig.setPrimaryColor("#BD1522");
            defaultConfig.setSecondaryColor("#6f6f6f");
            defaultConfig.setLogoPath("public/DealerGestor-logo_edited.jpg");

            configurationRepository.save(defaultConfig);
            System.out.println("✅ Default company configuration created in @PostConstruct");
        }
    }
}