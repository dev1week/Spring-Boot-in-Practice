package com.ssafy.practice.listener;

import com.ssafy.practice.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//로그인 실패 이벤트를 처리하는 리스너
@Component
@AllArgsConstructor
public class AuthenticationFailureEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final LoginAttemptService loginAttemptService;

    //로그인 실패시 LoginAttemptService를 호출해서 로그인 실패 횟수를 업데이트한다.
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String username = (String) event.getAuthentication().getPrincipal();
        loginAttemptService.loginFailed(username);

    }
}
