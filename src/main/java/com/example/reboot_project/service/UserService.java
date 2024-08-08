package com.example.reboot_project.service;

import com.example.reboot_project.dto.user.*;
import com.example.reboot_project.entity.DeviceEntity;
import com.example.reboot_project.entity.UserEntity;
import com.example.reboot_project.entity.enums.UserStatusEnum;
import com.example.reboot_project.repository.DeviceRepository;
import com.example.reboot_project.repository.UserRepository;
import com.example.reboot_project.utils.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(
            UserRepository userRepository,
            DeviceRepository deviceRepository,
            JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.jwtUtil = jwtUtil;
    }

    public TokenResponseDto saveUser (
            SaveUserRequestDto userDto
    ) {
        userDto.encryptInfo();

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

        DeviceEntity device = deviceRepository.save(
                DeviceEntity.builder()
                        .userId(user.getId())
                        .type(userDto.getDeviceType())
                        .AccessToken(jwtUtil.createAccessToken(user))
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
        userDto.encryptInfo();
        UserEntity user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException("존재하지 않는 계정입니다."));

        if(!user.getPassword().equals(userDto.getPassword())) {
            throw new UsernameNotFoundException("존재하지 않는 계정입니다.");
        }

        String token = jwtUtil.createAccessToken(user);
        DeviceEntity device = deviceRepository.findByUserIdAndType(user.getId(), userDto.getDeviceType())
                .orElse(
                        DeviceEntity.builder()
                                .userId(user.getId())
                                .type(userDto.getDeviceType())
                                .build()
                );
        device.setAccessToken(token);
        deviceRepository.save(device);

        return TokenResponseDto.builder()
                .token(token)
                .build();
    }

    @Transactional
    public UserResponseDto updateUser(
            String userId,
            UpdateUserRequestDto userDto
    ) {
        userDto.encryptInfo();
        UserEntity user = userRepository.findById(userId).orElseThrow(InvalidParameterException::new);

        if(!userDto.getPassword().equals(user.getPassword())) {
            List<DeviceEntity> deviceList = deviceRepository.findAllByUserId(userId);
            deviceList.forEach(device -> device.setAccessToken(""));
        }

        user.update(
                userDto.getPassword(),
                userDto.getName(),
                userDto.getAddress(),
                userDto.getDetailAddress(),
                userDto.getStatus()
        );

        return UserResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .address(user.getAddress())
                .detailAddress(user.getDetailAddress())
                .status(user.getStatus())
                .build();
    }
}
