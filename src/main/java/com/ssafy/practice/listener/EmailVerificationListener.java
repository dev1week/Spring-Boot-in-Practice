package com.ssafy.practice.listener;

import com.ssafy.practice.event.UserRegistrationEvent;
import com.ssafy.practice.model.ApplicationUser;
import com.ssafy.practice.service.EmailVerificationService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@AllArgsConstructor
public class EmailVerificationListener implements ApplicationListener<UserRegistrationEvent> {

    private final JavaMailSender mailSender;
    private final EmailVerificationService emailVerificationService;



    //UserRegistrationevent 발생시 해당 메서드가 호출된다.
    @Override
    public void onApplicationEvent(UserRegistrationEvent event) {
        ApplicationUser user = event.getUser();
        String username = user.getUsername();
        //사용자 등록시 생성된 verificationId를 조회한다.
        String verificationId = emailVerificationService.generateVerification(username);
        String email = user.getEmail();

        //메시지 세팅
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("회원 가입 인증 메일입니다.");
        message.setText(getText(user, verificationId));

        //메시지 보내기
        message.setTo(email);
        mailSender.send(message);
    }


    private String getText(ApplicationUser user, String verificationId){

        String encodedVerificationId = new String(Base64.getEncoder().encode(verificationId.getBytes()));

        StringBuffer buffer = new StringBuffer();
        buffer.append("Dear ").append(user.getFirstName()).append(" ").append(user.getLastName()).append(",").append(System.lineSeparator()).append(System.lineSeparator());
        buffer.append("Your account has been successfully created in the Course Tracker application. ");

        buffer.append("Activate your account by clicking the following link: http://localhost:8080/verify/email?id=").append(encodedVerificationId);
        buffer.append(System.lineSeparator()).append(System.lineSeparator());
        buffer.append("Regards,").append(System.lineSeparator()).append("Course Tracker Team");
        return buffer.toString();


    }
}
