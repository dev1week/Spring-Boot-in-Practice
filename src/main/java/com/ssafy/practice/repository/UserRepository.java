package com.ssafy.practice.repository;

import com.ssafy.practice.model.ApplicationUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends CrudRepository<ApplicationUser, Long> {

    ApplicationUser findByUsername(String username);
}
