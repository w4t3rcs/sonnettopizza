package org.sonnetto.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.sonnetto.user.dto.UserRequest;
import org.sonnetto.user.dto.UserResponse;
import org.sonnetto.user.entity.User;
import org.sonnetto.user.exception.UserNotFoundException;
import org.sonnetto.user.repository.UserRepository;
import org.sonnetto.user.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        return UserResponse.fromUser(userRepository.save(userRequest.toUser()));
    }

    @Override
    public PagedModel<UserResponse> getUsers(Pageable pageable) {
        return new PagedModel<>(userRepository.findAll(pageable)
                .map(UserResponse::fromUser));
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
