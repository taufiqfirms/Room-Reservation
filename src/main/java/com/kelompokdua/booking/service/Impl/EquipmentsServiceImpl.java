package com.kelompokdua.booking.service.Impl;


import com.kelompokdua.booking.entity.Equipments;
import com.kelompokdua.booking.model.request.EquipmentsRequest;
import com.kelompokdua.booking.model.request.EquipmentsSearchRequest;
import com.kelompokdua.booking.model.response.EquipmentsResponse;
import com.kelompokdua.booking.repository.EquipmentsRepository;
import com.kelompokdua.booking.service.EquipmentsService;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EquipmentsServiceImpl implements EquipmentsService {

    private final EquipmentsRepository equipmentsRepository;

    @Override
    public EquipmentsResponse createEquipment(EquipmentsRequest equipmentsRequest) {
        Equipments newEquipments = Equipments.builder()
                .equipment(equipmentsRequest.getEquipment())
                .quantity(equipmentsRequest.getQuantity())
                .price(equipmentsRequest.getPrice())
                .build();
        Equipments saveEquipments = equipmentsRepository.saveAndFlush(newEquipments);
        return EquipmentsResponse.builder()
                .equipment(saveEquipments.getEquipment())
                .quantity(saveEquipments.getQuantity())
                .price(saveEquipments.getPrice())
                .build();
    }
    @Override
    public Page<Equipments> findAllEquipment(EquipmentsSearchRequest request) {
        if (request.getPage() == null || request.getPage() <= 0) {
            request.setPage(1);
        }

        if (request.getSize() == null || request.getSize() <= 0) {
            request.setSize(10); // Jumlah default item per halaman
        }
        Pageable pageable = PageRequest.of(
                request.getPage() - 1, request.getSize());

        Specification<Equipments> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getId() != null && !request.getId().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("id"), request.getId()));
            }
            if (request.getEquipment() != null) {
                predicates.add(criteriaBuilder.equal(root.get("equipment"), request.getEquipment()));
            }
            if (request.getMinQuantity() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), request.getMinQuantity()));
            }
            if (request.getMaxQuantity() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("quantity"), request.getMaxQuantity()));
            }
            if (request.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), request.getMinPrice()));
            }
            if (request.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), request.getMaxPrice()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return equipmentsRepository.findAll(spec, pageable);
    }

    @Override
    public Equipments getEquipmentById(String id) {
        Optional<Equipments> optionalEquipments = equipmentsRepository.findById(id);
        if (optionalEquipments.isPresent()) {
            return optionalEquipments.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipment ID Not Found");
    }

    @Override
    public Equipments updateEquipmentById(Equipments equipments) {
        this.getEquipmentById(equipments.getId());
        return equipmentsRepository.saveAndFlush(equipments);
    }

    @Override
    public void deleteEquipmentById(String id) {
        this.getEquipmentById(id);
        equipmentsRepository.deleteById(id);

    }
}
