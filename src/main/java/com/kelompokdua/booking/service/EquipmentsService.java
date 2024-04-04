package com.kelompokdua.booking.service;


import com.kelompokdua.booking.entity.Equipments;
import com.kelompokdua.booking.model.request.EquipmentsRequest;
import com.kelompokdua.booking.model.response.EquipmentsResponse;
import org.springframework.data.domain.Page;

public interface EquipmentsService {

    EquipmentsResponse createEquipment(EquipmentsRequest equipmentRequest);
    Page<Equipments> getAllEquipment(Integer page, Integer size);
    Equipments getEquipmentById(String id);
    Equipments updateEquipmentById(Equipments Equipment);
    void deleteEquipmentById(String id);
}
