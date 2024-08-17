package com.example.userservice.utils;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {
    private final String key = "secretsecretsecretkeyy";
    private final String salt = "73616C74";


    public String encrypt(String text) {
        TextEncryptor encryptor = Encryptors.text(key, salt);
        return encryptor.encrypt(text);
    }

    public String decrypt(String encryptString) {
        TextEncryptor encryptor = Encryptors.text(key, salt);
        return encryptor.decrypt(encryptString);
    }
}
