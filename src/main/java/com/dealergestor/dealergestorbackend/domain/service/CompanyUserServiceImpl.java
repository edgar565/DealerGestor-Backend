/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.CompanyUserEntity;
import com.dealergestor.dealergestorbackend.domain.model.CompanyUser;
import com.dealergestor.dealergestorbackend.domain.repository.CompanyUserRepository;
import com.dealergestor.dealergestorbackend.utils.ModelMapperUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyUserServiceImpl implements CompanyUserService{

    private final CompanyUserRepository companyUserRepository;
    private final ModelMapperUtil modelMapperUtil;
    private final PasswordEncoder passwordEncoder;

    public CompanyUserServiceImpl(CompanyUserRepository companyUserRepository,
                                  ModelMapperUtil modelMapperUtil,
                                  PasswordEncoder passwordEncoder) {
        this.companyUserRepository = companyUserRepository;
        this.modelMapperUtil = modelMapperUtil;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<CompanyUser> findAllCompanyUsers() {
        return companyUserRepository.findAll().stream()
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyUser findCompanyUserById(Long id) {
        CompanyUserEntity e = companyUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CompanyUser not found"));
        return modelMapperUtil.toModel(e);
    }

    @Override
    public CompanyUser saveCompanyUser(CompanyUser companyUser) {
        CompanyUserEntity companyUserEntity = new CompanyUserEntity();
        companyUserEntity.setUsername(companyUser.getUsername());
        // Hashear contraseña
        companyUserEntity.setPassword(passwordEncoder.encode(companyUser.getPassword()));
        companyUserEntity.setRole(CompanyUserEntity.Role.valueOf(companyUser.getRole()));
        companyUserEntity.setEnabled(true);
        CompanyUserEntity saved = companyUserRepository.save(companyUserEntity);
        return modelMapperUtil.toModel(saved);
    }

    @Override
    public CompanyUser updateCompanyUser(Long id, CompanyUser companyUser) {
        CompanyUserEntity companyUserEntity = companyUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CompanyUser not found"));

        companyUserEntity.setUsername(companyUser.getUsername());

        if (companyUser.getPassword() != null) {
            companyUserEntity.setPassword(passwordEncoder.encode(companyUser.getPassword()));
        }

        companyUserEntity.setRole(CompanyUserEntity.Role.valueOf(companyUser.getRole()));
        companyUserEntity.setEnabled(companyUser.isEnabled());
        return modelMapperUtil.toModel(companyUserRepository.save(companyUserEntity));
    }

    @Override
    public void deleteCompanyUser(Long id) {
        companyUserRepository.deleteById(id);
    }

    @Override
    public CompanyUser findByUsername(String username){
        return modelMapperUtil.toModel(companyUserRepository.findByUsername(username));
    }
}