package dev.abraham.dreamshops.security.user;

import dev.abraham.dreamshops.exceptions.UserNotFound;
import dev.abraham.dreamshops.model.User;
import dev.abraham.dreamshops.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(()->new UserNotFound("User not found"));
        return ShopUserDetail.buildUserDTO(user);
    }
}