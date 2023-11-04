package com.ssafy.practice.controller;

import com.ssafy.practice.model.ApplicationUser;
import com.ssafy.practice.service.EmailVerificationService;
import com.ssafy.practice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Base64;


//이메일로 보낸 활성화 링크를 클릭하면 호출될 엔드포인트
@Controller
@AllArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;
    private final UserService userService;

    @GetMapping("/verify/email")
    public String verifyEmail(@RequestParam String id){
        //base64로 인코딩되어 메일로 보낸 verificationId 값을 Base64로 디코딩한다.
        byte[] actualId = Base64.getDecoder().decode(id.getBytes());

        //디코딩한 id값을 이용하여 username을 조회한다.
        String username = emailVerificationService.getUsernameForVerificationId(new String(actualId));


        //user가 존재하면 verified를 true로 저장하고 인증 완료 페이지로 리다이렉트 한다.
        if(username!=null){

            ApplicationUser user = userService.findByUsername(username);

            user.setVerified(true);
            userService.save(user);

            return "redirect:/login-verified";
        }

        return "redirect:/login-error";
    }


}
