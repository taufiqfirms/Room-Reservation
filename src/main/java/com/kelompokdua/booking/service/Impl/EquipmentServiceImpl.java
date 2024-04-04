package com.kelompokdua.booking.service.Impl;


import com.kelompokdua.booking.entity.Equipments;
import com.kelompokdua.booking.model.request.EquipmentsRequest;
import com.kelompokdua.booking.model.response.EquipmentsResponse;
import com.kelompokdua.booking.repository.EquipmentRequestRepository;
import com.kelompokdua.booking.service.EquipmentsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EquipmentServiceImpl implements EquipmentsService {

    private final EquipmentRequestRepository equipmentRequestRepository;

    @Override
    public EquipmentsResponse createEquipment(EquipmentsRequest equipmentsRequest) {
        Equipments newEquipments = Equipments.builder()
                .equipment(equipmentsRequest.getEquipment())
                .quantity(equipmentsRequest.getQuantity())
                .price(equipmentsRequest.getPrice())
                .build();
        Equipments saveEquipments = equipmentRequestRepository.saveAndFlush(newEquipments);
        return EquipmentsResponse.builder()
                .equipment(saveEquipments.getEquipment())
                .quantity(saveEquipments.getQuantity())
                .price(saveEquipments.getPrice())
                .build();
    }

    @Override
    public Page<Equipments> getAllEquipment(Integer page, Integer size) {
        if (page <= 0 ) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page-1,size);
        return equipmentRequestRepository.findAll(pageable);
    }

    @Override
    public Equipments getEquipmentById(String id) {
        Optional<Equipments> optionalEquipments = equipmentRequestRepository.findById(id);
        if (optionalEquipments.isPresent()) {
            return optionalEquipments.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipment ID Not Found");
    }

    @Override
    public Equipments updateEquipmentById(Equipments equipments) {
        this.getEquipmentById(equipments.getId());
        return equipmentRequestRepository.save(equipments);
    }

    @Override
    public void deleteEquipmentById(String id) {
        this.getEquipmentById(id);
        equipmentRequestRepository.deleteById(id);

    }
}
