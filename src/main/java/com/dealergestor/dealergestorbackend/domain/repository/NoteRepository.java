/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.repository;


import com.dealergestor.dealergestorbackend.domain.entity.CompanyUserEntity;
import com.dealergestor.dealergestorbackend.domain.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    List<NoteEntity> findByCompanyUserEntity(CompanyUserEntity companyUserEntity);
}