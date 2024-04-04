package com.kelompokdua.booking.service;


import com.kelompokdua.booking.entity.Rooms;
import com.kelompokdua.booking.model.request.RoomsRequest;
import com.kelompokdua.booking.model.response.RoomsResponse;
import org.springframework.data.domain.Page;

public interface RoomsService {

    RoomsResponse createRoom(RoomsRequest roomRequest);
    Page<Rooms> getAllRooms(Integer page, Integer size);
    Rooms getByRoomId(String id);
    Rooms updateRoomById(Rooms rooms);
    void deleteRoomById(String id);






}
