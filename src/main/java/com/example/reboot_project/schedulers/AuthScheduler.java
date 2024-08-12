package com.example.reboot_project.schedulers;

import com.example.reboot_project.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class AuthScheduler {
    @Scheduled(cron = "0 */5 * * * *")
    public void deleteExpiredMailCode(){
        Set<String> keySet = EmailService.verifyCodeMap.keySet();

        keySet.forEach(key -> {
            if(EmailService.verifyCodeMap.get(key).getExpireTime().isBefore(LocalDateTime.now())) {
                EmailService.verifyCodeMap.remove(key);
            }
        });
    }
}
