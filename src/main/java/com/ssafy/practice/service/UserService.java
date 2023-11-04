package com.ssafy.practice.service;

import com.ssafy.practice.Dto.UserDto;
import com.ssafy.practice.model.ApplicationUser;

public interface UserService {
    ApplicationUser createUser(UserDto userDto);
    ApplicationUser findByUsername(String username);
}
