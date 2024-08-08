package com.example.reboot_project.security;

import com.example.reboot_project.entity.UserEntity;
import com.example.reboot_project.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(
            UserRepository userRepository
    ){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Not available User"));

        return new CustomUserDetails(user);
    }
}
