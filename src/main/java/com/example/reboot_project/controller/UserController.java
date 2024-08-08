package com.example.reboot_project.controller;

import com.example.reboot_project.dto.user.*;
import com.example.reboot_project.service.EmailService;
import com.example.reboot_project.service.UserService;
import jakarta.mail.MessagingException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public UserController(
            EmailService emailService,
            UserService userService
    ) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @GetMapping("/verifycode")
    public void getVerifyCode(
            @RequestParam(value = "email")String email
    ) throws MessagingException {
        emailService.sendEmail(email);
    }

    @GetMapping("/verify")
    public void verifyCode(
            @RequestParam(value = "email")String email,
            @RequestParam(value = "code")String code
    ) throws BadRequestException {
        if(!emailService.verifyEmailCode(email, code)){
            throw new BadRequestException("코드가 일치하지 않습니다.");
        }
    }

    @PostMapping("/signup")
    public TokenResponseDto saveUser(
            @RequestBody SaveUserRequestDto userDto
    ) {
        return userService.saveUser(userDto);
    }

    @PostMapping("/signin")
    public TokenResponseDto authUser(
            @RequestBody AuthUserRequestDto userDto
    ) {
        return userService.authUser(userDto);
    }

    @PutMapping("/{userId}")
    public UserResponseDto updateUser(
            @PathVariable String userId,
            @RequestBody UpdateUserRequestDto userDto
    ) {
        return userService.updateUser(userId, userDto);
    }
}
