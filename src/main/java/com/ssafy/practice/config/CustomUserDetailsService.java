package com.ssafy.practice.config;

import com.ssafy.practice.model.ApplicationUser;
import com.ssafy.practice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.error("들어왔습니다.");
        ApplicationUser applicationUser = userService.findByUsername(username);
        if(applicationUser == null) {
            log.error("[CustomUserDetailsService loadUserByUsername] username :: {}이 존재하지 않습니다.", username);
            throw new UsernameNotFoundException("User with username "+username+" does not exists");
        }

        return User.withUsername(username).password(applicationUser.getPassword()).roles("USER").disabled(!applicationUser.isVerified()).build();

    }
}
