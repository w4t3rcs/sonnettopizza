package org.sonnetto.user.service;

import org.sonnetto.user.dto.UserRequest;
import org.sonnetto.user.dto.UserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);

    List<UserResponse> getAllUsers();

    PagedModel<UserResponse> getAllUsers(Pageable pageable);

    UserResponse getUser(Long id);

    UserResponse updateUser(Long id, UserRequest userRequest);

    Long deleteUser(Long id);
}
