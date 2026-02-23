package com.dokotech.platform.userservice.services;

import com.dokotech.platform.userservice.models.User;
import com.dokotech.platform.userservice.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        if (!user.getIsActive()) {
            throw new UsernameNotFoundException("User is inactive: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(),
                getAuthorities(user)
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        // TODO: Implement proper role-based authorities
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));


        // Original implementation (commented out for now)
        // return user.getRoles().stream()
        //         .flatMap(role -> role.getPermissions().stream())
        //         .map(permission -> new SimpleGrantedAuthority(permission.getName()))
        //         .collect(Collectors.toSet());
    }
}

