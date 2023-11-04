package com.ssafy.practice.service;

import com.ssafy.practice.model.EmailVerification;
import com.ssafy.practice.repository.EmailVerificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmailVerificationService {

    private final EmailVerificationRepository repository;



    //등록된 사용자 유저 네임에 해당하는 EmailVerification을 생성하고 저장
    public String generateVerification(String username) {
        if (!repository.existsByUsername(username)) {
            EmailVerification verification = new EmailVerification(username);
            verification = repository.save(verification);
            return verification.getVerificationId();
        }
        return getVerificationIdByUsername(username);
    }

    //username에 해당하는 EmailVerification의 식별자인 verificationId를 만든다.
    public String getVerificationIdByUsername(String username) {
        EmailVerification verification = repository.findByUsername(username);
        if(verification != null) {
            return verification.getVerificationId();
        }
        return null;
    }

    //식별 id에 해당하는 username을 반환한다.
    public String getUsernameForVerificationId(String verificationId){
        Optional<EmailVerification> verification = repository.findById(verificationId);

        if(verification.isPresent()){
            return verification.get().getUsername();
        }

        return null;


    }



}
