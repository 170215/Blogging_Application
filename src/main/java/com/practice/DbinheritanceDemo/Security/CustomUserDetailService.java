package com.practice.DbinheritanceDemo.Security;

import com.practice.DbinheritanceDemo.exceptions.ResourceNotFoundException;
import com.practice.DbinheritanceDemo.models.User;
import com.practice.DbinheritanceDemo.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {

   UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User","email",username));
        return user;
    }
}
