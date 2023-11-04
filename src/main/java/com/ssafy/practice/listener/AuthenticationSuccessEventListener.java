package com.ssafy.practice.listener;

import com.ssafy.practice.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.User;


@Component
@AllArgsConstructor
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final LoginAttemptService loginAttemptService;
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        //로그인 성공시 사용자 인증정보 추출
        User user = (User) event.getAuthentication().getPrincipal();
        //로그인 실패횟수 캐시에서 무효화
        loginAttemptService.loginSuccess(user.getUsername());
    }
}
