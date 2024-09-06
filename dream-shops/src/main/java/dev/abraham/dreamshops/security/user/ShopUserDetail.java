package dev.abraham.dreamshops.security.user;

import dev.abraham.dreamshops.model.Role;
import dev.abraham.dreamshops.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShopUserDetail implements UserDetails {
    private Long id;
    private String email;
    @Getter
    private String password;

    private Collection<GrantedAuthority> authorities;

    public static ShopUserDetail buildUserDTO(User user) {
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new ShopUserDetail(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getUsername() {
        return email;
    }

    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
