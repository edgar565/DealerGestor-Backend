package com.dealergestor.dealergestorbackend.domain;

import com.dealergestor.dealergestorbackend.domain.entity.CompanyUserEntity;
import com.dealergestor.dealergestorbackend.domain.entity.RepairEntity;
import com.dealergestor.dealergestorbackend.domain.entity.VehicleEntity;
import com.dealergestor.dealergestorbackend.domain.model.CompanyUser;
import com.dealergestor.dealergestorbackend.domain.model.Repair;
import com.dealergestor.dealergestorbackend.domain.model.Vehicle;
import com.dealergestor.dealergestorbackend.domain.repository.CompanyUserRepository;
import com.dealergestor.dealergestorbackend.domain.repository.RepairRepository;
import com.dealergestor.dealergestorbackend.domain.repository.VehicleRepository;
import com.dealergestor.dealergestorbackend.domain.service.RepairService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RepairServiceTest {

    @Mock
    private RepairRepository repairRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private CompanyUserRepository companyUserRepository;

    @InjectMocks
    private RepairService repairService;

    @Test
    void testSaveRepair_Success() {
        // Arrange
        Long vehicleId = 1L;
        Long operatorId = 2L;
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleId(vehicleId);
        CompanyUser operator = new CompanyUser();
        operator.setCompanyUserId(operatorId);
        Repair repair = new Repair();
        repair.setVehicle(vehicle);
        repair.setOperator(operator);
        repair.setStatus("EN_REPARACION");

        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setVehicleId(vehicleId);

        CompanyUserEntity companyUserEntity = new CompanyUserEntity();

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.<VehicleEntity>of(vehicleEntity));
        when(companyUserRepository.findById(operatorId)).thenReturn(Optional.<CompanyUserEntity>of(companyUserEntity));
        when(repairRepository.existsByVehicle(vehicleEntity)).thenReturn(false);
        when(repairRepository.save(any(RepairEntity.class))).thenReturn(new RepairEntity());

        // Act
        Repair result = repairService.saveRepair(repair);

        // Assert
        assertNotNull(result);
        verify(repairRepository).save(any(RepairEntity.class));
    }
}