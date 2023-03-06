package com.parceldelivery.auth.service;

import com.google.common.collect.ImmutableSet;
import com.parceldelivery.auth.exception.UserAlreadyExistsException;
import com.parceldelivery.auth.model.dto.SignupRequest;
import com.parceldelivery.auth.model.entity.UserEntity;
import com.parceldelivery.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public UUID save(@NonNull SignupRequest signupRequest) {
        validateUserForSighup(signupRequest.getUsername(), signupRequest.getEmail());
        UserEntity userEntity = toUserEntity(signupRequest);
        UserEntity createdUser = userRepository.save(userEntity);
        return createdUser.getId();
    }

    private UserEntity toUserEntity(SignupRequest request) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        userEntity.setEmail(request.getEmail());
        userEntity.setUsername(request.getUsername());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setRoles(ImmutableSet.of(roleService.getRole(request.getRole())));
        return userEntity;
    }

    private void validateUserForSighup(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException(username);
        }

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }
    }
}
