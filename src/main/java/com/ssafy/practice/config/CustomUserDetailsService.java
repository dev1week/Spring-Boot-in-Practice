package com.ssafy.practice.config;

import com.ssafy.practice.model.ApplicationUser;
import com.ssafy.practice.service.LoginAttemptService;
import com.ssafy.practice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.LockedException;
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

    private final LoginAttemptService loginAttemptService;
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //로그인 실패 횟수가 정해진 기준을 넘어서면 일정시간 동안 로그인 금지
        if(loginAttemptService.isBlocked(username)){
            throw new LockedException(username+ "은 계정이 잠겨있습니다.");
        }

        log.error("들어왔습니다.");
        ApplicationUser applicationUser = userService.findByUsername(username);
        if(applicationUser == null) {
            log.error("[CustomUserDetailsService loadUserByUsername] username :: {}이 존재하지 않습니다.", username);
            throw new UsernameNotFoundException("User with username "+username+" does not exists");
        }

        return User.withUsername(username).password(applicationUser.getPassword()).roles("USER").disabled(!applicationUser.isVerified()).build();

    }
}
