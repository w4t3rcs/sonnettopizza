package org.sonnetto.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.sonnetto.user.dto.UserRequest;
import org.sonnetto.user.dto.UserResponse;
import org.sonnetto.user.entity.User;
import org.sonnetto.user.exception.UserNotFoundException;
import org.sonnetto.user.producer.UserProducer;
import org.sonnetto.user.repository.UserRepository;
import org.sonnetto.user.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserProducer userProducer;

    @Override
    @Caching(cacheable = @Cacheable("userCache"))
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        User user = userRepository.save(userRequest.toUser());
        userProducer.sendUser("user.created", user);
        return UserResponse.fromUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<UserResponse> getUsers(Pageable pageable) {
        return new PagedModel<>(userRepository.findAll(pageable)
                .map(UserResponse::fromUser));
    }

    @Override
    @Cacheable("userCache")
    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {
        return UserResponse.fromUser(userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new));
    }

    @Override
    @Caching(put = @CachePut("userCache"))
    @Transactional
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        if (userRequest.getName() != null) user.setName(userRequest.getName());
        if (userRequest.getPassword() != null) user.setPassword(userRequest.getPassword());
        if (userRequest.getEmail() != null) user.setEmail(userRequest.getEmail());
        if (userRequest.getRole() != null) user.setRole(userRequest.getRole());
        User updatedUser = userRepository.save(user);
        userProducer.sendUser("user.updated", updatedUser);
        return UserResponse.fromUser(updatedUser);
    }

    @Override
    @Caching(evict = @CacheEvict("userCache"))
    @Transactional
    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }
}
