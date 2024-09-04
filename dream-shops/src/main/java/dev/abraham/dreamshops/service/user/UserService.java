package dev.abraham.dreamshops.service.user;

import dev.abraham.dreamshops.dto.UserDTO;
import dev.abraham.dreamshops.exceptions.UserExistsException;
import dev.abraham.dreamshops.exceptions.UserNotFound;
import dev.abraham.dreamshops.model.User;
import dev.abraham.dreamshops.repository.UserRepository;
import dev.abraham.dreamshops.request.user.CreateUserRequest;
import dev.abraham.dreamshops.request.user.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()->new UserNotFound("User Not Found"));
    }

    public User createUser(CreateUserRequest request) {
        return Optional.of(request).filter(user->!userRepository.existsByEmail(request.getEmail())).map(req->{
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            return userRepository.save(user);
        }).orElseThrow(()->new UserExistsException(request.getEmail()+" already exists"));
    }

    public User updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser->{
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(()->new UserNotFound("User Not Found"));
    }

    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete,
                        ()->{throw new UserNotFound("User Not Found");});
    }

    public UserDTO castToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
