package com.kelompokdua.booking.controller;


import com.kelompokdua.booking.entity.Equipments;
import com.kelompokdua.booking.model.request.EquipmentsRequest;
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
public class EquipmentController{

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
    public ResponseEntity<WebResponse<List<Equipments>>> getAllEquipment(
            @RequestParam(defaultValue = "") String id,
            @RequestParam(defaultValue = "") String equipment,
            @RequestParam(defaultValue = "") Integer quantity,
            @RequestParam(defaultValue = "") Long minPrice,
            @RequestParam(defaultValue = "") Long maxPrice,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
        SearchLoanRequest searchLoanRequest = SearchLoanRequest.builder()
                .page((page))
                .size(size)
                .minAmount(minamout)
                .maxamount(maxamount)
                .build();
        Page<Equipments> equipmentsList = equipmentsService.getAllEquipment(page, size);
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

