/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.repository;

import com.dealergestor.dealergestorbackend.domain.entity.CompanyUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUserEntity, Long> {

    CompanyUserEntity findByUsername(String username);
}