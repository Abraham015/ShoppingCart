package dev.abraham.dreamshops.service.user;

import dev.abraham.dreamshops.dto.UserDTO;
import dev.abraham.dreamshops.model.User;
import dev.abraham.dreamshops.request.user.CreateUserRequest;
import dev.abraham.dreamshops.request.user.UpdateUserRequest;

public interface IUserService {
    UserDTO getUserById(Long userId);
    UserDTO createUser(CreateUserRequest request);
    UserDTO updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);
}
