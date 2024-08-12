package com.example.reboot_project.security;

import com.example.reboot_project.entity.user.DeviceEntity;
import com.example.reboot_project.repository.user.DeviceRepository;
import com.example.reboot_project.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final DeviceRepository deviceRepository;

    @Autowired
    public JwtAuthenticationFilter(
            CustomUserDetailsService customUserDetailsService,
            JwtUtil jwtUtil,
            DeviceRepository deviceRepository
    ){
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.deviceRepository = deviceRepository;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                String userId = jwtUtil.getUserId(token);

                CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);
                List<DeviceEntity> deviceList = deviceRepository.findAllByUserId(userId);
                List<DeviceEntity> activeDevice = deviceList.stream()
                        .filter(device -> token.equals(device.getAccessToken()))
                        .toList();

                if(activeDevice.isEmpty()) {
                    throw new AccountExpiredException("다시 로그인 해주세요.");
                }

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}