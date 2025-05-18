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
        // 1) Sube el logo y obtiene la URL
//        String logoUrl = fileStorageService.storeFile(logoFile, "company-logos/" + companyConfiguration.getCompanyId());

        // 2) Carga la empresa
        CompanyConfigurationEntity company = companyConfigurationRepository.findById(companyConfiguration.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found: " + companyConfiguration.getCompanyId()));

        // 3) Actualiza el campo logoPath

//        company.setLogoPath(logoUrl);
        company.setNameCompany(companyConfiguration.getNameCompany());
        company.setPrimaryColor(companyConfiguration.getPrimaryColor());
        company.setSecondaryColor(companyConfiguration.getSecondaryColor());
        company.setWhatsappApiKey(companyConfiguration.getWhatsappApiKey());
        CompanyConfigurationEntity saved = companyConfigurationRepository.save(company);

        // 4) Devuelve el modelo
        return modelMapperUtil.toModel(saved);
    }

}