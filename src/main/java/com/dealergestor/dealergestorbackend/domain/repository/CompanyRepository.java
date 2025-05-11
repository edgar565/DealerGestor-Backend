/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.repository;

import com.dealergestor.dealergestorbackend.domain.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

}