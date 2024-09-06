package dev.abraham.dreamshops.service.user;

import dev.abraham.dreamshops.dto.UserDTO;
import dev.abraham.dreamshops.model.User;
import dev.abraham.dreamshops.request.user.CreateUserRequest;
import dev.abraham.dreamshops.request.user.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);
    UserDTO castToDTO(User user);
    User getAuthenticatedUser();
}