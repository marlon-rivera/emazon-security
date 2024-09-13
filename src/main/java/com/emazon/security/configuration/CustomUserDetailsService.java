package com.emazon.security.configuration;

import com.emazon.security.adapters.driven.jpa.mysql.entity.UserEntity;
import com.emazon.security.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.emazon.security.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(Constants.USER_NOT_FOUND));
        GrantedAuthority authority = new SimpleGrantedAuthority(Constants.ROLE_SECURITY_PREFIX + user.getRole().name());
        return new org.springframework.security.core.userdetails.User(
                user.getId(),
                user.getPassword(),
                List.of(authority)
        );
    }
}
