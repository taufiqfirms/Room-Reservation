package com.kelompokdua.booking.service.Impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.kelompokdua.booking.constant.ERole;
import com.kelompokdua.booking.entity.GA;
import com.kelompokdua.booking.entity.UserCredential;
import com.kelompokdua.booking.model.request.GARequest;
import com.kelompokdua.booking.repository.GARepository;
import com.kelompokdua.booking.service.GAService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GAServiceImpl implements GAService {

    private final GARepository gaRepository;
    
    @Override
    public GA createGA(GARequest gaRequest, UserCredential userCredential) {
       GA ga = GA.builder()
       .name(gaRequest.getName())
       .email(gaRequest.getEmail())
       .roles(ERole.ROLE_GA)
       .userCredential(userCredential)
       .build();

       GA newGA = gaRepository.saveAndFlush(ga);

       return newGA;
    }

    @Override
    public Page<GA> getAllGA(Integer page, Integer size) {
        if (page <=0) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page-1, size);
        return gaRepository.findAll(pageable);
    }

    @Override
    public GA getGAById(String id) {
        Optional<GA> optionalGA = gaRepository.findById(id);
        if(optionalGA.isPresent()) return optionalGA.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "GA with id : " + id + " Not Found");
    }

    @Override
    public GA updateGA(GA ga) {
        this.getGAById(ga.getId());
        return gaRepository.save(ga);
    }

    @Override
    public void deleteGAById(String id) {
        this.getGAById(id);
        gaRepository.deleteById(id);
    }

}
