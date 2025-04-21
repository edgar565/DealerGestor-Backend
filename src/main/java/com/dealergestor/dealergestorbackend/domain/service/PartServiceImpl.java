/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.domain.service;

import com.dealergestor.dealergestorbackend.domain.entity.Part;
import com.dealergestor.dealergestorbackend.domain.entity.Repair;
import com.dealergestor.dealergestorbackend.domain.model.PartModel;
import com.dealergestor.dealergestorbackend.domain.repository.PartRepository;
import com.dealergestor.dealergestorbackend.domain.repository.RepairRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartServiceImpl implements PartService{

    private final PartRepository partRepository;
    private final RepairRepository repairRepository;
    private final ModelMapperUtil modelMapperUtil;

    public PartServiceImpl(PartRepository partRepository, RepairRepository repairRepository, ModelMapperUtil modelMapperUtil) {
        this.partRepository = partRepository;
        this.repairRepository = repairRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    @Override
    public List<PartModel> findAll() {
        return partRepository.findAll().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public PartModel findById(Long id) {
        Part part = partRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Part not found"));
        return modelMapperUtil.toModel(part);
    }

    @Override
    public PartModel create(PartModel model) {
        Repair repair = repairRepository.findById(model.getRepairId())
                .orElseThrow(() -> new RuntimeException("Repair not found"));

        Part part = new Part();
        part.setKeychain(model.getKeychain());
        part.setNumberOrder(model.getNumberOrder());
        part.setWork(model.getWork());
        part.setMaterials(model.getMaterials());
        part.setObservations(model.getObservations());
        part.setLights(model.getLights());
        part.setBrakeSensors(model.getBrakeSensors());
        part.setCableTension(model.getCableTension());
        part.setTirePressure(model.getTirePressure());
        part.setEngineOil(model.getEngineOil());
        part.setWear(model.getWear());
        part.setBrakeFluid(model.getBrakeFluid());
        part.setBrakePads(model.getBrakePads());
        part.setTransmissionKit(model.getTransmissionKit());
        part.setSteeringCondition(model.getSteeringCondition());
        part.setDynamicTest(model.getDynamicTest());

        part.setDay1(model.getDay1());
        part.setDay2(model.getDay2());
        part.setDay3(model.getDay3());
        part.setDay4(model.getDay4());
        part.setDay5(model.getDay5());
        part.setDay6(model.getDay6());

        part.setTime1(model.getTime1());
        part.setTime2(model.getTime2());
        part.setTime3(model.getTime3());
        part.setTime4(model.getTime4());
        part.setTime5(model.getTime5());
        part.setTime6(model.getTime6());
        part.setTime7(model.getTime7());
        part.setTime8(model.getTime8());
        part.setTime9(model.getTime9());
        part.setTime10(model.getTime10());
        part.setTime11(model.getTime11());
        part.setTime12(model.getTime12());
        part.setKeychain(model.getKeychain());
        part.setRepair(repair);

        return modelMapperUtil.toModel(partRepository.save(part));
    }

    @Override
    public PartModel update(Long id, PartModel model) {
        Part part = partRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Part not found"));

        Repair repair = repairRepository.findById(model.getRepairId())
                .orElseThrow(() -> new RuntimeException("Repair not found"));

        part.setName(model.getName());
        part.setBrand(model.getBrand());
        part.setPrice(model.getPrice());
        part.setKeychain(model.getKeychain());
        part.setRepair(repair);

        return modelMapperUtil.toModel(partRepository.save(part));
    }

    @Override
    public void delete(Long id) {
        partRepository.deleteById(id);
    }
}