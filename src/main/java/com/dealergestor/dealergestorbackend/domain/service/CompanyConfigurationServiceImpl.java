/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.CompanyConfigurationEntity;
import com.dealergestor.dealergestorbackend.domain.model.CompanyConfiguration;
import com.dealergestor.dealergestorbackend.domain.repository.CompanyConfigurationRepository;
import com.dealergestor.dealergestorbackend.storage.FileStorageService;
import com.dealergestor.dealergestorbackend.utils.ModelMapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CompanyConfigurationServiceImpl implements CompanyConfigurationService {

    private final CompanyConfigurationRepository companyConfigurationRepository;
    private final ModelMapperUtil modelMapperUtil;
    private final FileStorageService fileStorageService;


    public CompanyConfigurationServiceImpl(CompanyConfigurationRepository companyConfigurationRepository, ModelMapperUtil modelMapperUtil, FileStorageService fileStorageService) {
        this.companyConfigurationRepository = companyConfigurationRepository;
        this.modelMapperUtil = modelMapperUtil;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public CompanyConfiguration findCompanyConfiguration(Long id){
        CompanyConfigurationEntity companyConfigurationEntity = companyConfigurationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CompanyConfiguration not found"));
        return modelMapperUtil.toModel(companyConfigurationEntity);
    }

    @Override
    public CompanyConfiguration updateCompanyConfiguration(CompanyConfiguration companyConfiguration, MultipartFile logoFile) {
        String logoUrl = fileStorageService.storeFile(logoFile, String.valueOf(companyConfiguration.getCompanyId()));

        CompanyConfigurationEntity company = companyConfigurationRepository.findById(companyConfiguration.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found: " + companyConfiguration.getCompanyId()));

        company.setLogoPath(logoUrl);
        company.setNameCompany(companyConfiguration.getNameCompany());
        company.setPrimaryColor(companyConfiguration.getPrimaryColor());
        company.setSecondaryColor(companyConfiguration.getSecondaryColor());

        CompanyConfigurationEntity saved = companyConfigurationRepository.save(company);
        return modelMapperUtil.toModel(saved);
    }
}