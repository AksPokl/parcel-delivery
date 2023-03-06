package com.parceldelivery.auth.service;

import com.parceldelivery.auth.model.entity.RoleEntity;
import com.parceldelivery.model.request.Role;

public interface RoleService {
    RoleEntity getRole(Role role);
}
