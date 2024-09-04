package dev.abraham.dreamshops.controller;

import dev.abraham.dreamshops.dto.UserDTO;
import dev.abraham.dreamshops.exceptions.UserExistsException;
import dev.abraham.dreamshops.exceptions.UserNotFound;
import dev.abraham.dreamshops.model.User;
import dev.abraham.dreamshops.request.user.CreateUserRequest;
import dev.abraham.dreamshops.request.user.UpdateUserRequest;
import dev.abraham.dreamshops.response.APIResponse;
import dev.abraham.dreamshops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            UserDTO userDTO =userService.castToDTO(user);
            return ResponseEntity.ok(new APIResponse("User found", userDTO));
        } catch (UserNotFound e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addUser(@RequestBody CreateUserRequest request) {
        try {
            User user=userService.createUser(request);
            return ResponseEntity.ok(new APIResponse("User added", userService.castToDTO(user)));
        } catch (UserExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<APIResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long userId) {
        try {
            User user=userService.updateUser(request, userId);
            return ResponseEntity.ok(new APIResponse("User updated", userService.castToDTO(user)));
        } catch (UserNotFound e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new APIResponse("User deleted", userId));
        } catch (UserNotFound e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }
}