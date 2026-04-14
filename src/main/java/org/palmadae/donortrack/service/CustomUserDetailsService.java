package org.palmadae.donortrack.service;

import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String roleName = user.getRole() != null ? user.getRole().name() : "USER";
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getHash_pass())
                .roles(roleName)
                .build();
    }
}