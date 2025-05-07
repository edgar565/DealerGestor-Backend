/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.CompanyUserEntity;
import com.dealergestor.dealergestorbackend.domain.model.CompanyUser;
import org.springframework.stereotype.Service;

@Service
public class CompanyUserServiceImpl implements CompanyUserService{

    public CompanyUser toModel(CompanyUserEntity entity) {
        return new CompanyUser(entity.getCompanyUserId(), entity.getUsername(), entity.getPassword(), entity.getRole(), entity.isEnabled(), entity.getNoteEntities());
    }
}