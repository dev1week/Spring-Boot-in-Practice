package com.ssafy.practice.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {


    @GetMapping("/login")
    public String login(){
        return "login";
    }



    //logout에는 csrf 공격을 막기 위해 get이 아니라 post 메서드를 사용한다.
    @PostMapping("/doLogout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null){
            //SecurityContextLogoutHandler는 현재 존재하는 httpSession 객체를 비활성화하고
            //SecurityContext에서 인증 정보를 삭제하면서 사용자를 로그아웃 처리한다.
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login";
    }


}
