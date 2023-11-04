package com.ssafy.practice.repository;

import com.ssafy.practice.model.EmailVerification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerificationRepository extends CrudRepository<EmailVerification, String> {

    EmailVerification findByUsername(String username);
    boolean existsByUsername(String username);




}
