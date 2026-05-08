package org.palmadae.donortrack.service.user;

import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.repository.user.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserJpaRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        String authority = "ROLE_" + user.getRole().name();
        System.out.println("Loading user: " + username + " with authority: " + authority);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getHashPass())
                .authorities(authority)
                .build();
    }
}