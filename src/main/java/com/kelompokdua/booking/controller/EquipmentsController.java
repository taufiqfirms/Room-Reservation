package com.kelompokdua.booking.controller;


import com.kelompokdua.booking.entity.Equipments;
import com.kelompokdua.booking.model.request.EquipmentsRequest;
import com.kelompokdua.booking.model.request.EquipmentsSearchRequest;
import com.kelompokdua.booking.model.response.EquipmentsResponse;
import com.kelompokdua.booking.model.response.PagingResponse;
import com.kelompokdua.booking.model.response.WebResponse;
import com.kelompokdua.booking.service.EquipmentsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/equipment-request")
public class EquipmentsController{

    private final EquipmentsService equipmentsService;

    @PostMapping
    public ResponseEntity<WebResponse<EquipmentsResponse>> createdEquipment(
            @RequestBody EquipmentsRequest equipmentRequest) {
        EquipmentsResponse equipmentResponse = equipmentsService.createEquipment(equipmentRequest);
        WebResponse<EquipmentsResponse> response = WebResponse.<EquipmentsResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Equipment Has Created And Register")
                .data(equipmentResponse)
                .build();
        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<?> findEquipment(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String equipment,
            @RequestParam(required = false) Integer minQuantity,
            @RequestParam(required = false) Integer maxQuantity,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice
    ) {
        EquipmentsSearchRequest equipmentsSearchRequest = EquipmentsSearchRequest.builder()
                .id(id)
                .equipment(equipment)
                .minQuantity(minQuantity)
                .maxQuantity(maxQuantity)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .page(page)
                .size(size)
                .build();
        Page<Equipments> equipmentsList = equipmentsService.findAllEquipment(equipmentsSearchRequest);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPages(equipmentsList.getTotalPages())
                .totalElement(equipmentsList.getTotalElements())
                .build();
        WebResponse<List<Equipments>> response = WebResponse.<List<Equipments>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get All Equipment List")
                .paging(pagingResponse)
                .data(equipmentsList.getContent())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<WebResponse<Equipments>> getRoomById(@PathVariable String id){
        Equipments findEquipmentsById = equipmentsService.getEquipmentById(id);
        WebResponse<Equipments> response = WebResponse.<Equipments>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get Equipment By ID")
                .data(findEquipmentsById)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<WebResponse<Equipments>> UpdateEquipmentsById(@RequestBody Equipments equipments) {
        Equipments updateEquipmentById = equipmentsService.updateEquipmentById(equipments);
        WebResponse<Equipments> response = WebResponse.<Equipments>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Update Equipment By ID")
                .data(updateEquipmentById)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<WebResponse<String>> deleteEquipmentById(@PathVariable String id) {
        equipmentsService.deleteEquipmentById(id);
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Delete Equipment")
                .data("OK")
                .build();
        return ResponseEntity.ok(response);
    }
}

