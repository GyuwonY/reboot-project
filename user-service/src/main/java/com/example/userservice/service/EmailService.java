package com.example.userservice.service;

import com.example.userservice.redis.entity.EmailCode;
import com.example.userservice.redis.repository.EmailCodeRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailSender;

    private final EmailCodeRepository emailCodeRepository;

    @Autowired
    public EmailService(
            JavaMailSender javaMailSender,
            EmailCodeRepository emailCodeRepository
    ) {
        this.javaMailSender = javaMailSender;
        this.emailCodeRepository = emailCodeRepository;
    }

    private String createCode() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 | i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private MimeMessage createEmailForm(String email) throws MessagingException {
        String authCode = createCode();

        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("인증번호");
        message.setFrom(mailSender);
        message.setText("인증번호 : " + authCode, "utf-8");

        emailCodeRepository.save(
                EmailCode.builder()
                        .id(email)
                        .code(authCode)
                        .build()
        );

        return message;
    }

    public void sendEmail(String toEmail) throws MessagingException {
        MimeMessage emailForm = createEmailForm(toEmail);
        javaMailSender.send(emailForm);
    }

    public Boolean verifyEmailCode(String email, String code) {
        EmailCode emailCode = emailCodeRepository.findById(email).orElseThrow(
                () -> new RuntimeException("입력 시간을 초과하였습니다.")
        );

        return emailCode.getCode().equals(code);
    }
}