package com.ssafy.practice.controller;

import com.ssafy.practice.Dto.UserDto;
import com.ssafy.practice.event.UserRegistrationEvent;
import com.ssafy.practice.model.ApplicationUser;
import com.ssafy.practice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class RegistrationController {


    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    @GetMapping("/adduser")
    public String register(Model model) {
        model.addAttribute("user", new UserDto());
        return "add-user";
    }

    @PostMapping("/adduser")
    public String register(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result) {
        if(result.hasErrors()) {
            return "add-user";
        }
        ApplicationUser applicationUser = userService.createUser(userDto);
        eventPublisher.publishEvent(new UserRegistrationEvent(applicationUser));
        return "redirect:adduser?validate";
    }
}

