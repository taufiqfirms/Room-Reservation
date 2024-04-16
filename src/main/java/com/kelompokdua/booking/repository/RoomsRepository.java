package com.kelompokdua.booking.repository;


import com.kelompokdua.booking.entity.Rooms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, String >, JpaSpecificationExecutor<Rooms> {
    Optional<Rooms> findByRoomName(String name);
}