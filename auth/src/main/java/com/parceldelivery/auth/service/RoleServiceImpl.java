package com.parceldelivery.auth.service;

import com.parceldelivery.auth.exception.NotFoundException;
import com.parceldelivery.auth.model.entity.RoleEntity;
import com.parceldelivery.auth.repository.RoleRepository;
import com.parceldelivery.model.request.Role;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleEntity getRole(@NonNull Role role) {
        return roleRepository.findByName(role.name())
                .orElseThrow(() -> new NotFoundException("Role", role.name()));
    }
}
