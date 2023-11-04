package com.ssafy.practice.handler;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private DefaultRedirectStrategy defaultRedirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //인증 예외가 disabled일 때만 해당 url 타고 이동
        if(exception instanceof DisabledException){
            defaultRedirectStrategy.sendRedirect(request, response, "/login-disabled");
            return;
        }

        //locked 예외
        if(exception.getCause() instanceof LockedException){
            defaultRedirectStrategy.sendRedirect(request, response, "/login-locked");
            return;
        }

        // 그 외의 모든 예외는 Logine error 취급
        defaultRedirectStrategy.sendRedirect(request, response, "/login-error");
    }
}
