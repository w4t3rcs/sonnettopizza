package org.sonnetto.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.sonnetto.user.dto.UserRequest;
import org.sonnetto.user.dto.UserResponse;
import org.sonnetto.user.entity.User;
import org.sonnetto.user.exception.UserNotFoundException;
import org.sonnetto.user.repository.UserRepository;
import org.sonnetto.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        return UserResponse.fromUser(userRepository.save(userRequest.toUser()));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::fromUser)
                .toList();
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserResponse::fromUser);
    }

    @Override
    public UserResponse getUser(Long id) {
        return UserResponse.fromUser(userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new));
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        if (userRequest.getName() != null) user.setName(userRequest.getName());
        if (userRequest.getPassword() != null) user.setPassword(userRequest.getPassword());
        if (userRequest.getEmail() != null) user.setEmail(userRequest.getEmail());
        if (userRequest.getRole() != null) user.setRole(userRequest.getRole());
        return UserResponse.fromUser(userRepository.save(user));
    }

    @Override
    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }
}
