package com.kelompokdua.booking.service.Impl;


import com.kelompokdua.booking.entity.Equipments;
import com.kelompokdua.booking.model.request.EquipmentsRequest;
import com.kelompokdua.booking.model.request.EquipmentsSearchRequest;
import com.kelompokdua.booking.model.response.EquipmentsResponse;
import com.kelompokdua.booking.repository.EquipmentRepository;
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

import java.util.Optional;

@Service
@AllArgsConstructor
public class EquipmentServiceImpl implements EquipmentsService {

    private final EquipmentRepository equipmentRepository;

    @Override
    public EquipmentsResponse createEquipment(EquipmentsRequest equipmentsRequest) {
        Equipments newEquipments = Equipments.builder()
                .equipment(equipmentsRequest.getEquipment())
                .quantity(equipmentsRequest.getQuantity())
                .price(equipmentsRequest.getPrice())
                .build();
        Equipments saveEquipments = equipmentRepository.saveAndFlush(newEquipments);
        return EquipmentsResponse.builder()
                .equipment(saveEquipments.getEquipment())
                .quantity(saveEquipments.getQuantity())
                .price(saveEquipments.getPrice())
                .build();
    }

    @Override
    public Page<Equipments> getAllEquipment(EquipmentsSearchRequest equipmentsSearchRequest) {
        if (equipmentsSearchRequest.getPage() <= 0 ) {
            equipmentsSearchRequest.setPage(1);
        }
        Specification<Equipments> equipmentsSpecification = findEquipments(
                equipmentsSearchRequest.getId(),
                equipmentsSearchRequest.getEquipment(),
                equipmentsSearchRequest.getQuantity(),
                equipmentsSearchRequest.getMinPrice(),
                equipmentsSearchRequest.getMaxPrice());
        Pageable pageable = PageRequest.of(equipmentsSearchRequest.getPage()-1,equipmentsSearchRequest.getSize());
        return equipmentRepository.findAll(equipmentsSpecification, pageable);
    }

    public Specification<Equipments> findEquipments(String id, String equipment, Integer quantity,
                                                    Long minPrice, Long maxPrice) {

        return (root, query, criteriaBuilder) -> {
            Predicate idPredicate = criteriaBuilder.equal(root.get("id"), id);
            Predicate equipmentPredicate = criteriaBuilder.equal(root.get("equipment"), equipment);
            Predicate quantityTypePredicate = criteriaBuilder.equal(root.get("quantity"), quantity);
            Predicate minPricePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            Predicate maxPricePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            return criteriaBuilder.or(idPredicate, equipmentPredicate, quantityTypePredicate, 
                    minPricePredicate, maxPricePredicate);
        };
    };

    @Override
    public Equipments getEquipmentById(String id) {
        Optional<Equipments> optionalEquipments = equipmentRepository.findById(id);
        if (optionalEquipments.isPresent()) {
            return optionalEquipments.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipment ID Not Found");
    }

    @Override
    public Equipments updateEquipmentById(Equipments equipments) {
        this.getEquipmentById(equipments.getId());
        return equipmentRepository.save(equipments);
    }

    @Override
    public void deleteEquipmentById(String id) {
        this.getEquipmentById(id);
        equipmentRepository.deleteById(id);

    }
}
