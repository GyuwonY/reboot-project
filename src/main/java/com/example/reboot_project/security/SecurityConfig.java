package com.example.reboot_project.security;

import com.example.reboot_project.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    public SecurityConfig(
            CustomUserDetailsService customUserDetailsService,
            JwtUtil jwtUtil
    ){
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtAuthenticationFilter(customUserDetailsService, jwtUtil), UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**").permitAll()
                        .anyRequest().permitAll()
        );

        return http.build();
    }
}
