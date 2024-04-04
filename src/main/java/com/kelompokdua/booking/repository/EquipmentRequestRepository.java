package com.kelompokdua.booking.repository;

import com.kelompokdua.booking.entity.Equipments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRequestRepository extends JpaRepository<Equipments, String > {
}
