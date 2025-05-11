/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.CompanyEntity;
import com.dealergestor.dealergestorbackend.domain.model.Company;
import com.dealergestor.dealergestorbackend.domain.repository.CompanyRepository;
import com.dealergestor.dealergestorbackend.storage.FileStorageService;
import com.dealergestor.dealergestorbackend.utils.ModelMapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapperUtil modelMapperUtil;
    private final FileStorageService fileStorageService;

    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapperUtil modelMapperUtil, FileStorageService fileStorageService) {
        this.companyRepository = companyRepository;
        this.modelMapperUtil = modelMapperUtil;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public Company updateCompanyLogo(Long companyId, MultipartFile logoFile) {
        // 1) Sube el logo y obtiene la URL
        String logoUrl = fileStorageService.storeFile(logoFile, "company-logos/" + companyId);

        // 2) Carga la empresa
        CompanyEntity company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found: " + companyId));

        // 3) Actualiza el campo logoPath
        company.setLogoPath(logoUrl);
        CompanyEntity saved = companyRepository.save(company);

        // 4) Devuelve el modelo
        return modelMapperUtil.toModel(saved);
    }

}