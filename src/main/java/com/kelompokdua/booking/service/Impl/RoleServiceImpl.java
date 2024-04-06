package com.kelompokdua.booking.service.Impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kelompokdua.booking.constant.ERole;
import com.kelompokdua.booking.entity.Role;
import com.kelompokdua.booking.repository.RoleRepository;
import com.kelompokdua.booking.service.RoleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(ERole role) {
        // ambil jika role ada
        Optional<Role> optionalRole = roleRepository.findByRole(role);
        if(optionalRole.isPresent()) return optionalRole.get();
        // jika tidak ada, disimpan
        return roleRepository.saveAndFlush(
            Role.builder()
            .role(role)
            .build()
        );
    }
}
