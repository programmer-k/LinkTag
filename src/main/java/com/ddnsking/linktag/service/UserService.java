package com.ddnsking.linktag.service;

import com.ddnsking.linktag.domain.User;
import com.ddnsking.linktag.dto.CreateUserRequest;
import com.ddnsking.linktag.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean createUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByUsername(createUserRequest.username()))
            return false;

        User user = User.builder()
                .username(createUserRequest.username())
                .password(passwordEncoder.encode(createUserRequest.password()))
                .build();

        userRepository.save(user);
        return true;
    }

    @Transactional(readOnly = true)
    public User findUserByIdOrThrow(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
