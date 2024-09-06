package dev.abraham.dreamshops.data;

import dev.abraham.dreamshops.model.Role;
import dev.abraham.dreamshops.model.User;
import dev.abraham.dreamshops.repository.RoleRepository;
import dev.abraham.dreamshops.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles=Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultUserIfNotExists();
        createDefaultRoleIfNotExists(defaultRoles);
        createDefaultAdminIfNotExists();
    }

    private void createDefaultUserIfNotExists(){
        Role userRole=roleRepository.findByName("ROLE_USER").get(0);
        for (int i = 0; i < 5; i++) {
            String defaultEmail="user"+i+"@gmail.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }

            User user = new User();
            user.setEmail(defaultEmail);
            user.setLastName("User");
            user.setFirstName("User");
            user.setPassword(passwordEncoder.encode("12345"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
        }
    }

    private void createDefaultAdminIfNotExists(){
        Role userRole=roleRepository.findByName("ROLE_ADMIN").get(0);
        for (int i = 0; i < 2; i++) {
            String defaultEmail="admin"+i+"@gmail.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }

            User user = new User();
            user.setEmail(defaultEmail);
            user.setLastName("Admin");
            user.setFirstName("Admin");
            user.setPassword(passwordEncoder.encode("12345"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
        }
    }

    private void createDefaultRoleIfNotExists(Set<String> roles){
        roles.stream()
                .filter(role->roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }
}
