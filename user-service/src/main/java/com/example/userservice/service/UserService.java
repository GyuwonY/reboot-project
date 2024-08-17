package com.example.userservice.service;

import com.example.userservice.dto.*;
import com.example.userservice.entity.DeviceEntity;
import com.example.userservice.entity.UserEntity;
import com.example.common.entity.enums.user.DeviceTypeEnum;
import com.example.common.entity.enums.user.UserStatusEnum;
import com.example.userservice.redis.entity.AccessToken;
import com.example.userservice.redis.repository.AccessTokenRepository;
import com.example.userservice.repository.DeviceRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.utils.EncryptionUtil;
import com.example.userservice.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.InvalidParameterException;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final JwtUtil jwtUtil;
    private final AccessTokenRepository accessTokenRepository;
    private final EncryptionUtil encryptionUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(
            UserRepository userRepository,
            DeviceRepository deviceRepository,
            JwtUtil jwtUtil,
            AccessTokenRepository accessTokenRepository,
            EncryptionUtil encryptionUtil,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.jwtUtil = jwtUtil;
        this.accessTokenRepository = accessTokenRepository;
        this.encryptionUtil = encryptionUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenResponseDto saveUser (
            SaveUserRequestDto userDto
    ) {
        userDto.encryptInfo(encryptionUtil, passwordEncoder);

        userRepository.findByEmail(userDto.getEmail()).ifPresent(user -> {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
        });

        UserEntity user = userRepository.save(
                UserEntity.builder()
                        .email(userDto.getEmail())
                        .name(userDto.getName())
                        .password(userDto.getPassword())
                        .address(userDto.getAddress())
                        .detailAddress(userDto.getDetailAddress())
                        .status(UserStatusEnum.ACTIVE)
                        .build()
        );

        DeviceEntity device = DeviceEntity.builder()
                .userId(user.getId())
                .type(userDto.getDeviceType())
                .build();
        device.setAccessToken(jwtUtil.createAccessToken(user, device.getId()));
        deviceRepository.save(device);

        accessTokenRepository.save(
                AccessToken.builder()
                        .id(device.getId())
                        .token(device.getAccessToken())
                        .build()
        );

        return TokenResponseDto.builder()
                .token(device.getAccessToken())
                .build();
    }

    @Transactional
    public TokenResponseDto authUser(
            AuthUserRequestDto userDto
    ) {
        UserEntity user = userRepository.findByEmail(userDto.getEmail()).orElseThrow();

        if(!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("존재하지 않는 계정입니다.");
        }

        DeviceEntity device = deviceRepository.findByUserIdAndType(user.getId(), userDto.getDeviceType())
                .orElse(
                        DeviceEntity.builder()
                                .userId(user.getId())
                                .type(userDto.getDeviceType())
                                .build()
                );

        String token = jwtUtil.createAccessToken(user, device.getId());
        device.setAccessToken(token);
        deviceRepository.save(device);

        accessTokenRepository.save(
                AccessToken.builder()
                        .id(device.getId())
                        .token(token)
                        .build()
        );

        return TokenResponseDto.builder()
                .token(token)
                .build();
    }

    @Transactional
    public void signOut(
            String userId,
            DeviceTypeEnum deviceType
    ) {
        DeviceEntity device = deviceRepository.findByUserIdAndType(userId, deviceType)
                .orElseThrow();

        device.setAccessToken("");
        accessTokenRepository.deleteById(device.getId());
    }

    @Transactional
    public UserResponseDto updateUser(
            String userId,
            UpdateUserRequestDto userDto
    ) {
        userDto.encryptInfo(encryptionUtil, passwordEncoder);
        UserEntity user = userRepository.findById(userId).orElseThrow(InvalidParameterException::new);

        if(!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            List<DeviceEntity> deviceList = deviceRepository.findAllByUserId(userId);
            deviceList.forEach(device -> {
                device.setAccessToken("");
                accessTokenRepository.deleteById(device.getId());
            });
        }

        user.update(
                userDto.getPassword(),
                userDto.getName(),
                userDto.getAddress(),
                userDto.getDetailAddress(),
                userDto.getStatus()
        );

        UserResponseDto userResponse = UserResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .address(user.getAddress())
                .detailAddress(user.getDetailAddress())
                .status(user.getStatus())
                .build();
        userResponse.decryptInfo(encryptionUtil);
        return userResponse;
    }
}
