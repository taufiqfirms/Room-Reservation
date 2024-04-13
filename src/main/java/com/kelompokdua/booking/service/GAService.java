package com.kelompokdua.booking.service;

import org.springframework.data.domain.Page;

import com.kelompokdua.booking.entity.GA;
import com.kelompokdua.booking.entity.UserCredential;
import com.kelompokdua.booking.model.request.GARequest;

public interface GAService {

    GA createGA(GARequest gaRequest, UserCredential userCredential);

    Page<GA> getAllGA(Integer page, Integer size);

    GA getGAById(String id);

    GA updateGA(GA ga);

    void deleteGAById(String id);
}
