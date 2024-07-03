package com.springboot.blog.security;

import com.springboot.blog.entity.User;
import com.springboot.blog.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
public class customUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;


    public customUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        // 1. Fetch user from repository by username or email

    User user =userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow(() ->
                    new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));
        // 2. Map user's roles to Spring Security's GrantedAuthority

        Set<GrantedAuthority> authorities = user
            .getRoles()
            .stream()
            .map((role) ->  new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());




        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),             // Username used by Spring Security
                user.getPassword(),          // Password retrieved from the database
                authorities                 // Set of roles/authorities granted to the user
        );


    }
}
