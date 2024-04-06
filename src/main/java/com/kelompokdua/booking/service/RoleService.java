package com.kelompokdua.booking.service;

import com.kelompokdua.booking.constant.ERole;
import com.kelompokdua.booking.entity.Role;

public interface RoleService {

    Role getOrSave(ERole role);
}
