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
    public Page<Equipments> getAllEquipment(EquipmentsSearchRequest equipmentsSearchRequest) {
        if (equipmentsSearchRequest.getPage() <= 0 ) {
            equipmentsSearchRequest.setPage(1);
        }
        Specification<Equipments> equipmentsSpecification = findEquipments(
                equipmentsSearchRequest.getId(),
                equipmentsSearchRequest.getEquipment(),
                equipmentsSearchRequest.getMinQuantity(),
                equipmentsSearchRequest.getMaxQuantity(),
                equipmentsSearchRequest.getMinPrice(),
                equipmentsSearchRequest.getMaxPrice());
        Pageable pageable = PageRequest.of(equipmentsSearchRequest.getPage()-1,equipmentsSearchRequest.getSize());
        return equipmentsRepository.findAll(equipmentsSpecification, pageable);
    }

    public Specification<Equipments> findEquipments(String id, String equipment,
                                                    Integer minQuantity, Integer maxQuantity,
                                                    Long minPrice, Long maxPrice) {

        return (root, query, criteriaBuilder) -> {
            Predicate idPredicate = criteriaBuilder.equal(root.get("id"), id);
            Predicate equipmentPredicate = criteriaBuilder.equal(root.get("equipment"), equipment);
            Predicate mixQuantityPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), minQuantity);
            Predicate maxQuantityPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("quantity"), maxQuantity);
            Predicate minPricePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            Predicate maxPricePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            return criteriaBuilder.or(idPredicate, equipmentPredicate, mixQuantityPredicate,
                    maxQuantityPredicate, minPricePredicate, maxPricePredicate);
        };
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
